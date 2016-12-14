package com.leancloud.api.web.resp;

import java.io.Serializable;

public class BaseResp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String retCode;
	private String retMsg;
	private Object data;

	public BaseResp() {

	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
