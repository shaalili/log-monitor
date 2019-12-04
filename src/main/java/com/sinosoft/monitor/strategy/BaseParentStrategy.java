package com.sinosoft.monitor.strategy;

import java.util.Map;

import com.sinosoft.monitor.LogView;

/**
 * ���Ը���������
 *
 * @author��yangli	
 * @date:2019��11��26�� ����2:19:17
 * @version 1.0
 */
public abstract class BaseParentStrategy implements Strategy{

	/**
	 * ���ڼ�¼��ǰ�����û�map
	 */
	protected Map<String, LogView> logViewMap;
	
	@Override
	public void setLogViewMap(Map<String, LogView> logViewMap) {
		this.logViewMap = logViewMap;
	}
}
