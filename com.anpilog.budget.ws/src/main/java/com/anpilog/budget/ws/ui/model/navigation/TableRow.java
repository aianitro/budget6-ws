package com.anpilog.budget.ws.ui.model.navigation;

import java.time.LocalDate;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import com.anpilog.budget.ws.core.UberWebDriver;
import com.anpilog.budget.ws.exceptions.PageElementNotFoundException;
import com.anpilog.budget.ws.utils.DateUtils;
import com.anpilog.budget.ws.utils.NumUtils;


public class TableRow extends PageElement {

	private Label lblTransactionDate;
	private Integer valTransactionDateFormat;
	private Label lblTransactionAmount;
	private Label lblTransactionDescription;
	private Label lblTransactionCategory;

	public TableRow(String name, By locDate, Integer dateFormat, By locAmount, By locDescription, By locCategory,
			SearchContext searchContext, UberWebDriver webdriver) {
		super(name, null, searchContext, webdriver);

		lblTransactionDate = new Label("transaction date", locDate, searchContext, webdriver);
		valTransactionDateFormat = dateFormat;
		lblTransactionAmount = new Label("transaction amount", locAmount, searchContext, webdriver);
		lblTransactionDescription = new Label("transaction description", locDescription, searchContext, webdriver);
		lblTransactionCategory = new Label("transaction category", locCategory, searchContext, webdriver);

	}

	public LocalDate getDate() throws PageElementNotFoundException {
		return DateUtils.convertStringToDateByType(lblTransactionDate.getText(), valTransactionDateFormat);
	}

	public Double getAmount() throws PageElementNotFoundException {
		return NumUtils.convertStringAmountToDouble(lblTransactionAmount.getText());
	}

	public String getDescription() throws PageElementNotFoundException {
		return lblTransactionDescription.getText().trim().replace("\n", "-");
	}

	public String getCategory() throws PageElementNotFoundException {
		if (lblTransactionCategory.getLocator() == null)
			return "";
		else
			return lblTransactionCategory.getText();
	}

	@Override
	public String toString() {
		return "Table row [name=" + name + ", locator=" + locator + ", webElement=" + webElement + "]";
	}

}
