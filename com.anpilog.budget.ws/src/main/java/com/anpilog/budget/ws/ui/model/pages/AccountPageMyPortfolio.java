package com.anpilog.budget.ws.ui.model.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.anpilog.budget.ws.exceptions.ConfigurationException;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;
import com.anpilog.budget.ws.io.entity.SecretQuestionEntity;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.navigation.Button;
import com.anpilog.budget.ws.ui.model.navigation.Field;
import com.anpilog.budget.ws.ui.model.navigation.Switch;
import com.anpilog.budget.ws.ui.model.navigation.TableRow;
import com.anpilog.budget.ws.utils.NumUtils;
import com.anpilog.budget.ws.utils.SeleniumUtils;

public class AccountPageMyPortfolio extends AccountPage {

	private String url = "https://www.bankofamerica.com/";

	// Navigation
	private List<mpTotal> totalsList;
	private Field fldRefreshStatus;
	private Button btnToolsAndTransactions;
	private Button btnTransactions;
	private Button btnAccountSelect;

	// Transactions
	private Switch swtPeriod;

	public AccountPageMyPortfolio(AccountDTO account) throws ConfigurationException {
		super(account);
		valUsername = account.getBank().getUsername();
		valPassword = account.getBank().getPassword();
		if (valUsername == null || valUsername.trim().equals("") || valPassword == null
				|| valPassword.trim().equals(""))
			throw new ConfigurationException("Username/password is not configured for " + account.getBank().getName());
	}

	public void gotoHomePage() {
		webdriver.get(url);
	}

	public void refreshLocators() {
		// Login
		fldUsername = new Field("username", By.id("onlineId1"), getWebdriver(), getWebdriver());
		fldPassword = new Field("password", By.id("passcode1"), getWebdriver(), getWebdriver());
		btnLogin = new Button("login", By.id("signIn"), getWebdriver(), getWebdriver());
		btnLogout = new Button("logout", By.linkText("Sign Out"), getWebdriver(), getWebdriver());

		// Navigation
		fldRefreshStatus = new Field("refresh status", By.xpath("//a[@id='refresh']"), getWebdriver(), getWebdriver());
		btnToolsAndTransactions = new Button("tools and transactions", By.name("onh_tools_and_investing"),
				getWebdriver(), getWebdriver());
		btnTransactions = new Button("transactions", By.linkText("Transactions"), getWebdriver(), getWebdriver());
		btnAccountSelect = new Button("account select", By.id("dropdown_itemAccountId"), getWebdriver(),
				getWebdriver());

		// Transactions
		swtPeriod = new Switch("period switch", By.xpath("div[@id='tPeriodMenuContainer']/ul/li[2]/span[2]"), "select",
				getWebdriver(), getWebdriver());
	}

	public DataRetrievalStatus login() {
		if (SeleniumUtils.checkIfSiteDown(webdriver))
			return DataRetrievalStatus.SERVICE_UNAVAILABLE;
		try {
			fldUsername.setText(valUsername);
			fldPassword.setText(valPassword);
			btnLogin.click();
			return DataRetrievalStatus.COMPLETED;
		} catch (PageElementNotFoundException e) {
			return DataRetrievalStatus.NAVIGATION_BROKEN;
		}
	}

