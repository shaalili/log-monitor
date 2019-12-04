package com.sinosoft.monitor.strategy;

import java.util.Map;

import com.sinosoft.monitor.LogView;

/**
 * websocket发送信息策略接口类
 *
 * @author：yangli	
 * @date:2019年11月26日 下午2:02:28
 * @version 1.0
 */
public interface Strategy {

	/**
	 * 根据不同传入参数发送不同类型数据
	 * 
	 * @param filePath 前端传入参数（1.获取文件夹下所有可读日志文件，将结果发送给前台。2.将日志文件新增部分发送给前台）
	 * @param uCode
	 * @throws Exception 直接将异常抛出
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 下午2:19:53 
	 *
	 */
	void sendMessage(String filePath, String uCode) throws Exception;
	
	/**
	 * 设置viewMap
	 * 
	 * @param logViewMap
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月26日 下午6:17:16 
	 *
	 */
	void setLogViewMap(Map<String, LogView> logViewMap);
}
