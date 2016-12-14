package com.leancloud.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.leancloud.api.web.configuration.LeanCloudAppConfigurer;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.leancloud.api.web")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableConfigurationProperties({ LeanCloudAppConfigurer.class })
public class ApiApplication extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory
			.getLogger(ApiApplication.class);

	public static void main(String[] args) throws Exception {
		logger.debug("ApiApplication.run");
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(getClass());
	}

}
