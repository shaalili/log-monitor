package com.sinosoft.monitor.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.sinosoft.monitor.executor.factory.LogFileThreadFactory;

/**
 * ��־����̹߳�����
 *
 * @author��yangli	
 * @date:2019��12��4�� ����2:05:01
 * @version 1.0
 */
public class ThreadPoolUtil {
	
	/**
	 * ���ڼ�¼ÿ��ҳ���̷߳��ص�����ȡ�����߼����������
	 */
	private static Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	/**
	 * ��ʱ�̳߳�(�ݶ��̳߳ش�СΪ3)
	 */
	public static ScheduledExecutorService exec = new ScheduledThreadPoolExecutor(3, new LogFileThreadFactory());
	
	/**
	 * ���̳߳�ִ�н�����û�����Ϊ�����д洢
	 * 
	 * @param uCode
	 * @param scheduledFuture
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��4�� ����2:07:36 
	 *
	 */
	public static void put(String uCode, ScheduledFuture<?> scheduledFuture) {
		scheduledFutureMap.put(uCode, scheduledFuture);
	}
	
	/**
	 * ����websocket���ӶϿ�ʱ���ر��߳�
	 * 
	 * @param uCode
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��4�� ����2:08:16 
	 *
	 */
	public static void stop(String uCode) {
		ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(uCode);
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}
	}
	
	/**
	 * �ر��̳߳�
	 * 
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��4�� ����2:08:41 
	 *
	 */
	public static void shutDown() {
		exec.shutdown();
	}
}
