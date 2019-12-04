package com.sinosoft.monitor.strategy.impl;

import java.io.File;

import com.sinosoft.monitor.constant.LogConstant;
import com.sinosoft.monitor.strategy.BaseParentStrategy;
import com.sinosoft.monitor.util.LogFileSuchUtil;
import com.sinosoft.monitor.util.SessionUtils;
import com.sinosoft.monitor.zip.ReduceFileUtil;

/**
 * ����ѡ����־�ļ�������
 *
 * @author��yangli	
 * @date:2019��12��2�� ����2:29:06
 * @version 1.0
 */
public class DownloadLogFileStratrgy extends BaseParentStrategy{

	/**
	 * �����ļ����ƣ����䱣�浽��Ŀ�²��ҽ�������
	 * 
	 * @param filePath �ļ�����
	 * @param uCode
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��2�� ����2:29:45 
	 *
	 */
	@Override
	public void sendMessage(String filePath, String uCode) throws Exception {
		//ɾ����ǰ������־�м��ļ�
		deleteHistoryLogFile(LogConstant.ZIP_FILE_PATH);
		filePath = filePath.substring(8);
		String absoluteFile = LogFileSuchUtil.get(uCode, filePath);
		//��������ļ����ڻ�����ȥ�������·�������ҽ��ж�ȡ
		File reduceFileName = ReduceFileUtil.reduceFile(new File(absoluteFile));
		//������·�����ظ��û�
		SessionUtils.get(uCode).getBasicRemote().sendText(LogConstant.REQUEST_TYPE_DOWNLOAD + LogConstant.ZIP_FILE_DOWNLOAD_PATH + File.separator + reduceFileName.getName());
	}
	
	
	/**
	 * ɾ���ϴ�������־���ɵ��м���־�ļ�ѹ����
	 * 
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��4�� ����10:05:36 
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