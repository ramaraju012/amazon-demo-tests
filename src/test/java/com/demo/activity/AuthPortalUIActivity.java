package com.demo.activity;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import com.demo.constants.Constants;
import com.demo.driver.DriverUtils;
import com.demo.property.PropertyLoader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * @author bvino
 *
 */
public class AuthPortalUIActivity {

	private static final Logger LOGGER = Logger.getLogger(AuthPortalUIActivity.class);

	private AppiumDriver driver;

	public AuthPortalUIActivity(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/apparentlayout']//*[@resource-id='ap_email_login']")
	private MobileElement txt_UserName;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/apparentlayout']//*[@resource-id='continue']")
	private MobileElement btn_Continue;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/apparentlayout']//*[@resource-id='ap_password']")
	private MobileElement txt_Password;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/apparentlayout']//*[@resource-id='signInSubmit']")
	private MobileElement btn_Login;

	/**
	 * Log in to application by using given user name and password
	 * 
	 * @param userName user name
	 * @param password password
	 */
	public void login(String userName, String password) {
		DriverUtils.sleep(Constants.WAIT_1_SEC);
		DriverUtils.waitTillElementVisible(driver, txt_UserName, Constants.WAIT_60_SEC);
		DriverUtils.enterText(txt_UserName, userName);
		DriverUtils.clickElement(btn_Continue);
		DriverUtils.waitTillElementVisible(driver, txt_Password, Constants.WAIT_30_SEC);
		DriverUtils.enterText(txt_Password, password);
		DriverUtils.clickElement(btn_Login);
		LOGGER.info("Logged in to application");
	}

	/**
	 * 
	 * Log in to application by using user name and password from test data
	 * 
	 * @param testData test data
	 */
	public void login(Map<String, Object> testData) {
		this.login(testData.get("UserName").toString(), testData.get("Password").toString());
	}

	/**
	 * Log in to application with default UN and PWD
	 */
	public void login() {
		this.login(PropertyLoader.getProperty("userName"), PropertyLoader.getProperty("password"));
	}

}
