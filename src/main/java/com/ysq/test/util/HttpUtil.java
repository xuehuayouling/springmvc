package com.ysq.test.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

	private static final int BUFFER_SIZE = 102400;
	public static final String BASE_VOICE_FILE_PATH = "/Users/ysq/Music/dictionaryvoices/";
	public static InputStream getInputStream(String urlPath) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(urlPath);
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();
				// 设置连接网络的超时时间
				httpURLConnection.setConnectTimeout(3000);
				httpURLConnection.setDoInput(true);
				// 表示设置本次http请求使用GET方式请求
				httpURLConnection.setRequestMethod("GET");
				int responseCode = httpURLConnection.getResponseCode();
				if (responseCode == 200) {
					// 从服务器获得一个输入流
					inputStream = httpURLConnection.getInputStream();
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputStream;
	}

	public static String inputStreamTOString(InputStream in) throws IOException{
		if (in == null) {
			return null;
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}

	public static String getURLContent(String urlPath) throws IOException {
		return inputStreamTOString(getInputStream(urlPath));
	}
	
	public static boolean saveMp3ToDisk(String urlPath, String fileName) {
		InputStream inputStream = getInputStream(urlPath);
		byte[] data = new byte[BUFFER_SIZE];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(BASE_VOICE_FILE_PATH + fileName);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static void saveImageToDisk(String urlPath) {
		InputStream inputStream = getInputStream(urlPath);
		byte[] data = new byte[BUFFER_SIZE];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("C:\\test.png");
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
