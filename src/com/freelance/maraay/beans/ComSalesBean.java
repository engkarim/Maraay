package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.freelance.maraay.dao.ComSalesDao;
import com.freelance.maraay.model.TblComSalesDate;

@ManagedBean
@ViewScoped
public class ComSalesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ComSalesDao comSalesDao = ComSalesDao.getInstance();

	private List<TblComSalesDate> filteredDates;
	private List<TblComSalesDate> salesDates;
	
	public List<TblComSalesDate> getSalesDates() {
		salesDates = comSalesDao.listAllSalesDates();
		return salesDates;
	}

	public List<TblComSalesDate> getFilteredDates() {
		return filteredDates;
	}

	public void setFilteredDates(List<TblComSalesDate> filteredDates) {
		this.filteredDates = filteredDates;
	}
	
	/******************** search logic ***********************/

	private Date startDate;
	private Date endDate;
	private List<TblComSalesDate> searchedDates;

	public List<TblComSalesDate> getSearchedDates() {
		searchedDates = comSalesDao.listByDate(startDate, endDate);
		return searchedDates;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private Double totTotalAfter;
	private Double totTotalBefore;
	private Double totDiscount;

	public Double getTotTotalAfter() {
		double tot = 0.0;
		for (TblComSalesDate d : searchedDates) {
			tot = tot + d.getTotalAfter();
		}
		totTotalAfter = tot;
		return totTotalAfter;
	}

	public Double getTotTotalBefore() {
		double tot = 0.0;
		for (TblComSalesDate d : searchedDates) {
			tot = tot + d.getTotalBefore();
		}
		totTotalBefore = tot;
		return totTotalBefore;
	}

	public Double getTotDiscount() {
		Double discount = 0.0;
		for (TblComSalesDate d : searchedDates) {
			discount = discount + d.getDiscountValue();
		}
		totDiscount = discount;
		return totDiscount;
	}
	
	
}
