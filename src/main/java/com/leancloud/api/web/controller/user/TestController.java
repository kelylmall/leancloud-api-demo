package com.leancloud.api.web.controller.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.leancloud.api.web.function.jni.HelloWorld;
import com.leancloud.api.web.resp.BaseResp;
import com.leancloud.api.web.resp.ClientStateCode;

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
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		new HelloWorld().hello();
		return realPath;
	}

	@RequestMapping(value = "/testJni")
	@ResponseBody
	public String testJni(
			@RequestHeader HttpHeaders headers,
			@RequestParam(value = "key", required = false, defaultValue = "jni") String key,
			@RequestBody(required = false) String requestBody) {
		String testJni = new HelloWorld().testJni(key);
		return testJni;
	}

	// 数据存储
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

	// 从数据流构建文件上传
	@RequestMapping(value = "/uploadFile", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	@ResponseBody
	public BaseResp uploadFile(@RequestHeader HttpHeaders headers,
			@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request) throws Exception {
		boolean multipartContent = ServletFileUpload
				.isMultipartContent(request);// 检查输入请求是否为multipart
		BaseResp baseResp = null;
		if (multipartContent) {
			if (file == null) {
				baseResp = new BaseResp(ClientStateCode.file_empty);
			} else {
				String originalFilename = file.getOriginalFilename();
				AVFile avFile = new AVFile(originalFilename, file.getBytes());
				avFile.save();
				String url = avFile.getUrl();
				String objectId = avFile.getObjectId();
				JSONObject result = new JSONObject();
				result.put("url", url);
				result.put("objectId", objectId);
				baseResp = new BaseResp(ClientStateCode.SUCCESS);
				baseResp.setData(result);
			}
		} else {
			baseResp = new BaseResp(ClientStateCode.uploadfile_type_err);
		}
		return baseResp;
	}

	// 上传网络图片
	@RequestMapping(value = "/uploadNetworkFile", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	@ResponseBody
	public BaseResp uploadNetworkFile(@RequestHeader HttpHeaders headers,
			HttpServletRequest request) throws Exception {
		AVFile avFile = new AVFile(
				"test.gif",
				"http://ww3.sinaimg.cn/bmiddle/596b0666gw1ed70eavm5tg20bq06m7wi.gif",
				new HashMap<String, Object>());
		avFile.save();
		String url = avFile.getUrl();
		String objectId = avFile.getObjectId();
		JSONObject result = new JSONObject();
		result.put("url", url);
		result.put("objectId", objectId);
		BaseResp baseResp = new BaseResp(ClientStateCode.SUCCESS);
		baseResp.setData(result);
		return baseResp;
	}

	// 从数据流构建文件上传
	@RequestMapping(value = "/uploadStringFile", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	@ResponseBody
	public BaseResp uploadFile(@RequestHeader HttpHeaders headers,
			HttpServletRequest request) throws Exception {
		AVFile avFile = new AVFile("test.txt", "sdasdasdasdas".getBytes());
		avFile.save();
		String url = avFile.getUrl();
		String objectId = avFile.getObjectId();
		JSONObject result = new JSONObject();
		result.put("url", url);
		result.put("objectId", objectId);
		BaseResp baseResp = new BaseResp(ClientStateCode.SUCCESS);
		baseResp.setData(result);

		return baseResp;
	}

	@RequestMapping(value = "/download/{objectId}", method = RequestMethod.GET)
	public ResponseEntity<Object> downloadFile(
			@PathVariable("objectId") String objectId) {

		AVFile avFile = null;
		try {
			avFile = AVFile.withObjectId(objectId);
		} catch (FileNotFoundException | AVException e1) {
			e1.printStackTrace();

			BaseResp baseResp = new BaseResp(ClientStateCode.file_not_exists);
			return ResponseEntity
					.ok()
					.contentType(
							MediaType
									.parseMediaType("application/json;charset=UTF-8"))
					.body(JSON.toJSONString(baseResp));
		}

		String originalName = avFile.getOriginalName();
		int size = avFile.getSize();
		String fileUrl = avFile.getUrl();
		InputStream inputStream = null;
		try {
			inputStream = getInputStream(fileUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Content-Disposition",
					String.format("attachment; filename=\"%s\"", originalName));
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(size)
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new InputStreamResource(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
			BaseResp baseResp = new BaseResp(ClientStateCode.file_not_exists);
			return ResponseEntity
					.ok()
					.contentType(
							MediaType
									.parseMediaType("application/json;charset=UTF-8"))
					.body(JSON.toJSONString(baseResp));
		}
	}

	private InputStream getInputStream(String fileUrl) throws IOException {
		URL url;
		url = new URL(fileUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(30 * 1000);
		InputStream inputStream = conn.getInputStream();// 通过输入流获取图片数据
		return inputStream;
	}

}
