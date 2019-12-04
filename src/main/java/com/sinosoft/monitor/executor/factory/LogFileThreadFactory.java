package com.sinosoft.monitor.executor.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 日志监控线程池线程创建工厂，用于定义线程名称，便于监控
 *
 * @author：yangli	
 * @date:2019年12月4日 上午11:19:11
 * @version 1.0
 */
public class LogFileThreadFactory implements ThreadFactory {
	private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;

	public LogFileThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "logFileMonitor-" + POOL_NUMBER.getAndIncrement() + "-thread-";
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
