package com.leancloud.api.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.leancloud.LeanEngine;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.internal.impl.JavaRequestSignImplementation;
import com.leancloud.api.web.configuration.LeanCloudAppConfigurer;

@WebListener
@Component
public class AppInitListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory
			.getLogger(AppInitListener.class);

	@Autowired
	private LeanCloudAppConfigurer leanCloudAppConfigurer;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("AppInitListener.contextInitialized--------------------------------------------");
		String appId = leanCloudAppConfigurer.getAppId();
		String appKey = leanCloudAppConfigurer.getAppKey();
		String appMasterKey = leanCloudAppConfigurer.getMasterKey();
		// 数据存储初始化
		AVOSCloud.initialize(appId, appKey, appMasterKey);
		AVOSCloud.setDebugLogEnabled(true);
		// 云引擎初始化
		try {
			System.setProperty("LEANCLOUD_APP_PORT", "3000");
			LeanEngine.initialize(appId, appKey, appMasterKey);
			// 在请求签名中使用masterKey以激活云代码的最高权限
			JavaRequestSignImplementation.instance().setUseMasterKey(true);
			if (System.getenv("LEANCLOUD_APP_ENV").equals("development")) {
				// 如果是开发环境，则设置 AVCloud.callFunction 和 AVCloud.rpcFunction
				// 调用本地云函数实现
				// 如果需要本地开发时调用云端云函数实现，则注释掉下面语句。
				LeanEngine.setLocalEngineCallEnabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
