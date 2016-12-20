package com.leancloud.api.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.leancloud.api.web.entity.Todo;
import com.leancloud.api.web.function.jni.HelloWorld;

@RestController
@RequestMapping(value = "/api")
public class TestController {

	public static Logger logger = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody,
			HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		new HelloWorld().hello();
		return realPath;
	}

	@RequestMapping(value = "/testJni")
	@ResponseBody
	public String testJni(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false,defaultValue="jni") String key,
			@RequestBody(required = false) String requestBody) {
		String testJni = new HelloWorld().testJni(key);
		return testJni;
	}

	@RequestMapping(value = "/testSaveDb", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AVObject testSaveDb(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody)
			throws AVException {
		AVObject todo = new AVObject("Todo");
		todo.put("title", "工程师周会");
		todo.put("content", "每周工程师会议，周一下午2点");
		todo.save();
		return todo;
	}

	@RequestMapping(value = "/testSaveDb1", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Todo testSaveDb1(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody)
			throws AVException {
		Todo todo = new Todo();
		todo.setContent("222222");
		logger.debug(JSON.toJSONString(todo));
		todo.save();
		return todo;
	}

}
