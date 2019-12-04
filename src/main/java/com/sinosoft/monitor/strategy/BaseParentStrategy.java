package com.sinosoft.monitor.strategy;

import java.util.Map;

import com.sinosoft.monitor.LogView;

/**
 * 策略父级抽象类
 *
 * @author：yangli	
 * @date:2019年11月26日 下午2:19:17
 * @version 1.0
 */
public abstract class BaseParentStrategy implements Strategy{

	/**
	 * 用于记录当前访问用户map
	 */
	protected Map<String, LogView> logViewMap;
	
	@Override
	public void setLogViewMap(Map<String, LogView> logViewMap) {
		this.logViewMap = logViewMap;
	}
}
