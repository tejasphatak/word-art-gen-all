package com.wordart.gen.service;

import com.wordart.gen.entities.Job;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.wordart.gen.config.MessagingConfig;

@Service
public class JobQueueService {

	private final RabbitTemplate rabbitTemplate;
	
	public JobQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
	
	public void queueJob(Job job)
	{
		rabbitTemplate.convertAndSend(MessagingConfig.topicExchangeName, "word.art.gen.job", job.getId().toString());
	}
}
