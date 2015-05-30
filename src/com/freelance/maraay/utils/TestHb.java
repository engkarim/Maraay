package com.freelance.maraay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.freelance.maraay.beans.ComEquationBean;
import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.ComEquationDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.RepFirstLoadingDao;
import com.freelance.maraay.dao.RepInvoiceDao;
import com.freelance.maraay.dao.RepLastLoadingDao;
import com.freelance.maraay.dao.RepTotalLoadingDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.model.TblRepInvoice;
import com.freelance.maraay.model.TblRepTotalLoadingDate;
import com.freelance.maraay.model.TblRepTotalLoadingValue;

public class TestHb {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		/*
		 * User u = new User(); // UserType ut = new UserType(); //
		 * ut.setPrivilege("karim"); u.setName("Karim"); u.setUserName("admin");
		 * u.setPassword("admin"); // u.setUserType(userType);
		 */
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString1 = "2015-01-1";
		String dateInString2 = "2015-04-26";
		Date date1 = formatter.parse(dateInString1);
		Date date2 = formatter.parse(dateInString2);

		
		List<TblComDiscountDate> checkedDiscountList = ComDiscountingDao.getInstance().findByCompletedCalue(0);
		AppUtils appUtils = new AppUtils();
		appUtils.deleteNonCompletedCom(checkedDiscountList);


	}
}
