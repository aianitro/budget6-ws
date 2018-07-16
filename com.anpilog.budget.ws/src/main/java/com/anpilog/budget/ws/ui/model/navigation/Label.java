package com.anpilog.budget.ws.ui.model.navigation;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;

import com.anpilog.budget.ws.core.UberWebDriver;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;

public class Label extends PageElement {

	public Label(String name, By locator, SearchContext searchContext, UberWebDriver webdriver) {
		super(name, locator, searchContext, webdriver);
	}

	public String getText() throws PageElementNotFoundException {
		if (locator == null)
			return null;
		if (webElement == null)
			webElement = searchContext.findElement(locator);
		if (webElement == null){
			webdriver.takeScreenshot();
			throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ");
		}
		
		try {
			return webElement.getText().trim();
		} catch (StaleElementReferenceException ex) {
			webElement = searchContext.findElement(locator);
			if (webElement == null) {
				webdriver.takeScreenshot();
				throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ");
			}
			return webElement.getText().trim();
		}				
	}

	@Override
	public String toString() {
		return "Label [name=" + name + ", locator=" + locator + ", webElement=" + webElement + "]";
	}

}
