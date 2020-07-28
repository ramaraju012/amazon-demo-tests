package com.demo.activity;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import com.demo.constants.Constants;
import com.demo.driver.DriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SigninPromptActivity {

	private static final Logger LOGGER = Logger.getLogger(SigninPromptActivity.class);

	private AppiumDriver driver;

	public SigninPromptActivity(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/sign_in_button")
	private MobileElement btn_AlreadyACustomerSignIn;

	/**
	 * Clicks on signin button
	 */
	public void clickSignIn() {
		DriverUtils.waitTillElementVisible(driver, btn_AlreadyACustomerSignIn, Constants.WAIT_30_SEC);
		DriverUtils.clickElement(btn_AlreadyACustomerSignIn);
		LOGGER.info("clicked on sign in button");
	}

}