	public Double getTotal() throws ConfigurationException, PageElementNotFoundException {

		// table of totals might be already captured
		if (totalsList == null) {

			logger.info("Capturing all totals on 'My Portfolio' page");

			answerSecretQuestion();

			navigateToMyPortfolioPage();

			totalsList = new ArrayList<mpTotal>();

			try {
				waitingTotalsTableToRefresh();

				captureDebitAccounts();

				captureCreditAccounts();

				logger.info("Accounts total (parsed on page):");
				totalsList.stream().forEach(t -> logger.info(t.toString()));

				// return to the main page (from where Transactions will be
				// opened)
				webdriver.getWebDriver().navigate().back();

			} catch (PageElementNotFoundException e) {
				return null;
			}
		}

		// TODO validation on AccountEntryPoint
		if (account.getMyPortfolioId() == null)
			throw new ConfigurationException(
					"Unable to find total for '" + account.getName() + "' due to missed 'My Portfolio ID' value in DB");

		mpTotal totalRow = totalsList.stream().filter(t -> t.getId().contains(account.getMyPortfolioId())).findFirst()
				.orElse(null);

		if (totalRow != null)
			return totalRow.getAmount();
		else {
			logger.error("Unable to find total for {} by id '{}'", account.getName(), account.getMyPortfolioId());
			webdriver.takeScreenshot();
			return null;
		}
	}

	private void answerSecretQuestion() throws ConfigurationException {
		if (SeleniumUtils.isSecretQuestionShown(webdriver))
			
			logger.info("Answering secret question...");

			fldSecretQuestion = new Field("secret question", By.cssSelector("label"), getWebdriver(), getWebdriver());
			fldSecretAnswer = new Field("secret answer", By.id("tlpvt-challenge-answer"), getWebdriver(), getWebdriver());
			btnSecretSubmit = new Button("submit secret answer", By.id("verify-cq-submit"), getWebdriver(), getWebdriver());
			btnRecognizeNextTime = new Button("pre submit secret answer", By.id("yes-recognize"), getWebdriver(),
					getWebdriver());

			try {
				String secretQuestion = fldSecretQuestion.getText();
				if (secretQuestion == null) {  
					webdriver.takeScreenshot();
					throw new ConfigurationException("No secret question shown on the page");
				}

				SecretQuestionEntity secretAnswer = account.getBank().getSecretQuestions().stream()
						.filter(sq -> sq.getQuestion().equals(secretQuestion)).findFirst().orElse(null);
				if (secretAnswer == null)
					throw new ConfigurationException("Cannot find answer for question "+secretQuestion);
				else
					fldSecretAnswer.setText(secretAnswer.getAnswer());

				btnRecognizeNextTime.clickIfAvailable();
				btnSecretSubmit.click();
			} catch (PageElementNotFoundException e) {
				webdriver.takeScreenshot();
				throw new ConfigurationException(e.getLocalizedMessage());
			}
	}
	
	private void navigateToMyPortfolioPage() throws PageElementNotFoundException {
		if (!webdriver.getWebDriver().getTitle().contains("My Portfolio"))
			gotoMyPortfolioPage();
	}

	private void waitingTotalsTableToRefresh() throws PageElementNotFoundException {
		if (fldRefreshStatus.getText().startsWith("Refreshing")) {
			logger.info("Waiting for refreshing accounts table");
			while (fldRefreshStatus.getText().startsWith("Refreshing")) {
				SeleniumUtils.sleep(5000);
				fldRefreshStatus.setWebElement(null);
			}

			// Needs to come again on 'My Portfolio' page
			// otherwise updated totals not reflected in table
			webdriver.getWebDriver().navigate().back();
			SeleniumUtils.sleep(3000);
			gotoMyPortfolioPage();
			logger.info("All My Portfolio accounts are up to date");
		}
	}

	private void captureDebitAccounts() throws PageElementNotFoundException {

		List<WebElement> debitAccounts = webdriver.findElements(By.xpath("//div[@id='main-table']/div/table/tbody/tr"));
		if (debitAccounts == null)
			throw new PageElementNotFoundException("Unable to find table with debit accounts", webdriver);

		for (WebElement row : debitAccounts) {
			if (row.getText().contains("TOTAL:"))
				break;

			WebElement weId = webdriver.findElementInRow(row, By.xpath("./td/div/div/div[2]/span"));
			if (weId == null)
				throw new PageElementNotFoundException(
						"Unable to find 'my portfolio id' in a table with debit accounts", webdriver);

			WebElement weAmount = webdriver.findElementInRow(row, By.xpath("./td[2]/span/span"));
			if (weAmount == null)
				throw new PageElementNotFoundException("Unable to find 'amount' in a table with debit accounts",
						webdriver);

			String totalLocator = SeleniumUtils.getLocatorForWebElement(row);

			totalsList.add(new mpTotal(weId.getText(), totalLocator,
					NumUtils.convertStringAmountToDouble(weAmount.getText())));
		}
	}

