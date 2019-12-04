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
 * ����ʹ��ע�͵ķ�ʽ����ϵͳָ�����ҵ����LogMinitorWebSocket��һ��websocket�� ͬʱָ����·��Ϊ/websocket/{uCode}
 * ���е�{uCode}��һ���仯�Ĳ���������ҳ�˶�̬�����롣�����ҾͿ���ͨ��@PathParam("uCode")���ע���ڲ����л�õ�ǰ��ѯ�û���Ψһ��ʶ
 * uCodeΪҳ��������ɵ�һ������,���ڱ�ǲ�ͬ��־�򿪵Ĳ�ͬ��ѯҳ��
 *
 * @author��yangli	
 * @date:2019��12��4�� ����2:11:09
 * @version 1.0
 */
@ServerEndpoint("/websocket/{uCode}")
public class LogMinitorWebSocket {

	/**
	 * ���ڲ���ÿ�������û�
	 */
	private Map<String, LogView> logViewMap = new ConcurrentHashMap<String, LogView>();

	/**
	 * �ͻ��˷�����Ϣ������,������Ϣ����ʱ���÷���������ش���
	 * 
	 * @param filePath ��ѯ��־�ļ���
	 * @param uCode ÿ���򿪵�ҳ���Ψһ��ʶ
	 * @throws Exception ͵�����������׳�һ��EXCEPTION
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��28�� ����6:01:15 
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
				SessionUtils.get(uCode).getBasicRemote().sendText("�����ļ����ƷǷ�");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * �½����Ӽ�һЩ��ʼ������
	 * 
	 * @param uCode ÿ���򿪵�ҳ���Ψһ��ʶ
	 * @param session ������û�������session��Ϣ������Ψһ��ʶ����û���ͨ��
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��28�� ����6:04:00 
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
	 * �ͻ��˶Ͽ����ӵ��÷������ر��̵߳Ȳ���������
	 * 
	 * @param uCode ÿ���򿪵�ҳ���Ψһ��ʶ
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��28�� ����6:04:38 
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
	 * �����쳣���÷������ر��̵߳Ȳ���������
	 * 
	 * @param e ���������ֵ��쳣
	 * @param session ������û�������session��Ϣ������Ψһ��ʶ����û���ͨ��
	 * @param uCode ÿ���򿪵�ҳ���Ψһ��ʶ
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��28�� ����6:05:17 
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