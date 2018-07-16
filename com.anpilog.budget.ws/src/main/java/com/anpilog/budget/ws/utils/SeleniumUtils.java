package com.anpilog.budget.ws.utils;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anpilog.budget.ws.core.UberWebDriver;

public class SeleniumUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	public static boolean isProblemWithLogin(UberWebDriver webDriver) {
		if (webDriver.lookupElement(By.xpath("//*[contains(text(),'Enter Account Number')]")) != null)
			return true;

		return false;
	}

	public static boolean isSecretQuestionShown(UberWebDriver webDriver) {
		// Kohls
		WebElement secretQuestion = webDriver.lookupElement(By.xpath("//*[contains(text(),'Security Question')]"));
		if (secretQuestion != null && secretQuestion.isDisplayed())
			return true;
		
		secretQuestion = webDriver.lookupElement(By.xpath("//*[contains(text(),'Secret')]"));
		if (secretQuestion != null && secretQuestion.isDisplayed())
			return true;

		secretQuestion = webDriver.lookupElement(By.xpath("//*[contains(text(),'Verify Your Identity')]"), 0);
		if (secretQuestion != null && secretQuestion.isDisplayed())
			return true;

		secretQuestion = webDriver.lookupElement(By.xpath("//*[contains(text(),'Challenge Question')]"), 0);
		if (secretQuestion != null && secretQuestion.isDisplayed())
			return true;

		secretQuestion = webDriver.lookupElement(By.xpath("//*[contains(text(),'Security Verification')]"), 0);
		if (secretQuestion != null && secretQuestion.isDisplayed())
			return true;

		secretQuestion = webDriver.lookupElement(By.xpath("//*[contains(text(),'Security question')]"), 0);
		if (secretQuestion != null && secretQuestion.isDisplayed())
			return true;

		return false;
	}

	public static boolean checkIfSiteDown(UberWebDriver webDriver) {
		WebElement text = webDriver.lookupElement(By.xpath("//*[contains(text(),'This site can’t be reached')]"), 0);
		if (text != null && text.isDisplayed())
			return true;

		text = webDriver.lookupElement(By.xpath("//*[contains(text(),'Our site is down')]"));
		if (text != null && text.isDisplayed())
			return true;

		text = webDriver.lookupElement(By.xpath("//*[contains(text(),'Our site is down')]"), 0);
		if (text != null && text.isDisplayed())
			return true;

		return false;
	}
	
	public static String prepareTextForQuery(String inText) {
		String outText = inText.replace("'", "");
		outText = outText.replace("#", "");

		return outText;
	}

	public static boolean isPending(String row) {
		if ("".equals(row.trim()) || row.contains("pending transactions") || row.contains("Pending Transactions")
				|| row.contains("There is no recent activity") || row.contains("Posted Transactions")
				|| row.equals("Pending") || row.contains("end of your statement")
				|| row.startsWith("No activity posted")
				|| row.equals("You've reached the end of the statement cycle account activity.")
				|| row.equals("In Progress and Cleared Transactions") || row.contains("Pending*")
				|| row.contains("Total for") || row.contains("Transaction Detail"))
			return true;
		else
			return false;
	}

	public static void clearBatch(Statement stmt) {
		try {
			stmt.clearBatch();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			logger.error(ex.getMessage());
		}
	}

	public static void executorShutdown(ExecutorService executor) {
		try {
			// logger.info("attempt to shutdown executor");
			executor.shutdown();
			executor.awaitTermination(15, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error("tasks interrupted");
		} finally {
			if (!executor.isTerminated()) {
				logger.error("cancel non-finished tasks");
			}
			executor.shutdownNow();
			logger.info("shutdown finished");
		}
	}

	public static String getThreadNumber(String string) {
		// thread # within pool
		if (string.length() == 1)
			return string;
		else {
			int i = string.indexOf("Bank accounts");
			return string.substring(i + 15, i + 16);
		}
	}

	public static By getByLocator(String string) {
		if (string == null)
			return null;
		if (!string.contains(":")) {
			logger.error("Locator '{}' cannot be initialized", string);
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

	public static String getLocatorForWebElement(WebElement we) {
		// assuming locator in xpath
		return we.toString().substring(we.toString().indexOf("->") + 10);
	}

}
