package com.sinosoft.monitor.util;

/**
 * ��־����У����
 *
 * @author��yangli	
 * @date:2019��12��4�� ����2:14:24
 * @version 1.0
 */
public class FileNameVerfiyUtil {
	
	/**
	 * ������־�������255
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
