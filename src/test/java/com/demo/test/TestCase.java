package com.demo.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demo.activity.AuthPortalUIActivity;
import com.demo.activity.MShopWebGatewayActivity;
import com.demo.activity.RetailSearchActivity;
import com.demo.activity.SigninPromptActivity;
import com.demo.data.DataDriven;
import com.demo.testng.BaseTest;

public class TestCase extends BaseTest {

	private SigninPromptActivity signinPromptActivity;
	private AuthPortalUIActivity authPortalUIActivity;
	private MShopWebGatewayActivity mShopWebGatewayActivity;
	private RetailSearchActivity retailSearchActivity;

	@BeforeClass
	public void setUp() {
		signinPromptActivity = new SigninPromptActivity(driver);
		signinPromptActivity.clickSignIn();
		authPortalUIActivity = new AuthPortalUIActivity(driver);
		authPortalUIActivity.login();
	}

	@Test(dataProvider = "getTestData", dataProviderClass = DataDriven.class)
	public void demoTestCase1(Map<String, Object> testData) {
		mShopWebGatewayActivity = new MShopWebGatewayActivity(driver);
		mShopWebGatewayActivity.clickSearch();
		retailSearchActivity = new RetailSearchActivity(driver);
		retailSearchActivity.searchForData(testData);
		retailSearchActivity.swipeSearchResults();
		retailSearchActivity.selectSearchResultRandomly(testData);
		retailSearchActivity.verifyDataDisplayedInProductDetails(testData);
		retailSearchActivity.clickBuyNow(testData);
	}

}
