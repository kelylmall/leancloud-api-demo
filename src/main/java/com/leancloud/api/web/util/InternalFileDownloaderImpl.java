package com.leancloud.api.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.internal.InternalFileDownloader;

public class InternalFileDownloaderImpl implements InternalFileDownloader {

	private HttpURLConnection connection;

	private InputStream inputStream;
	private long contentLength;
	private byte[] bytes = new byte[1024];
	private long progress = 0;

	@Override
	public AVException doWork(String url) {
		try {
			URL downUrl = new URL(url);
			connection = (HttpURLConnection) downUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(30 * 1000);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			// 打开连接
			connection.connect();
			// 获取内容长度
			contentLength = connection.getContentLength();
			// 输入流
			inputStream = connection.getInputStream();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return new AVException(0, "");
		}
	}

	@Override
	public void execute(String url) {

	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public void setProgressCallback(ProgressCallback callback) {
		long totalReaded = 0;
		int temp_Len;
		try {
			while ((temp_Len = inputStream.read(bytes)) != -1) {
				totalReaded += temp_Len;
				progress = totalReaded * 100 / contentLength;
				callback.internalDone(Long.valueOf(progress).intValue(), null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setGetDataCallback(GetDataCallback callback) {
		byte[] byteArray = null;
		try {
			byteArray = IOUtils.toByteArray(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		callback.done(byteArray, null);
		if (progress == 100) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				inputStream=null;
			}
		}

	}

}
