package com.leancloud.api.web.function.jni;

import java.lang.reflect.Field;

public class HelloWorld {
	public native void hello();

	public native String testJni(String keyword);

	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		// 设置查找路径为当前项目路径
		System.setProperty(
				"java.library.path",
				System.getProperty("java.library.path")
						+ ":/Users/kelylmall/documents/workspace4/leancloud-api-demo/src/main/webapp/jni_lib");
		ClassLoader.class.getDeclaredField("sys_paths");
		Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
		fieldSysPath.setAccessible(true);
		fieldSysPath.set(null, null);
		// 加载动态库的名称
		System.loadLibrary("hello");
		String testJni = new HelloWorld().testJni("1111");
		System.out.println(testJni);
	}
}
