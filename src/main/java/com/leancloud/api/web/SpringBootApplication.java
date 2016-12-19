package com.leancloud.api.web;

import java.lang.reflect.Field;

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

import com.avos.avoscloud.internal.impl.JavaRequestSignImplementation;
import com.leancloud.api.web.configuration.LeanCloudAppConfigurer;
import com.leancloud.api.web.function.TestFuntion;
import com.leancloud.api.web.function.jni.HelloWorld;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.leancloud.api.web")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableConfigurationProperties({ LeanCloudAppConfigurer.class })
public class SpringBootApplication extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory
			.getLogger(SpringBootApplication.class);

	public static void main(String[] args) throws Exception {
		logger.debug("SpringBootApplication.run---start---");
		SpringApplication application = new SpringApplication(
				SpringBootApplication.class);
		application.run();
	}

	private static void loadJNILibDynamically() {
		try {
			System.setProperty(
					"java.library.path",
					System.getProperty("java.library.path")
							+ ":/Users/kelylmall/documents/workspace4/leancloud-api-demo/src/main/resources");
			ClassLoader.class.getDeclaredField("sys_paths");
			Field fieldSysPath = ClassLoader.class
					.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
			System.loadLibrary("hello");
			HelloWorld helloWorld = new HelloWorld();
			helloWorld.hello();
			System.out.println(System.getProperty("java.library.path"));
		} catch (Exception e) {
			// do nothing for exception
		}
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		// jni_lib库目录
		String contextPath = servletContext.getRealPath("/");
		String jni_lib_path = contextPath + "/jni_lib";

		System.setProperty("java.library.path",
				System.getProperty("java.library.path") + ":" + jni_lib_path);
		logger.info("SpringBootApplication.onStartup jni_lib_path:"
				+ System.getProperty("java.library.path"));
		try {
			ClassLoader.class.getDeclaredField("sys_paths");
			Field fieldSysPath = ClassLoader.class
					.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
			System.loadLibrary("hello");// 加载
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		super.onStartup(servletContext);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext(
			ServletContext servletContext) {
		return super.createRootApplicationContext(servletContext);
	}

	@Override
	protected SpringApplicationBuilder createSpringApplicationBuilder() {
		return super.createSpringApplicationBuilder();
	}

	@Override
	protected WebApplicationContext run(SpringApplication application) {
		logger.info("SpringBootApplication.run start--------------------------------------------");
		System.setProperty("LEANCLOUD_APP_PORT", "3000");
		System.setProperty("LEANCLOUD_APP_ID",
				"LrOxQaQGfylPVKwKyVbbEO2P-gzGzoHsz");
		System.setProperty("LEANCLOUD_APP_KEY", "yq9OoOTM1wbewKIE4kWEwBHJ");
		System.setProperty("LEANCLOUD_APP_MASTER_KEY",
				"DCiz0tQOEGEhE662XbzjO0I1");

		String appId = System.getProperty("LEANCLOUD_APP_ID");
		String appKey = System.getProperty("LEANCLOUD_APP_KEY");
		String appMasterKey = System.getProperty("LEANCLOUD_APP_MASTER_KEY");
		logger.info(
				"SpringBootApplication.run appId:{},appKey:{},appMasterKey:{}",
				appId, appKey, appMasterKey);

		LeanEngine.initialize(appId, appKey, appMasterKey);
		// 在请求签名中使用masterKey以激活云代码的最高权限
		JavaRequestSignImplementation.instance().setUseMasterKey(true);
		// 打开 debug 日志
		// AVOSCloud.setDebugLogEnabled(true);
		// 向云引擎注册云函数
		LeanEngine.register(TestFuntion.class);
		if ("development".equals(System.getenv("LEANCLOUD_APP_ENV"))) {
			// 如果是开发环境，则设置 AVCloud.callFunction 和 AVCloud.rpcFunction 调用本地云函数实现
			// 如果需要本地开发时调用云端云函数实现，则注释掉下面语句。
			LeanEngine.setLocalEngineCallEnabled(true);
		}

		return super.run(application);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(getClass());
	}

}
