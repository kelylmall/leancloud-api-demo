package com.leancloud.api.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	private static final Logger logger = LoggerFactory
			.getLogger(ScheduledTask.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private Integer count = 1;

	// cron = "*/5 * * * * * *"提供了一种通用的定时任务表达式，这里表示每隔5秒执行一次，更加详细的信息可以参考linux
	// cron表达式
	@Scheduled(cron = "* * */1 * * *")
	// 每秒执行执行一次
	public void reportCurrentTimeCron() {
		try {
			logger.info("+++第{}次执行，当前时间为：{}", count++,
					dateFormat.format(new Date()));
		} catch (Exception e) {// 线程异常
			e.printStackTrace();
		}
	}

}
