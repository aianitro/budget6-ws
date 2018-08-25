package com.anpilog.budget.ws.ui.model.navigation;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;

import com.anpilog.budget.ws.core.UberWebDriver;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;

public class Field extends PageElement {

	public Field(String name, By locator, SearchContext searchContext, UberWebDriver webdriver) {
		super(name, locator, searchContext, webdriver);
	}

	public String getText() throws PageElementNotFoundException {
		if (locator == null)
			return null;
		if (webElement == null)
			webElement = searchContext.findElement(locator);
		if (webElement == null)
			throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ", webdriver);

		try {
			return webElement.getText().trim();
		} catch (StaleElementReferenceException ex) {
			webElement = searchContext.findElement(locator);
			if (webElement == null)
				throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ", webdriver);
			return webElement.getText().trim();
		}
	}

	public void setText(String text) throws PageElementNotFoundException {
		if (locator == null)
			return;
		if (webElement == null)
			webElement = searchContext.findElement(locator);
		if (webElement == null)
			throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ", webdriver);
		
		try {
			webElement.sendKeys(text);
		} catch (StaleElementReferenceException ex) {
			webElement = searchContext.findElement(locator);
			if (webElement == null)
				throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ", webdriver);
			webElement.sendKeys(text);
		}
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", locator=" + locator + ", webElement=" + webElement + "]";
	}

}
