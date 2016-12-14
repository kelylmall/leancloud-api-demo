package com.leancloud.api.web.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.leancloud.api.web.entity.Todo;

@RestController
@RequestMapping(value = "/api")
public class TestController {

	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody) {

		return "test";
	}

	@RequestMapping(value = "/testSaveDb")
	@ResponseBody
	public AVObject testSaveDb(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody) {
		AVObject todo = new AVObject("Todo");
		todo.put("title", "工程师周会");
		todo.put("content", "每周工程师会议，周一下午2点");
		try {
			todo.save();
			// 保存成功
		} catch (AVException e) {
			// 失败的话，请检查网络环境以及 SDK 配置是否正确
			e.printStackTrace();
		}
		return todo;
	}

	@RequestMapping(value = "/testSaveDb1")
	@ResponseBody
	public Todo testSaveDb1(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody(required = false) String requestBody) {
		Todo todo = new Todo();
		todo.setContent("222222");
		try {
			todo.save();
			// 保存成功
		} catch (AVException e) {
			// 失败的话，请检查网络环境以及 SDK 配置是否正确
			e.printStackTrace();
		}
		return todo;
	}
	
	
	
	
	
}
