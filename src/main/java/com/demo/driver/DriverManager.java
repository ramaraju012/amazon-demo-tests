package com.demo.driver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.demo.constants.Constants;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class DriverManager {

	private static final Logger LOGGER = Logger.getLogger(DriverManager.class);

	/**
	 * Instantiates driver based on provided parameters. Capabilities for the driver
	 * will be set based on passed method parameters
	 * 
	 * @param platform        Name of the platform, i.e android, ios, etc
	 * @param platformVersion Version number of the platform
	 * @param deviceName      Name of the device on which tests will run
	 * @param automationName  Name of the automation framework to use
	 * @param nodeUrl         Appium server URL
	 * @return initialised AppiumDriver object
	 * @throws MalformedURLException
	 */
	public static AppiumDriver getDriver(String platform, String platformVersion, String deviceName, String automationName,
			String nodeUrl) throws MalformedURLException {
		AppiumDriver driver;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		if (platform.equalsIgnoreCase(Constants.ANDROID_PLATFORM)) {
			capabilities.android();
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("automationName", automationName);
			capabilities.setCapability("app", System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ "test" + File.separator + "resources" + File.separator + Constants.APP_NAME);
			driver = new AndroidDriver(new URL(nodeUrl), capabilities);
			LOGGER.info("New Android driver instance created");
		} else if (platform.equalsIgnoreCase(Constants.IOS_PLATFORM)) {
			capabilities.iphone();
			// TBD
			driver = new IOSDriver(new URL(nodeUrl), capabilities);
			LOGGER.info("New ios driver instance created");
		} else {
			LOGGER.error("provided platform is not supported: " + platform + ". Hence initiating AppiumDriver");
			driver = new AppiumDriver(new URL(nodeUrl), capabilities);
		}

		return driver;
	}

	/**
	 * Closes the driver and terminates connection
	 * 
	 * @param driver Driver object
	 */
	public static void quitBrowser(WebDriver driver) {
		driver.quit();
	}

}
