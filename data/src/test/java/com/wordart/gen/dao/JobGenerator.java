package com.wordart.gen.dao;

import java.util.EnumSet;
import java.util.UUID;

import com.wordart.gen.entities.Job;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class JobGenerator {

	
	private static EnumSet<Job.Effect> generateRandomEffects()
	{
		int randomEffectCount = RandomUtils.nextInt(0, Job.Effect.values().length-1);
		EnumSet<Job.Effect> effects = EnumSet.noneOf(Job.Effect.class);
		for (int i = 0; i <= randomEffectCount; i++) {
			effects.add(Job.Effect.values()[RandomUtils.nextInt(0, Job.Effect.values().length-1)]);			
		}
		return effects;
	}

	public static Job generateJob()
	{
		String randomText = RandomStringUtils.random(15);
		Job.Format format = Job.Format.values()[RandomUtils.nextInt(0, Job.Format.values().length-1)];
		EnumSet<Job.Effect> effects = generateRandomEffects();
		Job.Colour colour = Job.Colour.values()[RandomUtils.nextInt(0, Job.Colour.values().length-1)];
		return new Job(UUID.randomUUID(), format , randomText , effects, colour, Job.Font.FREE_MONO,
				Job.Status.ACCEPTED);
	}
}