	private void captureCreditAccounts() throws PageElementNotFoundException {
		List<WebElement> creditAccounts = webdriver
				.findElements(By.xpath("//div[@id='main-table']/div[2]/table/tbody/tr"));
		if (creditAccounts == null)
			throw new PageElementNotFoundException("Unable to find table with credit accounts", webdriver);

		for (WebElement row : creditAccounts) {
			if (row.getText().contains("TOTAL:"))
				break;

			WebElement weId = webdriver.findElementInRow(row, By.xpath("./td/div/div/div[2]/span"));
			if (weId == null)
				throw new PageElementNotFoundException(
						"Unable to find 'my portfolio id' in a table with debit accounts", webdriver);

			WebElement weAmount = webdriver.findElementInRow(row, By.xpath("./td[2]/span/span"));
			if (weAmount == null)
				throw new PageElementNotFoundException("Unable to find 'amount' in a table with debit accounts",
						webdriver);

			String totalLocator = SeleniumUtils.getLocatorForWebElement(row);

			totalsList.add(new mpTotal(weId.getText(), totalLocator,
					-NumUtils.convertStringAmountToDouble(weAmount.getText())));
		}
	}

	public void findTransactions(TotalDTO total, List<TransactionDTO> prevTransactions)
			throws PageElementNotFoundException {

		List<TransactionDTO> currentTransactions = new ArrayList<TransactionDTO>();
		getWebdriver().getWebDriver().switchTo().defaultContent();

		if (!webdriver.getWebDriver().getTitle().contains("Transaction")) {
			btnToolsAndTransactions.clickAsAction();
			btnTransactions.click();
		}

		// select account from dropdown list
		try {
			btnAccountSelect.clickIfAvailable();
		} catch (Exception e) {
		}

		List<WebElement> accounts = webdriver.findElements(By.className(" groupItem"));
		if (accounts == null)
			throw new PageElementNotFoundException("Unable to fetch list of accounts", webdriver);

		WebElement weAccount = accounts.stream()
				.filter(a -> !a.getText().equals("") && a.getText().contains(account.getMyPortfolioId())).findFirst()
				.orElse(null);
		if (weAccount == null) {
			logger.error("Unable to filter transactions by account '{}' withing following list:",
					account.getMyPortfolioId());
			accounts.stream().filter(a -> !a.getText().equals("")).forEach(a -> logger.error(a.getText()));
			throw new PageElementNotFoundException(
					"Unable to filter transactions by account '" + account.getMyPortfolioId() + "'", webdriver);
		} else
			webdriver.clickElementWithAction(weAccount);

		// select period for 1 month
		WebElement periodList = webdriver.findElement(By.id("dropdown_dateRangeId"));
		if (periodList != null) {
			if (!periodList.getText().equals("1 month")) {
				periodList.click();
				WebElement periodItem = webdriver.findElement(By.id("custom_multi_select_2_dateRangeId"));
				if (periodItem != null)
					periodItem.click();
			}
		}

		Double difference = total.getDifference();

		// CURRENT PERIOD TRANSACTIONS
		List<WebElement> currentPeriodRows = webdriver.findElements(By.xpath("//table[@id='TxnTable']/tbody/tr"));
		if (currentPeriodRows == null)
			logger.info("No rows found in the current period table");
		else {
			logger.info("Rows in the current period table: {}", currentPeriodRows.size());
			for (WebElement row : currentPeriodRows) {
				logger.info("Row in the current period table: {}",
						row.getText().replaceAll("\\n", "").replaceAll("\\r", ""));
				if (SeleniumUtils.isPending(row.getText()))
					continue;

				// Parsing row
				TableRow tr = new TableRow("transaction row", By.xpath("./td/b"), 4, By.xpath("./td[4]/div/span"),
						By.xpath("./td[2]/div"), By.xpath("./td[3]/div"), row, getWebdriver());

				if (!isTransactionExist(prevTransactions, tr.getDate(), tr.getAmount())) {

					currentTransactions.add(new TransactionDTO(total, tr.getDate(), tr.getDescription(), tr.getAmount(),
							tr.getCategory()));

					// Refreshing remaining difference
					difference = NumUtils.roundDouble(difference - tr.getAmount());
					logger.info("Amount: {}, diff: {}", tr.getAmount(), difference);
					if (difference == 0.0) {
						webdriver.getWebDriver().navigate().back();
						total.setTransactions(currentTransactions);
						return;
					}
				}
			}
		}

		// PREVIOUS PERIOD TRANSACTIONS
		List<WebElement> previousPeriodRows;
		// lets see how it will go... not many accounts reach that point
		// for some accounts previous period transactions are not considered
		// (i.e. WF)
		swtPeriod.perform();

		// Wait for previous transactions table to be loaded
		SeleniumUtils.sleep(5000);

		previousPeriodRows = webdriver.findElements(By.xpath("//table[@id='TxnTable']/tbody/tr"));
		logger.info("Rows in the previous period table: {}", previousPeriodRows.size());

		for (WebElement row : previousPeriodRows) {
			if (SeleniumUtils.isPending(row.getText()))
				continue;

			// Parsing row
			TableRow tr = new TableRow("transaction row", By.xpath("./td/b"), 1, By.xpath("./td[4]/div/span"),
					By.xpath("./td[2]/div"), By.xpath("./td[3]/div"), row, getWebdriver());

			if (!isTransactionExist(prevTransactions, tr.getDate(), tr.getAmount())
					&& !isTransactionExist(currentTransactions, tr.getDate(), tr.getAmount())) {

				currentTransactions.add(
						new TransactionDTO(total, tr.getDate(), tr.getDescription(), tr.getAmount(), tr.getCategory()));

				// Refreshing remaining difference
				difference = NumUtils.roundDouble(difference - tr.getAmount());
				logger.info("Amount: {}, diff: {}", tr.getAmount(), difference);
				if (difference == 0.0) {
					webdriver.getWebDriver().navigate().back();
					total.setTransactions(currentTransactions);
					return;
				}
			}
		}
	}

