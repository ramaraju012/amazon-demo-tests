package com.demo.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyLoader {
	private static final Logger LOGGER = Logger.getLogger(PropertyLoader.class);

	private static final String PROPERTY_FILE = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "configurations.properties";
	private static Properties PropertyReader = new Properties();

	static {
		try {
			LOGGER.info("Loading configurations from " + PROPERTY_FILE);
			FileInputStream in = new FileInputStream(PROPERTY_FILE);
			PropertyReader.load(in);
			in.close();
		} catch (IOException e) {
			LOGGER.error("error while loading prioperties" + e.getMessage());
		}
	}

	/**
	 * Method will return property value for the passed property name
	 * 
	 * @param key
	 *            property name
	 * @return returns property value
	 */
	public static String getProperty(String key) {
		return PropertyReader.getProperty(key);
	}
}
