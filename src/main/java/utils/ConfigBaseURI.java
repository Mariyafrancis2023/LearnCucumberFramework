package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigBaseURI {

	private static Properties properties;
	
	static {
		properties = new Properties();
		try {
			FileInputStream file = new FileInputStream("src/test/resources/schemas/config.properties");
			properties.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static String getBaseURI(String baseUrl){
		return properties.getProperty(baseUrl);
	}
}
