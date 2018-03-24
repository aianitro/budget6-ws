package com.anpilog.budget.ws.utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	public static boolean isDateToday(LocalDate date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date).equals(sdf.format(new Date()));
	}
	
	public static boolean isDateToday(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date).equals(sdf.format(new Date()));
	}

	public static boolean isDateThisMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		return sdf.format(date).equals(sdf.format(new Date()));
	}

	public static String formatDateForEmail(Date date) {
		return (isDateToday(date)) ? "today" : convertDateToStringType2(date);
	}

	static String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month - 1];
	}

	static int getCurrentMonthInt() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.MONTH) + 1;
	}

	static String[] getCurrentQuaterMonths() {
		// define number of current month
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int currentMonth = cal.get(Calendar.MONTH);
		int quater = Math.floorDiv(currentMonth, 3);

		// retrieving all months
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();

		String result[] = new String[3];
		result[0] = months[quater * 3];
		result[1] = months[quater * 3 + 1];
		result[2] = months[quater * 3 + 2];

		return result;
	}

	public static Date getFirstDayOfQuarter(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static Boolean isDateInBetween(Date date, Date start, Date end) {
		return (date.getTime() >= start.getTime() && date.getTime() <= end.getTime());
	}

	public static Date getFirstDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getFirstDayOfMonthsBack(int monthsBack) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthsBack);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getLastDayOfMonthsBack(int monthsBack) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthsBack);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static Date convertStringToDateByType(String string, int type) {
		string = string.replace("\n", " ");
		try {
			switch (type) {
			case 0:
				return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(string.trim());
			case 1:
				return new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH).parse(string.trim());
			case 2:
				return new SimpleDateFormat("MMM. dd, yyyy", Locale.ENGLISH).parse(string.trim());
			case 3:
				return new SimpleDateFormat("MMMdd yyyy", Locale.ENGLISH)
						.parse(string.trim() + " " + Calendar.getInstance().get(Calendar.YEAR));
			case 4:
				return new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(string.trim());
			case 5:
				return new SimpleDateFormat("MMMMM dd, yyyy", Locale.ENGLISH).parse(string.trim());
			case 6:
				return new SimpleDateFormat("MMMMM dd yyyy", Locale.ENGLISH).parse(string.trim());
			case 7:
				return new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).parse(string.trim());
			case 8:
				return new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
						.parse(string.trim() + ", " + new Date().getYear());
			}
		} catch (ParseException ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}
	
	public static String convertDateToStringType1(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String convertDateToStringType2(Date date) {
		return new SimpleDateFormat("MM/dd").format(date);
	}

	public static String convertDateToStringType3(Date date) {
		return new SimpleDateFormat("yyyy-MM").format(date);
	}

	public static java.sql.Timestamp getTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}


}
