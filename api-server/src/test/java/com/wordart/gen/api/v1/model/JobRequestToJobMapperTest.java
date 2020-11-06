package com.wordart.gen.api.v1.model;

import com.wordart.gen.api.v1.JobRequestToJobMapper;
import com.wordart.gen.entities.Job;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import com.wordart.gen.api.v1.model.JobRequest;
import com.wordart.gen.api.v1.model.JobRequest.ColourEnum;
import com.wordart.gen.api.v1.model.JobRequest.FontEnum;
import com.wordart.gen.api.v1.model.JobRequest.FormatEnum;

public class JobRequestToJobMapperTest {

	@Test
	public void testJobRequestToJobMapper() {
		JobRequest jobRequest = new JobRequest();
		jobRequest.setColour(ColourEnum.BLACK);
		jobRequest.setFont(FontEnum.FREEMONO);
		jobRequest.setText("test");
		jobRequest.setFormat(FormatEnum.JPEG);
		jobRequest.setEffect(Lists.newArrayList("SHADOW_HARD", "SHADOW_REFLECT"));
		JobRequestToJobMapper mapper = new JobRequestToJobMapper();
		Job job = mapper.map(jobRequest);
		System.out.println(jobRequest);
		System.out.println(job);
	}
}
