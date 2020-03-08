package com.yang.wordbreak;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Properties;

public class PropertiesUtils {

	private static String propertiesFileName = "/words.properties";
	
	private static Properties properties = new Properties();
	
	public static String getPropertyValues(String key) {
		init();//initial words.properties
		String value = (String)properties.get(key);
		return value == null ? "" : value.trim();
	}
	
	private static void init() {
		FileInputStream fis = null;
		InputStreamReader reader = null;
		Properties cache = new Properties();
		try {
			URI uri = PropertiesUtils.class.getResource(propertiesFileName).toURI();
			fis = new FileInputStream(new File(uri)); 
			reader = new InputStreamReader(fis,"utf-8");
			properties = new Properties();
			properties.load(reader);
		} catch (Exception ex) {
			properties = (Properties) cache.clone();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
			if(reader != null) {
				try {
					reader.close();
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
			cache.clear();
		}
	}
}
