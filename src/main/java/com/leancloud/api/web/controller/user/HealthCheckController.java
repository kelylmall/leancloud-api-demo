package com.leancloud.api.web.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.internal.InternalConfigurationController;

@RestController
public class HealthCheckController {
	@RequestMapping(value = { "/", "/__engine/1/ping" })
	@ResponseBody
	public String checkPing(@RequestHeader HttpHeaders headers,
			@RequestBody(required = false) String requestBody) {
		JSONObject result = new JSONObject();
		result.put("runtime", System.getProperty("java.version"));
		result.put("version", InternalConfigurationController.globalInstance()
				.getClientConfiguration().getUserAgent());
		return result.toJSONString();
	}

}
