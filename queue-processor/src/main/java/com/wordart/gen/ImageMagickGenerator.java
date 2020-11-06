package com.wordart.gen;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.wordart.gen.entities.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A {@link Generator} implementation that uses the image magick library's
 * convert command (expected to be on the system's path) to render images from
 * text.
 */
@Component
public class ImageMagickGenerator implements Generator{

  private Logger logger = LoggerFactory.getLogger(ImageMagickGenerator.class);
	
  @Override
  public InputStream generateImage(Job job) throws IOException {

    final int pointsize = 48;

    List<String> command = new ArrayList<>();
    command.add("convert");
    command.add("-size");
    command.add("400x100");
    command.add("xc:white");
    command.add("-font");
    command.add(job.getFont().getFontName());

    command.add("-pointsize");
    command.add(Integer.toString(pointsize));

    final int x = 25;
    final int y = 25 + pointsize;

    if (job.getEffects().contains(Job.Effect.SHADOW_HARD)) {
      int shadowoffset = pointsize / 12;

      command.add("-fill");
      command.add(Job.Colour.GREY.toString().toLowerCase());
      command.add("-annotate");
      command.add(String.format("+%d+%d", x - shadowoffset, y - shadowoffset));
      command.add(job.getText());
    }

    if (job.getEffects().contains(Job.Effect.SHADOW_REFLECT)) {

      command.add("-fill");
      command.add(Job.Colour.GREY.toString().toLowerCase());
      command.add("-annotate");
      command.add(String.format("0x45+%d+%d", x, y));
      command.add(job.getText());
    }

    if (job.getEffects().contains(Job.Effect.OUTLINE)) {
      command.add("-stroke");
      command.add("black");
    }

    if (job.getEffects().contains(Job.Effect.GRADIENT)) {
      command.add("-tile");
      command.add(String.format("gradient:%s-%s", Job.Colour.WHITE.toString().toLowerCase(), job.getFontColour().toString().toLowerCase()));
    } else {
      command.add("-fill");
      command.add(job.getFontColour().toString().toLowerCase());
    }
    command.add("-annotate");
    command.add(String.format("+%d+%d", x, y));
    command.add(job.getText());

    command.add(String.format("%s:-",  job.getFormat().toString().toLowerCase()));

    logger.info("Executing command -> {} ", command.toString());
    ProcessBuilder builder = new ProcessBuilder(command);
    Process process = builder.start();
    return process.getInputStream();
  }

}
