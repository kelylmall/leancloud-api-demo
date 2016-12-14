package com.leancloud.api.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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
import org.springframework.web.context.WebApplicationContext;

import cn.leancloud.LeanEngine;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.internal.impl.JavaRequestSignImplementation;
import com.leancloud.api.web.configuration.LeanCloudAppConfigurer;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.leancloud.api.web")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableConfigurationProperties({ LeanCloudAppConfigurer.class })
public class SpringBootApplication extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory
			.getLogger(SpringBootApplication.class);

	public static void main(String[] args) throws Exception {
		logger.debug("SpringBootApplication.run-----------------------------------------------------------------------");
		AVOSCloud.initialize("LrOxQaQGfylPVKwKyVbbEO2P-gzGzoHsz","yq9OoOTM1wbewKIE4kWEwBHJ", "DCiz0tQOEGEhE662XbzjO0I1");
		
		
		SpringApplication.run(SpringBootApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		super.onStartup(servletContext);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		System.setProperty("LEANCLOUD_APP_PORT", "3000");
		System.setProperty("LEANCLOUD_APP_ID",
				"LrOxQaQGfylPVKwKyVbbEO2P-gzGzoHsz");
		System.setProperty("LEANCLOUD_APP_KEY", "yq9OoOTM1wbewKIE4kWEwBHJ");
		System.setProperty("LEANCLOUD_APP_MASTER_KEY",
				"DCiz0tQOEGEhE662XbzjO0I1");
		logger.info("AppInitListener.contextInitialized start--------------------------------------------");
		String appId = System.getProperty("LEANCLOUD_APP_ID");
		String appKey = System.getProperty("LEANCLOUD_APP_KEY");
		String appMasterKey = System.getProperty("LEANCLOUD_APP_MASTER_KEY");
		logger.info("appId:{},appKey:{},appMasterKey:{}", appId, appKey,
				appMasterKey);
		// 数据存储初始化
		AVOSCloud.initialize(appId, appKey, appMasterKey);
		logger.info("AVOSCloud.initialize end");
		AVOSCloud.setDebugLogEnabled(true);
		// 云引擎初始化
		try {
			LeanEngine.initialize(appId, appKey, appMasterKey);
			logger.info("LeanEngine.initialize end");
			// 在请求签名中使用masterKey以激活云代码的最高权限
			JavaRequestSignImplementation.instance().setUseMasterKey(true);
			if ("development".equals(System.getProperty("LEANCLOUD_APP_ENV"))) {
				// 如果是开发环境，则设置 AVCloud.callFunction 和 AVCloud.rpcFunction
				// 调用本地云函数实现
				// 如果需要本地开发时调用云端云函数实现，则注释掉下面语句。
				LeanEngine.setLocalEngineCallEnabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AppInitListener.contextInitialized end--------------------------------------------");
		
		
		logger.debug("SpringBootApplication.configure--------------createRootApplicationContext-----------");
		return super.createRootApplicationContext(servletContext);
	}

	@Override
	protected SpringApplicationBuilder createSpringApplicationBuilder() {
		logger.debug("SpringBootApplication.configure---------------createSpringApplicationBuilder--------");
		return super.createSpringApplicationBuilder();
	}

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.debug("SpringBootApplication.configure---------------configure-----------------");
		return application.sources(getClass());
	}
	
	@Override
	protected WebApplicationContext run(SpringApplication application) {
		logger.debug("SpringBootApplication.configure---------------run-------------------");
		return super.run(application);
	}


}
