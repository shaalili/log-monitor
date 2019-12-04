package com.sinosoft.monitor.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.sinosoft.monitor.executor.factory.LogFileThreadFactory;

/**
 * 日志监控线程工具类
 *
 * @author：yangli	
 * @date:2019年12月4日 下午2:05:01
 * @version 1.0
 */
public class ThreadPoolUtil {
	
	/**
	 * 用于记录每个页面线程返回的用于取消或者检查的任务对象
	 */
	private static Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	/**
	 * 延时线程池(暂定线程池大小为3)
	 */
	public static ScheduledExecutorService exec = new ScheduledThreadPoolExecutor(3, new LogFileThreadFactory());
	
	/**
	 * 将线程池执行结果以用户代码为键进行存储
	 * 
	 * @param uCode
	 * @param scheduledFuture
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 下午2:07:36 
	 *
	 */
	public static void put(String uCode, ScheduledFuture<?> scheduledFuture) {
		scheduledFutureMap.put(uCode, scheduledFuture);
	}
	
	/**
	 * 用于websocket连接断开时，关闭线程
	 * 
	 * @param uCode
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 下午2:08:16 
	 *
	 */
	public static void stop(String uCode) {
		ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(uCode);
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}
	}
	
	/**
	 * 关闭线程池
	 * 
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 下午2:08:41 
	 *
	 */
	public static void shutDown() {
		exec.shutdown();
	}
}
