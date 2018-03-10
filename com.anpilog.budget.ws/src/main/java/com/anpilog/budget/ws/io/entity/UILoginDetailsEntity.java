package com.anpilog.budget.ws.io.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.openqa.selenium.By;

import com.anpilog.budget.ws.utils.SeleniumUtils;

@Entity(name = "ui_login_details")
public class UILoginDetailsEntity {

	@Id
	@GeneratedValue
	private int id;
	//@OneToOne(mappedBy = "uiLoginDetails")
	private String usernameLocator;
	private String usernameValue;
	private String passwordLocator;
	private String passwordValue;
	private String loginLocator;
	private String logoutLocator;

	public UILoginDetailsEntity(String usernameLocator, String usernameValue, String passwordLocator, String passwordValue,
			String loginLocator, String logoutLocator) {
		this.usernameLocator = usernameLocator;
		this.usernameValue = usernameValue;
		this.passwordLocator = passwordLocator;
		this.passwordValue = passwordValue;
		this.loginLocator = loginLocator;
		this.logoutLocator = logoutLocator;
	}

	public int getId() {
		return id;
	}

	public By getUsernameLocator() {
		return SeleniumUtils.getByLocator(usernameLocator);
	}

	public String getUsernameValue() {
		return usernameValue;
	}

	public By getPasswordLocator() {
		return SeleniumUtils.getByLocator(passwordLocator);
	}

	public String getPasswordValue() {
		return passwordValue;
	}

	public By getLoginLocator() {
		return SeleniumUtils.getByLocator(loginLocator);
	}

	public By getLogoutLocator() {
		return SeleniumUtils.getByLocator(logoutLocator);
	}

}
