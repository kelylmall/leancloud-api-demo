package com.leancloud.api.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.leancloud.LeanEngine;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.internal.impl.JavaRequestSignImplementation;

@WebListener
public class AppInitListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory
			.getLogger(AppInitListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
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
		AVOSCloud.setDebugLogEnabled(true);
		// 云引擎初始化
		try {

			LeanEngine.initialize(appId, appKey, appMasterKey);
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
	}
}
