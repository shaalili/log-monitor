package com.sinosoft.monitor.constant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ��־��س���
 *
 * @author��yangli	
 * @date:2019��12��2�� ����9:35:26
 * @version 1.0
 */
public class LogConstant {
	
	/**
	 * ���������ļ���ʼ��ָ����ز���
	 */
	public static void init() {
		Properties properties = new Properties();
	    InputStream in = LogConstant.class.getClassLoader().getResourceAsStream("/monitor.properties");
	    try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	    //����·��
	    String folderPath = properties.getProperty("monitor.folder.path");
	    if (folderPath != null) {
			FOLDER_PATH = folderPath;
		}
	    //�����ļ���
	    String folderName = properties.getProperty("monitor.folder.name");
	    if (folderName != null) {
			FOLDER_NAME = folderName;
		}
	    //�����ļ����ƽ�β
	    String fileNameEnding = properties.getProperty("monitor.file.name.ending");
	    if (fileNameEnding != null) {
			FILE_NAME_ENDING = fileNameEnding;
		}
	    //�������RECURSIVE_DEPTH
	    String recursiveDepth = properties.getProperty("monitor.recursive.Depth");
	    if (recursiveDepth != null) {
			RECURSIVE_DEPTH = Integer.valueOf(recursiveDepth);
		}
	    //ѹ���ļ��洢Ŀ¼
	    String zipFilePath = properties.getProperty("monitor.zip.file.path");
	    if (zipFilePath != null) {
	    	ZIP_FILE_PATH = zipFilePath;
		}
	    //ѹ���ļ�����·��
	    String zipFileDownloadPath = properties.getProperty("monitor.zip.file.download.path");
	    if (zipFilePath != null) {
	    	ZIP_FILE_DOWNLOAD_PATH = zipFileDownloadPath;
		}
	    try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ǰ�˴����������������
	 * ����������Ϊ�����ļ��������з��Ϲ������־�ļ�
	 */
	public static final String REQUEST_TYPE = "filePaht";
	
	/**
	 * ǰ�˴����������������
	 * ����������Ϊ������־�ļ�
	 */
	public static final String REQUEST_TYPE_DOWNLOAD = "download";
	
	/**
	 * ��������ʽ��ѯʱ�������ȡ��ǰ�ļ�Ȩ�ޣ�Ĭ�Ͽɶ���д��
	 */
	public static final String FILE_PERMISSION = "rw";
	
	/**
	 * �ݹ���ȣ��������ļ������(Ĭ��Ϊ2)
	 */
	public static Integer RECURSIVE_DEPTH = 2;

	/**
	 * ָ��ĳ�ļ��� �µ������ļ�(Ĭ��Ϊ������bin��һ���ļ��п�ʼ����)
	 */
	public static String FOLDER_PATH = ".." + File.separator;
	
	/**
	 * ָ����������ĳ���Ƶ��ļ��н��б���(Ĭ��Я��log�ַ����ļ���)
	 */
	public static String FOLDER_NAME = "log";
	
	/**
	 * ��ȡָ��Ŀ¼���Ըó�����β����־�ļ�(Ĭ��Ϊ.log�ļ���β����־�ļ�)
	 */
	public static String FILE_NAME_ENDING = ".log";
	
	/**
	 * ָ��ѹ���ļ����Ŀ¼
	 */
	public static String ZIP_FILE_PATH = "";
	
	/**
	 * ָ��ѹ���ļ�����·�� 
	 * ���磺http://localhost:8080/monitor/error.log
	 * ��ó���Ϊ /monitor
	 */
	public static String ZIP_FILE_DOWNLOAD_PATH = "";
}
