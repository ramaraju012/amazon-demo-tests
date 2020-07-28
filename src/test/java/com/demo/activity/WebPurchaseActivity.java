package com.demo.activity;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import com.demo.constants.Constants;
import com.demo.driver.DriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WebPurchaseActivity {

	private static final Logger LOGGER = Logger.getLogger(WebPurchaseActivity.class);

	private AppiumDriver driver;

	public WebPurchaseActivity(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']//*[@resource-id='a-autoid-0-announce']")
	private MobileElement btn_UseThisAddress;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']//android.widget.Button[@text='Continue']")
	private MobileElement btn_Continue;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']//*[@resource-id='vasSchedulingWidget-0']//android.view.View[contains(@resource-id, 's-159')][2]")
	private MobileElement btn_InstallationTimeSlot;

	/**
	 * Fills shipping address and time slot details
	 */
	public void completeAddressTimeSlotDetails() {
		DriverUtils.waitTillElementVisible(driver, btn_UseThisAddress, Constants.WAIT_30_SEC);
		DriverUtils.clickElement(btn_UseThisAddress);
		DriverUtils.scrollToAndClick(driver, btn_Continue, Constants.WAIT_30_SEC);
		DriverUtils.waitTillElementVisible(driver, btn_InstallationTimeSlot, Constants.WAIT_30_SEC);
		DriverUtils.clickElement(btn_InstallationTimeSlot);
		DriverUtils.scrollToAndClick(driver, btn_Continue, Constants.WAIT_30_SEC);
	}

}
