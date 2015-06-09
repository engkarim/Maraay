package com.freelance.maraay.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class Utils implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Utils uniqueInstance;

	private Utils() {

	}

	public static Utils getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Utils();
		}
		return uniqueInstance;
	}

	public Timestamp getCurrentTimeStamp() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	public Date parseToDate(String dateInString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(dateInString);
		return date;
	}

	public String parseToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = formatter.format(date);
		return stringDate;
	}

	public Date getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// System.out.println(formatter.format(date));
		return date;
	}

	public double roundDouble(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	public void sendRedirect(String s, boolean fullPath) {
		if (!fullPath) {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			s = request.getContextPath() + s;
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(s);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isLong(String s) {
		try {
			Long.parseLong(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public Date incrementDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, 1);
		Date incrementedDate = cal.getTime();
		return incrementedDate;

	}

	public Date decrementDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, -1);
		Date incrementedDate = cal.getTime();
		return incrementedDate;

	}

}
