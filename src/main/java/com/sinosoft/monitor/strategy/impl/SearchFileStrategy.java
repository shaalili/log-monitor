package com.sinosoft.monitor.strategy.impl;

import java.util.Map;
import java.util.Set;

import com.sinosoft.monitor.constant.LogConstant;
import com.sinosoft.monitor.strategy.BaseParentStrategy;
import com.sinosoft.monitor.util.LogFileSuchUtil;
import com.sinosoft.monitor.util.SessionUtils;

/**
 * ��ȡĿ��Ŀ¼�µ����з��Ϲ�����־�ļ�������
 *
 * @author��yangli	
 * @date:2019��11��26�� ����2:14:07
 * @version 1.0
 */
public class SearchFileStrategy extends BaseParentStrategy {

	@Override
	public void sendMessage(String filePath, String uCode) throws Exception {
		StringBuilder resultHtml = new StringBuilder();
		LogConstant.init();
		Map<String, String> allLogFile = LogFileSuchUtil.getAllLogFile(LogConstant.FOLDER_PATH, LogConstant.RECURSIVE_DEPTH);
		LogFileSuchUtil.put(uCode, allLogFile);
		Set<String> keySet = allLogFile.keySet();
		for (String string : keySet) {
			resultHtml.append("<li><a href=\"#\">" + string + "</a></li>");
		}
		SessionUtils.get(uCode).getBasicRemote().sendText(resultHtml.toString());
	}

}
