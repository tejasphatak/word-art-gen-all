package com.wordart.gen.api.v1.model;

import com.wordart.gen.api.v1.JobRequestToJobMapper;
import com.wordart.gen.api.v1.JobToJobResponseMapper;
import com.wordart.gen.entities.Job;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import com.wordart.gen.api.v1.model.JobRequest;
import com.wordart.gen.api.v1.model.JobResponse;
import com.wordart.gen.api.v1.model.JobRequest.ColourEnum;
import com.wordart.gen.api.v1.model.JobRequest.FontEnum;
import com.wordart.gen.api.v1.model.JobRequest.FormatEnum;

public class JobToJobResponseMapperTest {

	@Test
	public void testJobRequestToJobMapper() {
		JobRequest jobRequest = new JobRequest();
		jobRequest.setColour(ColourEnum.BLACK);
		jobRequest.setFont(FontEnum.FREEMONO);
		jobRequest.setText("test");
		jobRequest.setFormat(FormatEnum.JPEG);
		jobRequest.setEffect(Lists.newArrayList("SHADOW_HARD", "SHADOW_REFLECT"));
		JobRequestToJobMapper jobRequestToJobMapper= new JobRequestToJobMapper();
		Job job = jobRequestToJobMapper.map(jobRequest);
		
		JobToJobResponseMapper jobToJobResponseMapper = new JobToJobResponseMapper();
		JobResponse jobResponse = jobToJobResponseMapper.map(job);
		System.out.println(jobRequest);
		System.out.println(job);
		System.out.println(jobResponse);
	}
}
