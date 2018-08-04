
package com.anpilog.budget.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anpilog.budget.ws.exceptions.ConfigurationException;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.service.SeleniumService;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.pages.AccountPage;
import com.anpilog.budget.ws.ui.model.pages.AccountPageMyPortfolio;
import com.anpilog.budget.ws.utils.SeleniumUtils;

/**
 *
 * @author aanpilogov
 */
public class SeleniumServiceImpl implements SeleniumService {

	private static Logger logger = LoggerFactory.getLogger(SeleniumServiceImpl.class);
	private AccountPage accountPage = null;

	public AccountPage getAccountPage(AccountDTO accountDto) throws ConfigurationException {
		//
		// For now all accounts managed by My Portfolio
		//
		// switch (accountDto.getBank().getName()) {
		// case "mp":
		return new AccountPageMyPortfolio(accountDto);
		// default:
		// return null;
		// }
	}

	public void findTransactionsForDifference(TotalDTO totalDto, AccountPage accountPage,
			List<TransactionDTO> prevTransactions) {
		try {
			accountPage.findTransactions(totalDto, prevTransactions);
			if (totalDto.getTransactions().isEmpty()) {
				logger.info("No transactions found for difference");
				// totalDto.setErrorStatus(prevTotal, DataRetrievalStatus.NO_MATCH_FOR_TOTAL,
				// null); //TODO
			} else {
				for (TransactionDTO newTransaction : totalDto.getTransactions()) {
					// dataHandler.recognizeCategoryInTransaction(newTransaction); //TODO
					logger.info("{}, transaction: {}, {}, {}, {} ({})", accountPage.getAccount().getName(),
							newTransaction.getDate(), newTransaction.getDescription(), newTransaction.getAmount(),
							newTransaction.getCategoryStr(), newTransaction.getCategoryStr());
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
			totalDto.setStatus(DataRetrievalStatus.NAVIGATION_BROKEN);
		}

	}

	public void refreshTotals(List<TotalDTO> totalsToRefresh, List<TransactionDTO> prevTransactions)
			throws ConfigurationException {

		logger.info("Number of accounts to run: {}", totalsToRefresh.size());
		totalsToRefresh.stream().forEach(a -> logger.info(a.getAccount().getName()));

		// Extract list of drivers
		List<List<String>> drivers = new ArrayList<List<String>>();

		// Group accounts list by banks
		totalsToRefresh.stream().filter(a -> a.getAccount().getBank() != null).forEach(a -> {
			List<String> driver = new ArrayList<String>();
			driver.add(a.getAccount().getBank().getName());
			if (!drivers.contains(driver))
				drivers.add(driver);
		});

		// ExecutorService executor = Executors.newFixedThreadPool(nubmberOfThreads,
		// new ThreadFactoryBuilder().setNameFormat("%d").build());

		// for (List<String> driver : drivers) {
		// executor.submit(() -> {
		// totalsToRefresh.stream().filter(a ->
		// a.getAccount().getBank()==driver).forEach(total -> {
		for (TotalDTO total : totalsToRefresh) {
			AccountDTO accountDto = total.getAccount();
			Thread.currentThread().setName("Bank accounts ("
					+ SeleniumUtils.getThreadNumber(Thread.currentThread().getName()) + "): " + accountDto.getName());
			int attempt = 0;
			Double amount = null;
			boolean isDownloaded = false;
			while (!isDownloaded && attempt < 3) {
				logger.info("{}: attempt #{}", accountDto.getName(), ++attempt);
				if (accountPage == null) {
					accountPage = getAccountPage(accountDto);
					accountPage.gotoHomePage();
					DataRetrievalStatus loginStatus = accountPage.login();
					if (loginStatus != DataRetrievalStatus.COMPLETED) {
						total.setStatus(loginStatus);
						logger.error("Unsuccessful login to: {}", accountDto.getName());
						accountPage.quit();
						accountPage = null;
						isDownloaded = true;
						continue;
					}
				} else
					accountPage.setAccount(accountDto);

				try {
					amount = accountPage.getTotal();
				} catch (PageElementNotFoundException ex) {
					logger.error(ex.getLocalizedMessage());
					logger.error("Error while getting total for: {}", accountDto.getName());
					total.setStatus(DataRetrievalStatus.NAVIGATION_BROKEN);
					accountPage.quit();
					accountPage = null;
					continue;
				}

				Double prevTotal = total.getPreviousAmount();
				Double difference = amount - prevTotal;
				total.setAmount(amount);
				total.setDifference(difference);
				logger.info("{}, total: {}", accountDto.getName(), amount);
				if (difference != null && difference != 0.00) {
					logger.info("{}, difference: {}", accountDto.getName(), difference);
					findTransactionsForDifference(total, accountPage, prevTransactions);
				}

				if (total.getTransactions() != null && total.getTransactions().size() > 0) {
					total.setStatus(DataRetrievalStatus.COMPLETED);
					isDownloaded = true;
				}
			}
			// });
			accountPage.quit();
			accountPage = null;
		}
		// });
		// }

		// SeleniumUtils.executorShutdown(executor);
	}

	/*
	 * public List<TotalDTO> getNewTotals(List<TotalDTO> prevTotals,
	 * List<TransactionDTO> prevTransactions) {
	 * 
	 * List<TotalDTO> result = new ArrayList<TotalDTO>();
	 * 
	 * logger.info("Number of accounts to run: {}", prevTotals.size());
	 * prevTotals.stream().forEach(a -> logger.info(a.getAccount().getName()));
	 * 
	 * // Extract list of drivers List<List<String>> drivers = new
	 * ArrayList<List<String>>();
	 * 
	 * // Group accounts list by banks prevTotals.stream().filter(a ->
	 * a.getAccount().getBank() != null).forEach(a -> { List<String> driver = new
	 * ArrayList<String>(); driver.add(a.getAccount().getBank().getName()); if
	 * (!drivers.contains(driver)) drivers.add(driver); });
	 * 
	 * ExecutorService executor = Executors.newFixedThreadPool(nubmberOfThreads, new
	 * ThreadFactoryBuilder().setNameFormat("%d").build());
	 * 
	 * for (List<String> driver : drivers) { executor.submit(() -> {
	 * prevTotals.stream().filter(a ->
	 * a.getAccount().getBank()==driver).forEach(total -> { AccountDTO accountDto =
	 * total.getAccount(); Thread.currentThread().setName("Bank accounts (" +
	 * SeleniumUtils.getThreadNumber(Thread.currentThread().getName()) + "): " +
	 * accountDto.getName()); int attempt = 0; Double amount = null; TotalDTO total
	 * = null; Double prevTotal = total.getAmount(); Double difference = null;
	 * boolean isDownloaded = false; while (!isDownloaded && attempt <
	 * maxAttemptsToDownloadData) { logger.info("{}: attempt #{}",
	 * accountDto.getName(), ++attempt); if (accountPage == null) { accountPage =
	 * getAccountPage(accountDto); accountPage.gotoHomePage(); DataRetrievalStatus
	 * loginStatus = accountPage.login(); if (loginStatus !=
	 * DataRetrievalStatus.SUCCESS) { total = new Total(account, prevTotal, 0.0,
	 * loginStatus); logger.error("Unsuccessful login to: {}", account.getName());
	 * accountPage.quit(); accountPage = null; isDownloaded = true; // no need to
	 * try more - // smth too wrong continue; } } else
	 * accountPage.setAccount(account);
	 * 
	 * try { amount = accountPage.getTotal(); } catch (PageElementNotFoundException
	 * ex) { logger.error(ex.getLocalizedMessage());
	 * logger.error("Error while getting total for: {}", account.getName()); total =
	 * new Total(account, prevTotal, 0.0, DataRetrievalStatus.NAVIGATION_BROKEN);
	 * accountPage.quit(); accountPage = null; continue; }
	 * 
	 * difference = getDifference(account, amount, prevTotals); total = new
	 * Total(account, amount, difference, DataRetrievalStatus.SUCCESS);
	 * logger.info("{}, total: {}", account.getName(), amount); if (difference !=
	 * null && difference != 0.00) { logger.info("{}, difference: {}",
	 * account.getName(), difference); addTransactionsForDifference(total,
	 * accountPage, difference, prevTotal, prevTransactions); }
	 * 
	 * isDownloaded = true; } if (total != null) result.add(total); });
	 * accountPage.quit(); accountPage = null; }); }
	 * 
	 * Collections.sort(result); Util.executorShutdown(executor); return result; }
	 */

	public boolean isOnline() {
		logger.info("Network check...");
		WebDriver webDriver = new HtmlUnitDriver();

		int attempts = 0;
		while (attempts < 3) {
			try {
				webDriver.get("https://www.google.com");
				attempts = 3;
			} catch (WebDriverException e) {
				logger.error("Connection failed due to WebDriver exception");
				logger.error(e.getLocalizedMessage());
				attempts++;
			}
		}

		try {
			if (webDriver.findElements(By.cssSelector("#hplogo")).size() > 0) {
				webDriver.quit();
				logger.info("Network check - OK!");
				return true;
			}
		} catch (Exception e) {
		}
		webDriver.quit();
		logger.info("Network check - FAIL!");
		return false;
	}
}
