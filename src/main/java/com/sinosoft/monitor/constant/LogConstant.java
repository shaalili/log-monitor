package com.sinosoft.monitor.constant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 日志监控常量
 *
 * @author：yangli	
 * @date:2019年12月2日 上午9:35:26
 * @version 1.0
 */
public class LogConstant {
	
	/**
	 * 根据配置文件初始化指定相关参数
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
	    //遍历路径
	    String folderPath = properties.getProperty("monitor.folder.path");
	    if (folderPath != null) {
			FOLDER_PATH = folderPath;
		}
	    //遍历文件夹
	    String folderName = properties.getProperty("monitor.folder.name");
	    if (folderName != null) {
			FOLDER_NAME = folderName;
		}
	    //遍历文件名称结尾
	    String fileNameEnding = properties.getProperty("monitor.file.name.ending");
	    if (fileNameEnding != null) {
			FILE_NAME_ENDING = fileNameEnding;
		}
	    //遍历深度RECURSIVE_DEPTH
	    String recursiveDepth = properties.getProperty("monitor.recursive.Depth");
	    if (recursiveDepth != null) {
			RECURSIVE_DEPTH = Integer.valueOf(recursiveDepth);
		}
	    //压缩文件存储目录
	    String zipFilePath = properties.getProperty("monitor.zip.file.path");
	    if (zipFilePath != null) {
	    	ZIP_FILE_PATH = zipFilePath;
		}
	    //压缩文件下载路径
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
	 * 前端传入参数：请求类型
	 * 表明该请求为访问文件夹下所有符合规则的日志文件
	 */
	public static final String REQUEST_TYPE = "filePaht";
	
	/**
	 * 前端传入参数：请求类型
	 * 表明该请求为下载日志文件
	 */
	public static final String REQUEST_TYPE_DOWNLOAD = "download";
	
	/**
	 * 以增量形式查询时，定义读取当前文件权限（默认可读可写）
	 */
	public static final String FILE_PERMISSION = "rw";
	
	/**
	 * 递归深度，即便利文件夹深度(默认为2)
	 */
	public static Integer RECURSIVE_DEPTH = 2;

	/**
	 * 指定某文件夹 下的所有文件(默认为服务器bin上一级文件夹开始遍历)
	 */
	public static String FOLDER_PATH = ".." + File.separator;
	
	/**
	 * 指定遍历包含某名称的文件夹进行遍历(默认携带log字符的文件夹)
	 */
	public static String FOLDER_NAME = "log";
	
	/**
	 * 读取指定目录下以该常量结尾的日志文件(默认为.log文件结尾的日志文件)
	 */
	public static String FILE_NAME_ENDING = ".log";
	
	/**
	 * 指定压缩文件存放目录
	 */
	public static String ZIP_FILE_PATH = "";
	
	/**
	 * 指定压缩文件下载路径 
	 * 例如：http://localhost:8080/monitor/error.log
	 * 则该常量为 /monitor
	 */
	public static String ZIP_FILE_DOWNLOAD_PATH = "";
}
