package com.wordart.gen.api.v1;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wordart.gen.api.v1.model.JobRequest;
import com.wordart.gen.entities.Job;
import com.wordart.gen.entities.Job.Effect;

@Service
public class JobRequestToJobMapper {

	/*
	 * public Job(String id, Format format, String text, EnumSet<Effect> effects,
	 * Colour fontColour, Font font, Status status)
	 */
	public Job map(JobRequest jobRequest) {
		return new Job(UUID.randomUUID(),
				Job.Format.valueOf(jobRequest.getFormat().toString()),
				jobRequest.getText(),
				jobRequest.getEffect() == null ? null : getEffects(jobRequest.getEffect()),
				Job.Colour.valueOf(jobRequest.getColour().toString()),
				Job.Font.fromValue(jobRequest.getFont().toString()),
				Job.Status.ACCEPTED);
	}

	private EnumSet<Effect> getEffects(List<String> effects) {
		return EnumSet.copyOf(effects.stream().map(Job.Effect::valueOf).collect(Collectors.toSet()));
	}

}
