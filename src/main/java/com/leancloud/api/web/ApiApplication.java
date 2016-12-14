package com.leancloud.api.web;

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

	public static void main(String[] args) throws Exception {

		// String appId = System.getenv("LEANCLOUD_APP_ID");
		// String appKey = System.getenv("LEANCLOUD_APP_KEY");
		// String appMasterKey = System.getenv("LEANCLOUD_APP_MASTER_KEY");
		String appId = "LrOxQaQGfylPVKwKyVbbEO2P-gzGzoHsz";
		String appKey = "yq9OoOTM1wbewKIE4kWEwBHJ";
		String appMasterKey = "DCiz0tQOEGEhE662XbzjO0I1";
		AVOSCloud.initialize(appId, appKey, appMasterKey);
		// LeanEngine.initialize(appId, appKey, appMasterKey);
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(getClass());
	}

}
