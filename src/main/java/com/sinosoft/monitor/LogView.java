package com.sinosoft.monitor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.sinosoft.monitor.constant.LogConstant;
import com.sinosoft.monitor.executor.ThreadPoolUtil;
import com.sinosoft.monitor.util.SessionUtils;

/**
 * 用于读取指定日志文件增量类
 *
 * @author：yangli	
 * @date:2019年12月4日 上午11:19:51
 * @version 1.0
 */
public class LogView {
	/**
	 * 上次文件大小
	 */
	private long lastTimeFileSize = 0;
	/**
	 * 文件流
	 */
	private RandomAccessFile randomFile;

	/**
	 * 事实输出日志信息
	 * 
	 * @param filePath
	 * @param uCode
	 * @throws IOException
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 上午11:20:27 
	 *
	 */
	public void realtimeShowLog(String filePath, final String uCode) throws IOException {
		File logFile = new File(filePath);
		lastTimeFileSize = logFile.length();
		randomFile = new RandomAccessFile(logFile, LogConstant.FILE_PERMISSION);
		ScheduledFuture<?> scheduleWithFixedDelay = ThreadPoolUtil.exec.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					randomFile.seek(lastTimeFileSize);
					String tmp = "";
					while ((tmp = randomFile.readLine()) != null) {
						SessionUtils.get(uCode).getBasicRemote().sendText(new String(tmp.getBytes("ISO-8859-1"), "gbk"));
					}
					lastTimeFileSize = randomFile.length();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
		ThreadPoolUtil.put(uCode, scheduleWithFixedDelay);
	}
}
