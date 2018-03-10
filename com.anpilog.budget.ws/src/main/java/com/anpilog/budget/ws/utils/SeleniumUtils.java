package com.anpilog.budget.ws.utils;

import org.openqa.selenium.By;

public class SeleniumUtils {
	public static By getByLocator(String string) {
		if (string == null)
			return null;
		if (!string.contains(":")) {
			System.err.println("Locator '" + string + "' cannot be initialized");
			return null;
		}
		if (string.startsWith("id"))
			return By.id(string.replace("id:", ""));
		if (string.startsWith("css"))
			return By.cssSelector(string.replace("css:", ""));
		if (string.startsWith("link"))
			return By.linkText(string.replace("link:", ""));
		if (string.startsWith("xpath"))
			return By.xpath(string.replace("xpath:", ""));
		if (string.startsWith("name"))
			return By.name(string.replace("name:", ""));
		if (string.startsWith("class"))
			return By.className(string.replace("class:", ""));
		else
			return null;
	}
}
