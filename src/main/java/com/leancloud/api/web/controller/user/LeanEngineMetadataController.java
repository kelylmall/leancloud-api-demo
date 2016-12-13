package com.leancloud.api.web.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeanEngineMetadataController {

	@RequestMapping(value = "/1.1/functions/_ops/metadatas", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public String check(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody) {
		return "{\"result\":[]}";
	}
}
