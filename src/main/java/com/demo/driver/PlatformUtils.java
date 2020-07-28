package com.demo.driver;

import com.demo.constants.Constants;

import io.appium.java_client.AppiumDriver;

public class PlatformUtils {
	
	public static boolean isAndroid(AppiumDriver driver) {
		return driver.getCapabilities().getCapability("platformName").equals(Constants.ANDROID_PLATFORM);
	}
	
	public static boolean isIos(AppiumDriver driver) {
		return driver.getCapabilities().getCapability("platformName").equals(Constants.IOS_PLATFORM);
	}

}
