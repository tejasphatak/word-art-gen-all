package com.wordart.gen.dao;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.wordart.gen.entities.Job;

public interface JobRepository extends MongoRepository<Job, String> {
	 public Optional<Job> findById(UUID id);
}