	private void gotoMyPortfolioPage() throws PageElementNotFoundException {

		logger.info("Navigating to 'My Portfolio' page");

		webdriver.switchTo().defaultContent();

		new Button("tools and investing", By.name("onh_tools_and_investing"), getWebdriver(), getWebdriver())
				.clickAsAction();
		new Button("my portfolio", By.name("onh_tools_and_investing_my_portfolio"), getWebdriver(), getWebdriver())
				.click();

		webdriver.waitFrameToBeAvailableAndSwitchToIt("htmlhelp");

	}

	class mpTotal {
		private String id;
		private String locator;
		private Double amount;

		public mpTotal(String id, String locator, Double amount) {
			this.id = id;
			this.locator = locator;
			this.amount = amount;
		}

		public String getId() {
			return id;
		}

		public String getLocator() {
			return locator;
		}

		public Double getAmount() {
			return amount;
		}

		@Override
		public String toString() {
			return "mpTotal [id=" + id + ", locator=" + locator + ", amount=" + amount + "]";
		}
	}

	public void quit() {
		try {
			btnLogout.click();
			logger.info("Bank page {} was closed", account.getBank().getName());
		} catch (PageElementNotFoundException | WebDriverException e) {
			logger.error("Bank page {} was not closed properly", account.getBank().getName());
		}

		webdriver.quit();
	}
}
