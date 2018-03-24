package com.anpilog.budget.ws.utils;

public class NumUtils {
	
	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

	public static double convertStringAmountToDouble(String stringAmount) {
		String out = stringAmount.replace("+", "");
		out = out.replace("–", "-");
		out = out.replace("$", "");
		out = out.replace(" ", "");
		out = out.replace(",", "");
		out = out.replace(" dollars and", "");
		out = out.replace(" Cents", "");
		out = out.replace("(pending)", "");
		out = out.replace("(", "");
		out = out.replace(")", "");

		return Double.parseDouble(out);
	}

	public static Double wrapAmount(Double input) {
		if (input == -0.00)
			return 0.00;
		else
			return input;
	}
	
	public static String amountToString(Double amount) {
		if (amount == null)
			return "N/A";
		if (amount == 0.0)
			return "-";

		return String.valueOf(roundDouble(amount));
	}

	public static String amountToStringWithSign(Double amount) {
		if (amount == null)
			return "N/A";
		if (amount == 0.0)
			return "-";
		if (amount > 0.0)
			return "<font color='green'>+" + String.valueOf(roundDouble(amount)) + "</font>";
		else
			return "<font color='red'>" + String.valueOf(roundDouble(amount)) + "</font>";
	}

	public static double roundDouble(double input) {
		return Math.round(input * 100.0) / 100.0;
	}

}
