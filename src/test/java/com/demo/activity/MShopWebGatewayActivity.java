package com.demo.activity;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import com.demo.constants.Constants;
import com.demo.driver.DriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MShopWebGatewayActivity {

	private static final Logger LOGGER = Logger.getLogger(MShopWebGatewayActivity.class);

	private AppiumDriver driver;

	public MShopWebGatewayActivity(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	private MobileElement txt_Search;

	/**
	 * Clicks on search button
	 */
	public void clickSearch() {
		DriverUtils.waitTillElementVisible(driver, txt_Search, Constants.WAIT_30_SEC);
		DriverUtils.clickElement(txt_Search);
		LOGGER.info("clicked on search text box");
	}

}
