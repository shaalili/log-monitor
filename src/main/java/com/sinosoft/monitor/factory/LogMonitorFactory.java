package com.sinosoft.monitor.factory;

import com.sinosoft.monitor.constant.LogConstant;
import com.sinosoft.monitor.strategy.Strategy;
import com.sinosoft.monitor.strategy.impl.DownloadLogFileStratrgy;
import com.sinosoft.monitor.strategy.impl.RealtimeShowLogStrategy;
import com.sinosoft.monitor.strategy.impl.SearchFileStrategy;

/**
 * 策略生成工厂
 *
 * @author：yangli	
 * @date:2019年12月3日 上午9:50:07
 * @version 1.0
 */
public class LogMonitorFactory {

	/**
	 * 根据传入参数不同。获取相对应的策略
	 * 
	 * @param requestType
	 * @return
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月3日 上午9:50:26 
	 *
	 */
	public Strategy getStrategy(String requestType) {
		Strategy strategy = null;
		if (LogConstant.REQUEST_TYPE.equals(requestType)) {
			//遍历所有可读文件夹
			strategy = new SearchFileStrategy();
		} else if(requestType.startsWith(LogConstant.REQUEST_TYPE_DOWNLOAD)){
			//下载日志文件
			strategy = new DownloadLogFileStratrgy();
		} else {
			//读取选中日志文件
			strategy = new RealtimeShowLogStrategy();
		}
		return strategy;
	}
}
