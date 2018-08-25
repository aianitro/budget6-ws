package com.anpilog.budget.ws.ui.model.pages;

import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anpilog.budget.ws.core.UberWebDriver;
import com.anpilog.budget.ws.exceptions.ConfigurationException;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.navigation.Button;
import com.anpilog.budget.ws.ui.model.navigation.Field;
import com.anpilog.budget.ws.ui.model.navigation.Switch;
import com.anpilog.budget.ws.ui.model.navigation.TableRow;

public abstract class AccountPage implements Page {

	protected static Logger logger = LoggerFactory.getLogger(AccountPage.class);

	//private AccountPageSecretQuestions pageQuestions;

	protected AccountDTO account;

	protected UberWebDriver webdriver;
	protected WebDriverWait wait;

	// Login
	protected Field fldUsername;
	protected String valUsername;
	protected Field fldPassword;
	protected String valPassword;
	protected Button btnLogin;
	protected Button btnLogout;

	// Total
	protected Field fldBalance;

	// Transactions
	protected Button btnTransactionsPage;
	protected TableRow trTransaction;
	protected Switch swtPeriod;
	
	// Secret questions
	protected Field fldSecretQuestion;
	protected Field fldSecretAnswer;
	protected Button btnSecretSubmit;
	protected Button btnRecognizeNextTime;

	public AccountPage(AccountDTO accountDto) {
		this.account = accountDto;

		this.webdriver = new UberWebDriver();
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		//pageQuestions = new AccountPageSecretQuestions(accountDto, webDriver);
		
		refreshLocators();
	}

	public AccountDTO getAccount() {
		return this.account;
	}

	public void setAccount(AccountDTO accountDto) {
		logger.info("Setting account.. {}", accountDto.getName());
		this.account = accountDto;
		
		refreshLocators();
	}
	
	public abstract void refreshLocators();

	public abstract void gotoHomePage();

	public abstract DataRetrievalStatus login();

	public abstract Double getTotal() throws PageElementNotFoundException, ConfigurationException;

	public abstract void findTransactions(TotalDTO totalDto, List<TransactionDTO> prevTransactions) throws PageElementNotFoundException;

	protected boolean isTransactionExist(List<TransactionDTO> prevTransactions, LocalDate trDate, Double trAmount) {
		return prevTransactions.stream()
				.filter(t -> t.getDate().equals(trDate) && t.getAmount() == trAmount && t.getTotal().getAccount() == account)
				.count() > 0;
	}

	public int getScore() throws PageElementNotFoundException {
		return 0;
	}

	@Override
	public UberWebDriver getWebdriver() {
		return webdriver;
	}

	public abstract void quit();
}
