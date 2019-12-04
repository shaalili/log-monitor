package com.sinosoft.monitor.strategy;

import java.util.Map;

import com.sinosoft.monitor.LogView;

/**
 * websocket������Ϣ���Խӿ���
 *
 * @author��yangli	
 * @date:2019��11��26�� ����2:02:28
 * @version 1.0
 */
public interface Strategy {

	/**
	 * ���ݲ�ͬ����������Ͳ�ͬ��������
	 * 
	 * @param filePath ǰ�˴��������1.��ȡ�ļ��������пɶ���־�ļ�����������͸�ǰ̨��2.����־�ļ��������ַ��͸�ǰ̨��
	 * @param uCode
	 * @throws Exception ֱ�ӽ��쳣�׳�
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��4�� ����2:19:53 
	 *
	 */
	void sendMessage(String filePath, String uCode) throws Exception;
	
	/**
	 * ����viewMap
	 * 
	 * @param logViewMap
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��26�� ����6:17:16 
	 *
	 */
	void setLogViewMap(Map<String, LogView> logViewMap);
}
