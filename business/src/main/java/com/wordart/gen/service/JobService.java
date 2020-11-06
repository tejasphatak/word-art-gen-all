package com.wordart.gen.service;

import java.util.Optional;
import java.util.UUID;

import com.wordart.gen.dao.JobRepository;
import com.wordart.gen.entities.Job;
import org.springframework.stereotype.Service;

@Service
public class JobService {

	private final JobRepository jobRepository;
	private final JobQueueService jobQueueService;

	public JobService(JobRepository repository, JobQueueService jobQueueService) {
		this.jobRepository = repository;
		this.jobQueueService = jobQueueService;
	}

	/**
	 * 
	 * @param job
	 *            that is to be submitted
	 * @return Job object
	 */
	public Job submitJob(Job job) {
		Job returnJob = jobRepository.save(job);
		jobQueueService.queueJob(returnJob);
		return returnJob;
	}

	/**
	 * 
	 * @param id
	 *            Job Id
	 * @return Optional Job Object
	 */
	public Optional<Job> getJob(UUID id) {
		return jobRepository.findById(id);
	}

	/**
	 * 
	 * @param id
	 *            Job Id
	 */
	public void deleteJob(UUID id) {
		// To be implemented
		throw new UnsupportedOperationException("Not implemeted");
	}

}
