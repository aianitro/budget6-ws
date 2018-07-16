package com.anpilog.budget.ws.ui.model.navigation;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;

import com.anpilog.budget.ws.core.UberWebDriver;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;

public class Button extends PageElement {
	
	public Button(String name, By locator, SearchContext searchContext, UberWebDriver webdriver) {
		super(name, locator, searchContext, webdriver);
	}
	
	public void click() throws PageElementNotFoundException{
		if (webElement == null)
			webElement = searchContext.findElement(locator);
		if (webElement == null){
			webdriver.takeScreenshot();
			throw new PageElementNotFoundException("Button '" + name + "' (" + locator + ") not found ");
		}
		
		try {
			webElement.click();
		} catch (WebDriverException ex) {
			webElement = searchContext.findElement(locator);
			if (webElement == null) {
				webdriver.takeScreenshot();
				throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ");
			}
			webdriver.scrollTo(locator);
			webElement.click();
		}		
	}
	
	public void clickAsAction() throws PageElementNotFoundException{
		if (webElement == null)
			webElement = searchContext.findElement(locator);
		if (webElement == null){
			webdriver.takeScreenshot();
			throw new PageElementNotFoundException("Button '" + name + "' (" + locator + ") not found ");
		}
		
		try {
			Actions action = new Actions(webdriver.getWebDriver());
			action.moveToElement(webElement).build().perform();
		} catch (StaleElementReferenceException ex) {
			webElement = searchContext.findElement(locator);
			if (webElement == null) {
				webdriver.takeScreenshot();
				throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ");
			}
			Actions action = new Actions(webdriver.getWebDriver());
			action.moveToElement(webElement).build().perform();
		}	
	}
	
	public void clickIfAvailable() throws PageElementNotFoundException{
		if(locator==null)
			return;
		if (webElement == null)
			webElement = searchContext.findElement(locator);
		if (webElement == null)
			throw new PageElementNotFoundException("Button '" + name + "' (" + locator + ") not found ");
		
		try {
			webElement.click();
		} catch (StaleElementReferenceException ex) {
			webElement = searchContext.findElement(locator);
			if (webElement == null) {
				webdriver.takeScreenshot();
				throw new PageElementNotFoundException("Field '" + name + "' (" + locator + ") not found ");
			}
			webElement.click();
		}		
	}

	@Override
	public String toString() {
		return "Button [name=" + name + ", locator=" + locator  + ", webElement=" + webElement + "]";
	}

}
