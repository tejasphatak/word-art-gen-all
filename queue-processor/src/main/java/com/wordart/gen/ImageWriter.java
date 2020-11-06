package com.wordart.gen;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.wordart.gen.entities.Job;
import com.wordart.gen.util.Constants;

@Service
public class ImageWriter {

	private final String path;
	private Logger logger = LoggerFactory.getLogger(ImageWriter.class);
	
	public ImageWriter(@Value("${word-art-gen-path:/tmp/}") String path) {
		this.path = path;
		logger.info("Generated Word Art Files will be placed at {}", path);
	}

	public void write(InputStream stream, Job job) throws IOException {
		Path filePath = Paths.get(path, String.format(Constants.FILENAME_EXT_FORMAT, job.getId().toString(), job.getFormat().toString().toLowerCase()));
		FileUtils.copyInputStreamToFile(stream, filePath.toFile());
		logger.info("Job [{}] Word Art image copied to ->  [{}]", job.getId().toString() ,filePath.toAbsolutePath().toString());
	}


}
