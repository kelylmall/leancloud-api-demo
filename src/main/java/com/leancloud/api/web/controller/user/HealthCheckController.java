package com.leancloud.api.web.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	@RequestMapping(value = "/")
	@ResponseBody
	public String check(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody) {
		return "ok";
	}

	@RequestMapping(value = "/__engine/1/ping")
	@ResponseBody
	public String checkPing(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody) {
		return "ok";
	}
}
