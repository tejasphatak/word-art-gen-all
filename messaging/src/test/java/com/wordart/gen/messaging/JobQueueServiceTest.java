package com.wordart.gen.messaging;

import com.wordart.gen.entities.Job;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wordart.gen.config.MessagingConfig;
import com.wordart.gen.service.JobQueueService;

@SpringBootApplication
@ExtendWith(SpringExtension.class)
@Import({ MessagingConfig.class})
@TestPropertySource(locations="classpath:application.properties")
@Disabled
public class JobQueueServiceTest{

	@Autowired
	private JobQueueService messagingService;
	
	@Test
	public void testSent() {
		Job job = JobGenerator.generateJob();
		messagingService.queueJob(job);
	}
	
}
