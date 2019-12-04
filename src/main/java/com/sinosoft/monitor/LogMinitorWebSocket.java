package com.sinosoft.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.sinosoft.monitor.executor.ThreadPoolUtil;
import com.sinosoft.monitor.factory.LogMonitorFactory;
import com.sinosoft.monitor.strategy.Context;
import com.sinosoft.monitor.strategy.Strategy;
import com.sinosoft.monitor.util.FileNameVerfiyUtil;
import com.sinosoft.monitor.util.LogFileSuchUtil;
import com.sinosoft.monitor.util.SessionUtils;

/**
 * 这里使用注释的方式来向系统指明，我的这个LogMinitorWebSocket是一个websocket。 同时指定了路径为/websocket/{uCode}
 * 其中的{uCode}是一个变化的参数，在网页端动态的输入。这样我就可以通过@PathParam("uCode")这个注释在参数中获得当前查询用户的唯一标识
 * uCode为页面随机生成的一个参数,用于标记不同日志打开的不同查询页面
 *
 * @author：yangli	
 * @date:2019年12月4日 下午2:11:09
 * @version 1.0
 */
@ServerEndpoint("/websocket/{uCode}")
public class LogMinitorWebSocket {

	/**
	 * 用于操作每个连接用户
	 */
	private Map<String, LogView> logViewMap = new ConcurrentHashMap<String, LogView>();

	/**
	 * 客户端发送消息处理类,当有消息传来时，该方法进行相关处理
	 * 
	 * @param filePath 查询日志文件名
	 * @param uCode 每个打开的页面的唯一标识
	 * @throws Exception 偷懒的人总是抛出一个EXCEPTION
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月28日 下午6:01:15 
	 *
	 */
	@OnMessage
	public void onMessage(String filePath, @PathParam("uCode") String uCode){
		try {
			ThreadPoolUtil.stop(uCode);
			if (FileNameVerfiyUtil.verifyFileName(filePath)) {
				LogMonitorFactory factory = new LogMonitorFactory();
				Strategy strategy = factory.getStrategy(filePath);
				Context context = new Context(strategy);
				context.setLogViewMap(logViewMap);
				context.sendMessage(filePath, uCode);
			} else {
				SessionUtils.get(uCode).getBasicRemote().sendText("传入文件名称非法");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 新建连接及一些初始化方法
	 * 
	 * @param uCode 每个打开的页面的唯一标识
	 * @param session 这个是用户建立的session信息，用来唯一标识这个用户的通信
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月28日 下午6:04:00 
	 *
	 */
	@OnOpen
	public void onOpen(@PathParam("uCode") String uCode, Session session) throws Exception {
		if (SessionUtils.hasConnection(uCode)) {
			SessionUtils.get(uCode).close();
			SessionUtils.remove(uCode);
			SessionUtils.put(uCode, session);
		} else {
			SessionUtils.put(uCode, session);
		}
	}

	/**
	 * 客户端断开连接调用方法，关闭线程等操作在这里
	 * 
	 * @param uCode 每个打开的页面的唯一标识
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月28日 下午6:04:38 
	 *
	 */
	@OnClose
	public void onClose(@PathParam("uCode") String uCode) {
		ThreadPoolUtil.stop(uCode);
		LogFileSuchUtil.remove(uCode);
		SessionUtils.remove(uCode);
		if (SessionUtils.clients.size() <= 0) {
			ThreadPoolUtil.shutDown();
		}
	}

	/**
	 * 连接异常调用方法，关闭线程等操作在这里
	 * 
	 * @param e 连接所出现的异常
	 * @param session 这个是用户建立的session信息，用来唯一标识这个用户的通信
	 * @param uCode 每个打开的页面的唯一标识
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月28日 下午6:05:17 
	 *
	 */
	@OnError
	public void onError(Throwable e, Session session, @PathParam("uCode") String uCode) {
		System.out.println(e);
		ThreadPoolUtil.stop(uCode);
		LogFileSuchUtil.remove(uCode);
		if (SessionUtils.clients.containsValue(session)) {
			SessionUtils.remove(session);
		}
		if (SessionUtils.clients.size() <= 0) {
			ThreadPoolUtil.shutDown();
		}
	}

}