package com.leancloud.api.web.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "leancloudApp", locations = "classpath:config/api-conf.properties")
@Component
public class LeanCloudAppConfigurer {

	private String appId;

	private String appKey;

	private String masterKey;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

}
