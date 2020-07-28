package com.demo.testng;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.demo.driver.DriverManager;

import io.appium.java_client.AppiumDriver;

public class BaseTest {

	public AppiumDriver driver;

	/**
	 * Common setup method which will initiates driver object by taking parameters
	 * from testng.xml file
	 * 
	 * @param platform        Name of the platform, i.e android, ios, etc
	 * @param platformVersion Version number of the platform
	 * @param deviceName      Name of the device on which tests will run
	 * @param automationName  Name of the automation framework to use
	 * @param nodeUrl         Appium server URL
	 * @throws MalformedURLException
	 */
	@Parameters({ "platform", "platformVersion", "deviceName", "automationName", "nodeUrl" })
	@BeforeClass
	public void commonSetUp(String platform, String platformVersion, String deviceName, String automationName,
			String nodeUrl) throws MalformedURLException {
		driver = DriverManager.getDriver(platform, platformVersion, deviceName, automationName, nodeUrl);
	}

	/**
	 * Common tear down method to kill created driver object
	 */
	@AfterClass
	public void commonTearDown() {
		DriverManager.quitBrowser(driver);
	}

	/**
	 * takes screen shot of the mobile when test case fails
	 * 
	 * @param testResult test execution object
	 * @throws IOException
	 */
	@AfterMethod
	public void takeScreenshotOnFailure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file,
					new File(System.getProperty("user.dir") + File.separator + "target" + File.separator + "screenshots"
							+ File.separator + testResult.getMethod().getMethodName() + ".png"));
		}
	}

}
