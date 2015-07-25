package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.freelance.maraay.dao.RepSalesDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.TblRepSalesDate;

@ManagedBean
@ViewScoped
public class RepSalesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	RepSalesDao repSalesDao = RepSalesDao.getInstance();

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	private List<TblRepSalesDate> filteredDates;
	private List<TblRepSalesDate> salesDates;

	public List<TblRepSalesDate> getSalesDates() {
		System.out.println(loginBean.getRepDailyDate() + " ............. " + loginBean.getRepDirectionId());
		salesDates = repSalesDao.findByDateList(loginBean.getRepDailyDate(),loginBean.getRepDirectionId());
		return salesDates;
	}

	public List<TblRepSalesDate> getFilteredDates() {
		return filteredDates;
	}

	public void setFilteredDates(List<TblRepSalesDate> filteredDates) {
		this.filteredDates = filteredDates;
	}

	/******************** search logic ***********************/

	// private Date startDate;
	// private Date endDate;
	// private List<TblComSalesDate> searchedDates;
	//
	// public List<TblComSalesDate> getSearchedDates() {
	// searchedDates = comSalesDao.listByDate(startDate, endDate);
	// return searchedDates;
	// }
	//
	// public Date getStartDate() {
	// return startDate;
	// }
	//
	// public void setStartDate(Date startDate) {
	// this.startDate = startDate;
	// }
	//
	// public Date getEndDate() {
	// return endDate;
	// }
	//
	// public void setEndDate(Date endDate) {
	// this.endDate = endDate;
	// }
	//
	// private Double totTotalAfter;
	// private Double totTotalBefore;
	// private Double totDiscount;
	//
	// public Double getTotTotalAfter() {
	// double tot = 0.0;
	// for (TblComSalesDate d : searchedDates) {
	// tot = tot + d.getTotalAfter();
	// }
	// totTotalAfter = tot;
	// return totTotalAfter;
	// }
	//
	// public Double getTotTotalBefore() {
	// double tot = 0.0;
	// for (TblComSalesDate d : searchedDates) {
	// tot = tot + d.getTotalBefore();
	// }
	// totTotalBefore = tot;
	// return totTotalBefore;
	// }
	//
	// public Double getTotDiscount() {
	// Double discount = 0.0;
	// for (TblComSalesDate d : searchedDates) {
	// discount = discount + d.getDiscountValue();
	// }
	// totDiscount = discount;
	// return totDiscount;
	// }

}
