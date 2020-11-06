package com.wordart.gen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import com.wordart.gen.dao.JobRepository;
import com.wordart.gen.entities.Job;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JobProcessor {

	private final Generator generator;
	private final JobRepository repository;
	private final ImageWriter writer;
	private Logger logger = LoggerFactory.getLogger(JobProcessor.class);

	public JobProcessor(Generator generator, JobRepository repository, ImageWriter writer) {
		this.generator = generator;
		this.repository = repository;
		this.writer = writer;
	}

	public void process(String jobId) {
		validateJobId(jobId);

		Job job = null;
		Optional<Job> optionalJob = repository.findById(UUID.fromString(jobId));

		if (optionalJob.isPresent()) {
			job = optionalJob.get();
		} else {
			logger.info("Job '{}' does not exist!", jobId);
			return;
		}

		try {
			updateJobStatus(job, Job.Status.RUNNING);
			writer.write(generateImage(job), job);
			updateJobStatus(job, Job.Status.GENERATED);
		} catch (IOException e) {
			updateJobStatus(job, Job.Status.FAILED);
			logger.info(String.format("Failed to generate and create word art for Job [%s]", job.getId().toString()),
					e);
		}
	}

	private void updateJobStatus(Job job, Job.Status status) {
		logger.info("Updating job[{}] with status [{}]", job.getId().toString(), status);
		job.setStatus(status);
		repository.save(job);
	}

	private void validateJobId(String jobId) {
		if (StringUtils.isEmpty(jobId))
			throw new IllegalArgumentException("JobId cannot be null or empty!");

	}

	private InputStream generateImage(Job job) throws IOException {
		return generator.generateImage(job);
	}
}
