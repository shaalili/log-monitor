package com.sinosoft.monitor.util;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

/**
 * websocket session工具类
 *
 * @author：yangli	
 * @date:2019年12月4日 下午2:22:18
 * @version 1.0
 */
public class SessionUtils {
	
	/**
	 * <uCode,Session>的缓存
	 */
	public static Map<String, Session> clients = new ConcurrentHashMap<String, Session>();

	public static void put(String uCode, Session session) {
		clients.put(uCode, session);
	}

	public static Session get(String uCode) {
		return clients.get(uCode);
	}

	public static void remove(String uCode) {
		clients.remove(uCode);
	}

	public static void remove(Session session) {
		Iterator<java.util.Map.Entry<String, Session>> ito = clients.entrySet().iterator();
		while (ito.hasNext()) {
			java.util.Map.Entry<String, Session> entry = ito.next();
			if (entry.getValue().equals(session)) {
				remove(entry.getKey());
			}
		}
	}

	public static boolean hasConnection(String uCode) {
		return clients.containsKey(uCode);
	}
}
