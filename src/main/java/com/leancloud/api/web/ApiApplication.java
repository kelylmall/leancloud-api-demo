package com.leancloud.api.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.avos.avoscloud.AVOSCloud;
import com.leancloud.api.web.configuration.LeanCloudAppConfigurer;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.leancloud.api.web")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableConfigurationProperties({ LeanCloudAppConfigurer.class })
public class ApiApplication extends SpringBootServletInitializer {

	private static String appId="HM6nQFvp9GMEoY4O143lfKvq";
	private static String appKey="PqBoqihDG9gH2eCxI0wazAjf";
	private static String masterKey="UBAMc8xE3zvnxNFGrBX8wqu4";
	
	public static void main(String[] args) throws Exception {
		AVOSCloud.initialize(appId, appKey,masterKey);
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(getClass());
	}

	
}
