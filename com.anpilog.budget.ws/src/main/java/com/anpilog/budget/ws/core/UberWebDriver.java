package com.anpilog.budget.ws.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anpilog.budget.ws.utils.SeleniumUtils;

//import com.dashboard.budget.DAO.PicturesStorage;

public class UberWebDriver implements SearchContext {

	protected static Logger logger = LoggerFactory.getLogger(UberWebDriver.class);

	private WebDriver webDriver;
	private WebDriverWait wait;

	public UberWebDriver() {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		try {
			/*
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			options.addArguments("window-size=1200x600");
			this.webDriver = new ChromeDriver(options);
			*/
			this.webDriver = new ChromeDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.wait = new WebDriverWait(this.webDriver, 60);
	}

	public void get(String url) {
		webDriver.get(url);
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void quit() {
		webDriver.quit();
	}

	public TargetLocator switchTo() {
		return webDriver.switchTo();
	}

	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	public String getPageSource() {
		return webDriver.getPageSource();
	}

	public WebElement lookupElement(By by) {
		return lookupElement(by, 20);
	}

	public WebElement lookupElement(By by, int waitTime) {
		int i = 0;
		while (webDriver.findElements(by).size() == 0 && i < waitTime) {
			SeleniumUtils.sleep(500);
			i++;
		}
		List<WebElement> elements = webDriver.findElements(by);
		if (elements.size() > 0)
			return elements.get(0);
		else
			return null;
	}

	public WebElement findElement(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return webDriver.findElement(by);
		} catch (Exception e) {
			logger.error("Unable to find locator: {}", by);
			return null;
		}
	}

	public WebElement findElementInRow(WebElement row, By by) {
		int i = 0;
		while (row.findElements(by).size() == 0 && i < 20) {
			SeleniumUtils.sleep(500);
			i++;
		}
		List<WebElement> elements = row.findElements(by);
		if (elements.size() > 0)
			return elements.get(0);
		else
			return null;
	}

	public List<WebElement> findElements(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return webDriver.findElements(by);
		} catch (Exception e) {
			logger.error("Unable to find locator: {}", by);
			return null;
		}
	}

	public void waitForTextToBePresent(By by, String text) {
		try {
			wait.until(ExpectedConditions.textToBePresentInElement(findElement(by), text));
		} catch (Exception e) {
			logger.error("Unable to find locator: {}", by);
		}
	}

	public void waitFrameToBeAvailableAndSwitchToIt(String frame) {
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void waitToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void clickElementWithAction(WebElement elem) {
		try {
			Actions actions = new Actions(webDriver);
			actions.click(elem).build().perform();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
		
	public void scrollTo(By by){
		WebElement element = webDriver.findElement(by);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);		
	}
	
	public void takeScreenshot() {
		File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		File destFile = new File("/b6/screenshots/error" + System.currentTimeMillis() + ".png");
		try {
			FileUtils.copyFile(scrFile, destFile);
			logger.info("Screenshot '" + destFile.getAbsolutePath() + "' is saved");
		} catch (IOException e) {
			logger.error("Unable to save screenshot '" + destFile.getPath() + "'");
		}
	}

}
