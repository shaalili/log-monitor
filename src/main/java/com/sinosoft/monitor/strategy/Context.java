package com.sinosoft.monitor.strategy;

import java.util.Map;

import com.sinosoft.monitor.LogView;

/**
 * 调用不同发送策略操作类
 *
 * @author：yangli	
 * @date:2019年11月26日 下午2:08:25
 * @version 1.0
 */
public class Context {

	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void sendMessage(String filePath, String uCode) throws Exception {
		strategy.sendMessage(filePath, uCode);
	}
	
	public void setLogViewMap(Map<String, LogView> logViewMap) {
		strategy.setLogViewMap(logViewMap);
	}
}
