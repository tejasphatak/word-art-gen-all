package com.wordart.gen.api.v1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wordart.gen.api.v1.JobsApiDelegate;
import com.wordart.gen.api.v1.model.JobRequest;
import com.wordart.gen.api.v1.model.JobResponse;
import com.wordart.gen.entities.Job;
import com.wordart.gen.service.JobService;
import com.wordart.gen.util.Constants;

@Service
public class JobsApiDelegateImpl implements JobsApiDelegate {

	private Logger logger = LoggerFactory.getLogger(JobsApiDelegateImpl.class);
	private JobService jobService;
	private JobRequestToJobMapper jobRequestToJobMapper;
	private JobToJobResponseMapper jobToJobResponseMapper;
	private String outputPath;

	/**
	 * 
	 * @param jobService
	 *            Job Service Business Layer instance
	 * @param jobRequestToJobMapper
	 *            JobRequest to Job Mapper instance
	 * @param jobToJobResponseMapper
	 *            Job to JobResponse Mapper instance
	 * @param outputPath
	 *            Common shared path where generated files are kept.
	 */
	public JobsApiDelegateImpl(JobService jobService, JobRequestToJobMapper jobRequestToJobMapper,
			JobToJobResponseMapper jobToJobResponseMapper, @Value("${word-art-gen-path:/tmp/}") String outputPath) {
		this.jobService = jobService;
		this.jobRequestToJobMapper = jobRequestToJobMapper;
		this.jobToJobResponseMapper = jobToJobResponseMapper;
		this.outputPath = outputPath;
	}

	@Override
	public ResponseEntity<JobResponse> deleteJobById(UUID jobId) {
		// TODO
		return JobsApiDelegate.super.deleteJobById(jobId);
	}

	@Override
	public ResponseEntity<JobResponse> getJobById(UUID jobId) {
		Optional<Job> optionalJob = jobService.getJob(jobId);
		if (!optionalJob.isPresent()) {
			logger.info("Job [{}] not found", jobId);
			return new ResponseEntity<JobResponse>(HttpStatus.NOT_FOUND);
		} else {
			logger.info("Job [{}] found", jobId);
			return new ResponseEntity<JobResponse>(jobToJobResponseMapper.map(optionalJob.get()), HttpStatus.ACCEPTED);
		}
	}

	@Override
	public ResponseEntity<Resource> getWordArt(UUID jobId) {
		Optional<Job> job = jobService.getJob(jobId);
		if (job.isPresent() && job.get().getStatus().equals(Job.Status.GENERATED)) {
			Resource resource;
			MediaType mediaType = null;
			switch (job.get().getFormat()) {
			case ASCII:
				mediaType = MediaType.TEXT_PLAIN;
				break;
			case JPEG:
				mediaType = MediaType.IMAGE_JPEG;
				break;
			case PNG:
				mediaType = MediaType.IMAGE_PNG;
				break;
			case TIFF:
				mediaType = MediaType.valueOf("image/tiff");
				break;
			}
			try {
				logger.info("Job [{}] found. Returning Generated Art as MediaType [{}].", jobId, mediaType);
				resource = new ByteArrayResource(FileUtils.readFileToByteArray(new File(getAbsolutePath(job.get()))));
				return ResponseEntity.ok().contentLength(resource.contentLength()).contentType(mediaType)
						.body(resource);
			} catch (IOException e) {
				logger.info("Exception occured while reading generated image for Job [{" + jobId + "}] ", e);
				return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		logger.info("Job [{}] not found", jobId);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<JobResponse> submitJob(JobRequest jobRequest) {
		logger.info("Job request submitted: [{}]", jobRequest);
		Job job = jobRequestToJobMapper.map(jobRequest);
		Job acceptedJob = jobService.submitJob(job);
		logger.info("Job Request Saved: [{}] ", acceptedJob);
		return new ResponseEntity<JobResponse>(jobToJobResponseMapper.map(acceptedJob), HttpStatus.ACCEPTED);
	}

	private String getAbsolutePath(Job job) {
		return Paths.get(outputPath, String.format(Constants.FILENAME_EXT_FORMAT, job.getId().toString(),
				job.getFormat().toString().toLowerCase())).toAbsolutePath().toString();
	}
}
