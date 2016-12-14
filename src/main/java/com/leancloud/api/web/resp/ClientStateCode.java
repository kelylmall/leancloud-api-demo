package com.leancloud.api.web.resp;

public enum ClientStateCode {
SUCCESS("00000","success"),
 SYSTEM_ERR("00001","system err"),
	;
	
	
	private String code;
	private String msg;
	
	
	private ClientStateCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
