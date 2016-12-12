package com.leancloud.api.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = { RestController.class })
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String exceptionHandle(Exception ex) {
		logger.error("exceptionHandle {}", ex);
		return "500";
	}
}
