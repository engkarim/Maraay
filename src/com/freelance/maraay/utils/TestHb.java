package com.freelance.maraay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.freelance.maraay.dao.LuDirRepDrivDao;

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
		String dateInString2 = "2015-06-10";
		Date date1 = formatter.parse(dateInString1);
		Date date2 = formatter.parse(dateInString2);

		
		System.out.println(	LuDirRepDrivDao.getInstance().findRepByIdUpdate(7, date2,12));
	
	

	}
}
