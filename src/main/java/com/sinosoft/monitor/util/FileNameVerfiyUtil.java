package com.sinosoft.monitor.util;

/**
 * 日志名称校验类
 *
 * @author：yangli	
 * @date:2019年12月4日 下午2:14:24
 * @version 1.0
 */
public class FileNameVerfiyUtil {
	
	/**
	 * 定义日志名称最大255
	 */
	private static final int FILE_NAME_LENGTH = 255;

	public static boolean verifyFileName(String fileName) {
		if (fileName == null || fileName.length() > FILE_NAME_LENGTH) {
			return false;
		} else {
			return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
		}
	}
}
