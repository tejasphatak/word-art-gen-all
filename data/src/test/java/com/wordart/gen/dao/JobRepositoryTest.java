package com.wordart.gen.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;

import com.wordart.gen.entities.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootApplication
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class JobRepositoryTest{

	@Autowired
	private JobRepository repository;
	
	@Test
	public void testAdd() {
		Job job = JobGenerator.generateJob();
		repository.insert(job);
		Optional<Job> retrievedJob = repository.findById(job.getId());
		assertTrue(retrievedJob.isPresent());
	}
	
	@Test
	public void testRemove() {
		Job job = JobGenerator.generateJob();
		repository.insert(job);
		Optional<Job> retrievedJob = repository.findById(job.getId());
		assertTrue(retrievedJob.isPresent());
		repository.delete(job);
		assertFalse(repository.findById(job.getId()).isPresent());
	}
	
	@Test
	public void testUpdate() {
		Job job = JobGenerator.generateJob();
		job.setStatus(Job.Status.ACCEPTED);
		repository.insert(job);
		job.setStatus(Job.Status.RUNNING);
		repository.save(job);
		Optional<Job> retrievedJob = repository.findById(job.getId());
		assertTrue(retrievedJob.isPresent());
		assertTrue(retrievedJob.get().getStatus().equals(Job.Status.RUNNING));
	}
}
