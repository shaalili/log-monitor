package com.sinosoft.monitor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sinosoft.monitor.constant.LogConstant;

/**
 * 日志文件工具类
 *
 * @author：yangli	
 * @date:2019年12月4日 下午2:14:55
 * @version 1.0
 */
public class LogFileSuchUtil {
	
	/**
	 * 用于存储每个访问用户所读取到的文件及其绝对路径
	 */
	public static Map<String, Map<String, String>> logFileToUCode = new ConcurrentHashMap<String, Map<String,String>>();

	/**
	 * 通过给定路径获取路径下的所有符合规则的日志文件
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 下午2:16:12 
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
			throw new Exception("文件路径错误:" + filePath + ",请输入正确文件路径");
		}
		return logFilePathList;
	}

	/**
	 * 获取当前目录下的所有日志文件 以键值对，键为文件名，文件地址的形式去存储
	 * 
	 * @param filePath
	 * @param depth 递归深度，及遍历从给定目录往下的文件夹深度
	 * @return
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月4日 下午2:17:05 
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
	 * 将数据以用户名为键存入缓存中
	 * 
	 * @param uCode
	 * @param logMap
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月27日 上午11:03:53 
	 *
	 */
	public static void put(String uCode, Map<String, String> logMap) {
		logFileToUCode.put(uCode, logMap);
	}
	
	/**
	 * 将数据进行删除（在删除用户缓存时，一并删除该缓存，防止占用内存）
	 * 
	 * @param uCode
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月27日 下午12:17:17 
	 *
	 */
	public static void remove(String uCode) {
		logFileToUCode.remove(uCode);
	}
	
	/**
	 * 根据访问用户及文件名，获取该文件的绝对路径
	 * 
	 * @param uCode
	 * @param fileName
	 * @return
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年11月27日 下午12:17:53 
	 *
	 */
	public static String get(String uCode, String fileName) {
		return logFileToUCode.get(uCode).get(fileName);
	}
}
