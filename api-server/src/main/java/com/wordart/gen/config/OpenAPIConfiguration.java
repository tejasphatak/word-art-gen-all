package com.wordart.gen.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.parser.OpenAPIParser;
import io.swagger.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.core.models.ParseOptions;

@Configuration
@ComponentScan(basePackages= {"com.wordart.gen"})
public class OpenAPIConfiguration {

	@Bean
	public OpenAPI openapi(@Value("classpath:/api-v1.yaml") Resource openapiResource,
			@Value("${open-api.word-art-generator.base-path:/v1}") String apiBasePath) throws IOException {
		try (InputStream is = openapiResource.getInputStream()) {
			OpenAPI openAPI = new OpenAPIParser()
					.readContents(StreamUtils.copyToString(is, Charset.defaultCharset()), null, new ParseOptions())
					.getOpenAPI();
			ObjectNode node = Yaml.mapper().readValue(openapiResource.getInputStream(), ObjectNode.class);
			if (node.get("servers") == null) {
				openAPI.setServers(Collections.singletonList(new Server().url(apiBasePath)));
			}
			return openAPI;
		}
	}
}
