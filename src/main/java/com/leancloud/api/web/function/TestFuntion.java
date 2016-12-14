package com.leancloud.api.web.function;

import cn.leancloud.EngineFunction;

public class TestFuntion {

	@EngineFunction("test")
	public static String hello() {
		return "TestFuntion!111111";
	}

}
