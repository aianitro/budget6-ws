package com.anpilog.budget.ws.exceptions;

import com.anpilog.budget.ws.core.UberWebDriver;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = -7554191299889226373L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, UberWebDriver webdriver) {
		super(message);
		webdriver.takeScreenshot();
	}

}
