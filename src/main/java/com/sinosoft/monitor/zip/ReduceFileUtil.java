package com.sinosoft.monitor.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sinosoft.monitor.constant.LogConstant;

/**
 * 压缩文件工具类
 *
 * @author：yangli
 * @date:2019年12月2日 上午9:58:03
 * @version 1.0
 */
public class ReduceFileUtil {

	/**
	 * 压缩文件到指定目录下
	 * 
	 * @param file 要压缩的文件
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月2日 上午9:58:50
	 *
	 */
	public static File reduceFile(File file) {
		//如果文件夹不存在，则进行创建
		File filePath = new File(LogConstant.ZIP_FILE_PATH);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		//目标文件
		File zipFile = new File(LogConstant.ZIP_FILE_PATH + File.separator + file.getName().substring(0, file.getName().lastIndexOf(".")) + ".zip");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(zipFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ZipOutputStream zos = new ZipOutputStream(fos);
		if (!file.exists()) {
			return null;
		}
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(file.getName());
			zos.putNextEntry(entry);
			int count;
			byte[] buf = new byte[10240];
			while ((count = bis.read(buf)) != -1) {
				zos.write(buf, 0, count);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				zos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return zipFile;
	}
	
	/**
	 * 删除指定的日志文件
	 * 
	 * @param filePath
	 * @version: v1.0.0
	 * @author: yangli
	 * @date: 2019年12月2日 下午4:37:39 
	 *
	 */
	public static void deleteLogFile(String filePath) {
		new File(filePath).delete();
	}
}
