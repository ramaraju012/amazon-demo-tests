package com.demo.driver;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class DriverUtils {

	private static final Logger LOGGER = Logger.getLogger(DriverUtils.class);

	/**
	 * makes current thread to sleep for specified seconds
	 * 
	 * @param waitTime number of seconds to wait
	 */
	public static void sleep(int waitTime) {
		LOGGER.debug("Sleeping for " + waitTime + " seconds.");
		try {
			Thread.sleep(waitTime * 1000);
		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException occurre. Details: " + e.getMessage());
		}
	}

	/**
	 * Enters text in to mobile element
	 * 
	 * @param element mobile element
	 * @param value   data that need to be entered in textbox
	 */
	public static void enterText(MobileElement element, String value) {
		element.setValue(value);
		LOGGER.debug("Entered value in text box. Value: " + value);
	}

	/**
	 * Clicks on element
	 * 
	 * @param element mobile element
	 */
	public static void clickElement(MobileElement element) {
		element.click();
		LOGGER.debug("Clicked on element");
	}

	/**
	 * A special utility method which scrolls to the specified element and wait for
	 * element visibility and clicks when it is visible
	 * 
	 * @param driver   AppiumDriver object
	 * @param element  mobile element
	 * @param waitTime maximum number of seconds to wait
	 */
	public static void scrollToAndClick(AppiumDriver driver, MobileElement element, int waitTime) {
		scrollTillElement(driver, element, 50);
		waitTillElementVisible(driver, element, waitTime);
		clickElement(element);
	}

	/**
	 * Checks for given element visibility for a given maximum time. Doesn't wait
	 * for maximum time if element is visible before specified time
	 * 
	 * @param driver   AppiumDriver object
	 * @param element  mobile element
	 * @param waitTime maximum number of seconds to wait
	 */
	public static void waitTillElementVisible(AppiumDriver driver, MobileElement element, int waitTime) {
		LOGGER.trace("waiting for the element visibility for maximum " + waitTime + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Performs swipe gesture from bottom of the screen to top of the screen. Finds
	 * x,y coordinates dynamically
	 * 
	 * @param driver AppiumDriver object
	 */
	public static void swipeFromBottomToTop(AppiumDriver driver) {
		Dimension size = driver.manage().window().getSize();
		int startY = (int) (size.height * 0.80);
		int endY = (int) (size.height * 0.20);
		int x = size.width / 2;
		TouchAction touch = new TouchAction(driver);
		Duration duration = Duration.ofSeconds(1);
		touch.press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(duration))
				.moveTo(PointOption.point(x, endY)).release().perform();
		LOGGER.debug("Swiped to TOP");
	}

	/**
	 * Scrolls till the given element is displayed on the screen. Useful in such
	 * cases when facing difficulty in identifying elements present in bottom of the
	 * screen
	 * 
	 * @param driver         AppiumDriver object
	 * @param element        Mobile element
	 * @param maximumScrolls total number of scrolls that need to be performed on
	 *                       screen
	 */
	public static void scrollTillElement(AppiumDriver driver, MobileElement element, int maximumScrolls) {
		for (int i = 0; i < maximumScrolls; i++) {
			if (isElementDisplayed(element)) {
				LOGGER.trace("Found element.");
				break;
			} else
				swipeFromBottomToTop(driver);
		}

	}

	/**
	 * returns if the given element is displayed or not
	 * 
	 * @param element Mobile element
	 * @return boolean value of element display
	 */
	public static boolean isElementDisplayed(MobileElement element) {
		boolean flag;
		try {
			element.isDisplayed();
			flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		}
		return flag;
	}

}
