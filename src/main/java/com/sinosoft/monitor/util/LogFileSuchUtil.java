package com.sinosoft.monitor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sinosoft.monitor.constant.LogConstant;

/**
 * ��־�ļ�������
 *
 * @author��yangli	
 * @date:2019��12��4�� ����2:14:55
 * @version 1.0
 */
public class LogFileSuchUtil {
	
	/**
	 * ���ڴ洢ÿ�������û�����ȡ�����ļ��������·��
	 */
	public static Map<String, Map<String, String>> logFileToUCode = new ConcurrentHashMap<String, Map<String,String>>();

	/**
	 * ͨ������·����ȡ·���µ����з��Ϲ������־�ļ�
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��4�� ����2:16:12 
	 *
	 */
	public static List<String> getLogFileByFilePath(String filePath) throws Exception {
		File logFilePath = new File(filePath);
		List<String> logFilePathList = new ArrayList<String>();
		if (logFilePath.isDirectory()) {
			String[] fileStrings = logFilePath.list();
			if (fileStrings != null) {
				for (String file : fileStrings) {
					if (file.endsWith(LogConstant.FILE_NAME_ENDING)) {
						logFilePathList.add(file);
					}
				}
			}
		} else {
			throw new Exception("�ļ�·������:" + filePath + ",��������ȷ�ļ�·��");
		}
		return logFilePathList;
	}

	/**
	 * ��ȡ��ǰĿ¼�µ�������־�ļ� �Լ�ֵ�ԣ���Ϊ�ļ������ļ���ַ����ʽȥ�洢
	 * 
	 * @param filePath
	 * @param depth �ݹ���ȣ��������Ӹ���Ŀ¼���µ��ļ������
	 * @return
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��12��4�� ����2:17:05 
	 *
	 */
	public static Map<String, String> getAllLogFile(String filePath, int depth) {
		if (depth > 0) {
			depth--;
			Map<String, String> resultMap = new HashMap<String, String>(16);
			File file = new File(filePath);
			File[] listFiles = file.listFiles();
			if (listFiles != null) {
				for (File file2 : listFiles) {
					if (file2.isFile() && file2.getName().endsWith(LogConstant.FILE_NAME_ENDING)) {
						resultMap.put(file2.getName(), file2.getAbsolutePath());
					}
					if (file2.isDirectory() && file2.getName().indexOf(LogConstant.FOLDER_NAME) != -1) {
						Map<String, String> allLogFile = getAllLogFile(file2.getAbsolutePath(), depth);
						resultMap.putAll(allLogFile);
					}
				}
			}
			return resultMap;
		} else {
			return new HashMap<String, String>(1);
		}
		
	}
	
	/**
	 * ���������û���Ϊ�����뻺����
	 * 
	 * @param uCode
	 * @param logMap
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��27�� ����11:03:53 
	 *
	 */
	public static void put(String uCode, Map<String, String> logMap) {
		logFileToUCode.put(uCode, logMap);
	}
	
	/**
	 * �����ݽ���ɾ������ɾ���û�����ʱ��һ��ɾ���û��棬��ֹռ���ڴ棩
	 * 
	 * @param uCode
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��27�� ����12:17:17 
	 *
	 */
	public static void remove(String uCode) {
		logFileToUCode.remove(uCode);
	}
	
	/**
	 * ���ݷ����û����ļ�������ȡ���ļ��ľ���·��
	 * 
	 * @param uCode
	 * @param fileName
	 * @return
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019��11��27�� ����12:17:53 
	 *
	 */
	public static String get(String uCode, String fileName) {
		return logFileToUCode.get(uCode).get(fileName);
	}
}
