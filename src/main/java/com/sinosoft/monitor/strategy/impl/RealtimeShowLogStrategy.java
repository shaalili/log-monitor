package com.sinosoft.monitor.strategy.impl;

import com.sinosoft.monitor.LogView;
import com.sinosoft.monitor.strategy.BaseParentStrategy;
import com.sinosoft.monitor.util.LogFileSuchUtil;
import com.sinosoft.monitor.util.SessionUtils;

/**
 * ��ȡ�ļ��������ҷ���ǰ̨������
 *
 * @author��yangli	
 * @date:2019��11��26�� ����2:15:54
 * @version 1.0
 */
public class RealtimeShowLogStrategy extends BaseParentStrategy {

	@Override
	public void sendMessage(String filePath, String uCode) throws Exception {
		filePath = LogFileSuchUtil.get(uCode, filePath);
		if (SessionUtils.hasConnection(uCode)) {
			LogView logView = logViewMap.get(uCode);
			if (logView == null) {
				logView = new LogView();
				logViewMap.put(uCode, logView);
			}
			logView.realtimeShowLog(filePath, uCode);
		} else {
			SessionUtils.get(uCode).getBasicRemote().sendText("����ʧЧ����ˢ��ҳ�棬��������");
		}
	}
}
