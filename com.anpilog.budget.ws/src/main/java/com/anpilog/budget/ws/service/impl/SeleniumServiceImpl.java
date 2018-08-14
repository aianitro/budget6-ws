
package com.anpilog.budget.ws.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anpilog.budget.ws.exceptions.ConfigurationException;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;
import com.anpilog.budget.ws.io.entity.BankEntity;
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

		// Compiling map of totals grouped by bank
		Map<BankEntity, List<TotalDTO>> totalsByBank = new HashMap<BankEntity, List<TotalDTO>>();

		// Group totals list by banks
		for (TotalDTO total : totalsToRefresh) {
			BankEntity bank = total.getAccount().getBank();
			List<TotalDTO> totals = totalsByBank.get(bank);
			if (totals == null) {
				totals = new ArrayList<TotalDTO>();
				totalsByBank.put(bank, totals);
			}
			totals.add(total);
		}

		for (Map.Entry<BankEntity, List<TotalDTO>> totalsByBankEntry : totalsByBank.entrySet()) {
			
			for (TotalDTO total : totalsByBankEntry.getValue()) {

				AccountDTO accountDto = total.getAccount();
				Thread.currentThread()
						.setName("Bank accounts (" + SeleniumUtils.getThreadNumber(Thread.currentThread().getName())
								+ "): " + accountDto.getName());
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
					logger.info("{}, total: {}, difference: {}", accountDto.getName(), amount, difference);

					if (difference == 0.0) {
						total.setStatus(DataRetrievalStatus.COMPLETED);
						isDownloaded = true;
					} else {
						// Looking for transactions
						findTransactionsForDifference(total, accountPage, prevTransactions);

						if (total.getTransactions() != null && total.getTransactions().size() > 0) {
							total.setStatus(DataRetrievalStatus.COMPLETED);
							isDownloaded = true;
						}
					}
				}
			}
			accountPage.quit();
			accountPage = null;
		}
	}

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
