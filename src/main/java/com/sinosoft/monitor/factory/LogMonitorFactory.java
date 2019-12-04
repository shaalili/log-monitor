package com.sinosoft.monitor.factory;

import com.sinosoft.monitor.constant.LogConstant;
import com.sinosoft.monitor.strategy.Strategy;
import com.sinosoft.monitor.strategy.impl.DownloadLogFileStratrgy;
import com.sinosoft.monitor.strategy.impl.RealtimeShowLogStrategy;
import com.sinosoft.monitor.strategy.impl.SearchFileStrategy;

/**
 * �������ɹ���
 *
 * @author��yangli	
 * @date:2019��12��3�� ����9:50:07
 * @version 1.0
 */
public class LogMonitorFactory {

	/**
	 * ���ݴ��������ͬ����ȡ���Ӧ�Ĳ���
	 * 
	 * @param requestType
	 * @return
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��3�� ����9:50:26 
	 *
	 */
	public Strategy getStrategy(String requestType) {
		Strategy strategy = null;
		if (LogConstant.REQUEST_TYPE.equals(requestType)) {
			//�������пɶ��ļ���
			strategy = new SearchFileStrategy();
		} else if(requestType.startsWith(LogConstant.REQUEST_TYPE_DOWNLOAD)){
			//������־�ļ�
			strategy = new DownloadLogFileStratrgy();
		} else {
			//��ȡѡ����־�ļ�
			strategy = new RealtimeShowLogStrategy();
		}
		return strategy;
	}
}
