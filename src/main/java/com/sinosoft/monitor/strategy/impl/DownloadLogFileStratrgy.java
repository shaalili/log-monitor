package com.sinosoft.monitor.strategy.impl;

import java.io.File;

import com.sinosoft.monitor.constant.LogConstant;
import com.sinosoft.monitor.strategy.BaseParentStrategy;
import com.sinosoft.monitor.util.LogFileSuchUtil;
import com.sinosoft.monitor.util.SessionUtils;
import com.sinosoft.monitor.zip.ReduceFileUtil;

/**
 * 下载选定日志文件策略类
 *
 * @author：yangli	
 * @date:2019年12月2日 下午2:29:06
 * @version 1.0
 */
public class DownloadLogFileStratrgy extends BaseParentStrategy{

	/**
	 * 根据文件名称，将其保存到项目下并且进行下载
	 * 
	 * @param filePath 文件名称
	 * @param uCode
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月2日 下午2:29:45 
	 *
	 */
	@Override
	public void sendMessage(String filePath, String uCode) throws Exception {
		//删除以前生成日志中间文件
		deleteHistoryLogFile(LogConstant.ZIP_FILE_PATH);
		filePath = filePath.substring(8);
		String absoluteFile = LogFileSuchUtil.get(uCode, filePath);
		//将传入的文件名在缓存中去除其绝对路径，并且进行读取
		File reduceFileName = ReduceFileUtil.reduceFile(new File(absoluteFile));
		//将下载路径返回给用户
		SessionUtils.get(uCode).getBasicRemote().sendText(LogConstant.REQUEST_TYPE_DOWNLOAD + LogConstant.ZIP_FILE_DOWNLOAD_PATH + File.separator + reduceFileName.getName());
	}
	
	
	/**
	 * 删除上次下载日志生成的中间日志文件压缩包
	 * 
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 上午10:05:36 
	 *
	 */
	private void deleteHistoryLogFile(String zipFilePath) {
		File file = new File(zipFilePath);
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			file2.delete();
		}
		
	}
}