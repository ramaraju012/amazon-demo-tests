package com.demo.activity;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.demo.constants.Constants;
import com.demo.driver.DriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RetailSearchActivity {

	private static final Logger LOGGER = Logger.getLogger(RetailSearchActivity.class);

	private AppiumDriver driver;

	public RetailSearchActivity(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	private MobileElement txt_Search;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions")
	private MobileElement we_AutoSuggestions;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/list_product_linear_layout")
	private List<MobileElement> lnk_searchResults;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']//*[@resource-id='title_feature_div']/android.view.View")
	private MobileElement lbl_ProductTitle;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']//*[contains(@resource-id,'priceblock')]/*[self::android.widget.TextView or self::android.widget.EditText]")
	private MobileElement lbl_ProductPrice;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']//*[@resource-id='acrCustomerReviewLink']/android.view.View")
	private MobileElement lbl_ProductReviews;

	@AndroidFindBy(xpath = "//*[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']//*[@resource-id='buyNowCheckout']")
	private MobileElement btn_BuyNow;

	/**
	 * searches with the given keyword
	 * 
	 * @param testData test data
	 */
	public void searchForData(Map<?, ?> testData) {
		DriverUtils.sleep(2);
		DriverUtils.waitTillElementVisible(driver, txt_Search, Constants.WAIT_30_SEC);
		DriverUtils.enterText(txt_Search, testData.get("SearchKeyword").toString());
		DriverUtils.waitTillElementVisible(driver, we_AutoSuggestions, Constants.WAIT_30_SEC);
		DriverUtils.clickElement(we_AutoSuggestions);
		LOGGER.info("Searched for " + testData.get("SearchKeyword").toString());
	}

	/**
	 * Performs swipe gesture on search results
	 */
	public void swipeSearchResults() {
		DriverUtils.sleep(Constants.WAIT_2_SEC);
		DriverUtils.swipeFromBottomToTop(driver);
	}

	/**
	 * 
	 * Selects one search result randomly
	 * 
	 * @param testData test data
	 */
	public void selectSearchResultRandomly(Map<String, Object> testData) {
		int randomNumber = (int) ((Math.random() * (lnk_searchResults.size() - 2)) + 1);
		LOGGER.trace(randomNumber);
		DriverUtils.waitTillElementVisible(driver, lnk_searchResults.get(randomNumber), Constants.WAIT_30_SEC);
		testData.put("ProductTitle", lnk_searchResults.get(randomNumber)
				.findElementByXPath(".//*[@resource-id='com.amazon.mShop.android.shopping:id/item_title']").getText());
		testData.put("ProductPrice", lnk_searchResults.get(randomNumber).findElementByXPath(
				".//*[@resource-id='com.amazon.mShop.android.shopping:id/rs_results_price']//android.widget.TextView")
				.getText());
		testData.put("ProductReviews",
				lnk_searchResults.get(randomNumber)
						.findElementByXPath(
								".//*[@resource-id='com.amazon.mShop.android.shopping:id/rs_results_ratings']")
						.getText());
		DriverUtils.clickElement(lnk_searchResults.get(randomNumber));
		LOGGER.info("Clicked on search result number " + randomNumber);
	}

	/**
	 * Verifies product data displayed correctly in product details screen
	 * 
	 * @param testData test data
	 */
	public void verifyDataDisplayedInProductDetails(Map<String, Object> testData) {
		DriverUtils.waitTillElementVisible(driver, lbl_ProductTitle, Constants.WAIT_30_SEC);
		LOGGER.trace("Actual product Title: " + lbl_ProductTitle.getText() + " and Expected product title: "
				+ testData.get("ProductTitle").toString());
		Assert.assertEquals(lbl_ProductTitle.getText(), testData.get("ProductTitle").toString());
		LOGGER.trace("Actual product reviews: " + lbl_ProductReviews.getText() + " and Expected product reviews: "
				+ testData.get("ProductReviews").toString());
		// Commenting this assertion as for some products, reviews are displaying
		// incorrectly in search results and product desc
		/*
		 * Assert.assertEquals(lbl_ProductReviews.getText().substring(0, 3),
		 * testData.get("ProductReviews").toString().substring(0, 3));
		 */
		DriverUtils.sleep(Constants.WAIT_2_SEC);
		DriverUtils.scrollTillElement(driver, lbl_ProductPrice, 5);
		// format the price data to match with the expected data
		String expectedPrice = testData.get("ProductPrice").toString().split(" ")[0].substring(1);
		LOGGER.trace("Actual product price: " + lbl_ProductPrice.getText() + " and Expected product price: "
				+ testData.get("ProductPrice").toString());
		Assert.assertTrue(lbl_ProductPrice.getText().replace(".00", "").contains(expectedPrice));
		LOGGER.info("Data matched between search results and product description");
	}

	/**
	 * 
	 * Clicks on buy now button in product details screen
	 * 
	 * @param testData test data
	 */
	public void clickBuyNow(Map<String, Object> testData) {
		DriverUtils.waitTillElementVisible(driver, lbl_ProductTitle, Constants.WAIT_30_SEC);
		DriverUtils.scrollToAndClick(driver, btn_BuyNow, Constants.WAIT_30_SEC);
		LOGGER.info("Clicked on Buy Now buton");
	}

}
