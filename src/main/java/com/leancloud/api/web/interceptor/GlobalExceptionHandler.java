package com.leancloud.api.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.avos.avoscloud.AVException;
import com.leancloud.api.web.resp.BaseResp;
import com.leancloud.api.web.resp.ClientStateCode;

@ControllerAdvice(annotations = { RestController.class })
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = AVException.class)
	@ResponseBody
	public BaseResp exceptionHandle(AVException ex) {
		logger.error("GlobalExceptionHandler.exceptionHandle:{}", ex);
		BaseResp baseResp = new BaseResp(ClientStateCode.SYSTEM_ERR);
		return baseResp;
	}
}
