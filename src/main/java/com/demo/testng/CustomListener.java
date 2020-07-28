package com.demo.testng;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomListener extends TestListenerAdapter {

	private static final Logger LOGGER = Logger.getLogger(CustomListener.class);

	public void onTestSuccess(ITestResult testResult) {
		LOGGER.info("##### " + testResult.getMethod().getMethodName() + " test method under "
				+ testResult.getMethod().getTestClass().getRealClass().getSimpleName()
				+ " class has been passed #####");
	}

	public void onTestFailure(ITestResult testResult) {
		LOGGER.info("##### " + testResult.getMethod().getMethodName() + " test method under "
				+ testResult.getMethod().getTestClass().getRealClass().getSimpleName()
				+ " class has been failed #####");
		LOGGER.error("##### ERROR: " + testResult.getThrowable().toString(), testResult.getThrowable());
	}

	public void onTestSkipped(ITestResult testResult) {
		LOGGER.info("##### " + testResult.getMethod().getMethodName() + " test method under "
				+ testResult.getMethod().getTestClass().getRealClass().getSimpleName()
				+ " class has been skipped #####");
		if (testResult.getThrowable() != null) {
			LOGGER.error("##### ERROR: " + testResult.getThrowable().toString(), testResult.getThrowable());
		}
	}

	public void onTestStart(ITestResult testResult) {
		LOGGER.info("##### Execution for " + testResult.getMethod().getMethodName() + " test method under "
				+ testResult.getMethod().getTestClass().getRealClass().getSimpleName()
				+ " class has been started #####");
	}

}
