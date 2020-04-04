package com.pardxa.chatbox.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
	public static String load(String key) {
		Properties prop = new Properties();
		String value = "";
		try {
			InputStream in = PropertyLoader.class.getClassLoader().getResourceAsStream("prop.xml");
			prop.loadFromXML(in);
			value = prop.getProperty(key);
			in.close();
			value = (value == null) ? "" : value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
