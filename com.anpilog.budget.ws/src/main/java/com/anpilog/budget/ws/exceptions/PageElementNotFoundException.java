package com.anpilog.budget.ws.exceptions;

import com.anpilog.budget.ws.core.UberWebDriver;

public class PageElementNotFoundException extends Exception {

	private static final long serialVersionUID = 2539861797676879121L;

	public PageElementNotFoundException(String message, UberWebDriver webdriver) {
		super(message);
		webdriver.takeScreenshot();
	}

}
