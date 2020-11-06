package com.wordart.gen.api.v1;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wordart.gen.api.v1.model.JobRequest.ColourEnum;
import com.wordart.gen.api.v1.model.JobRequest.FontEnum;
import com.wordart.gen.api.v1.model.JobRequest.FormatEnum;
import com.wordart.gen.api.v1.model.JobResponse;
import com.wordart.gen.api.v1.model.JobResponse.StatusEnum;
import com.wordart.gen.entities.Job;

@Service
public class JobToJobResponseMapper {

	public JobResponse map(Job job) {
		JobResponse jobResponse = new JobResponse();
		jobResponse.setId(job.getId());
		jobResponse.setColour(ColourEnum.valueOf(job.getFontColour().toString()));
		jobResponse.setFont(FontEnum.fromValue(job.getFont().getFontName()));
		jobResponse.setEffect(getEffects(job.getEffects()));
		jobResponse.setStatus(StatusEnum.valueOf(job.getStatus().toString()));
		jobResponse.setText(job.getText());
		jobResponse.setFormat(FormatEnum.valueOf(job.getFormat().toString()));
		return jobResponse;
	}

	private List<String> getEffects(EnumSet<Job.Effect> effectsSet) {
		return effectsSet.stream().map(Job.Effect::toString).collect(Collectors.toList());
	}
}
