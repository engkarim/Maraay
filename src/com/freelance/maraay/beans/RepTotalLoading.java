package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import com.freelance.maraay.dao.ComSalesDao;
import com.freelance.maraay.dao.RepNewLoadingDao;
import com.freelance.maraay.dao.RepSalesDao;
import com.freelance.maraay.dao.RepTotalLoadingDao;
import com.freelance.maraay.model.TblComSalesDate;
import com.freelance.maraay.model.TblRepNewLoadingDate;
import com.freelance.maraay.model.TblRepSalesDate;
import com.freelance.maraay.model.TblRepTotalLoadingDate;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class RepTotalLoading implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	RepTotalLoadingDao repTotalLoadingDao = RepTotalLoadingDao.getInstance();

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	TblRepTotalLoadingDate totalLoadingDate = new TblRepTotalLoadingDate();

	public TblRepTotalLoadingDate getTotalLoadingDate() {
		totalLoadingDate = repTotalLoadingDao.findByDate(
				loginBean.getRepDailyDate(), loginBean.getRepDirectionId());
		return totalLoadingDate;
	}

	public void prerendre(ComponentSystemEvent event) {
		getTotalLoadingDate();
	}
	
	/********************* update logic **********************/

	TblRepTotalLoadingDate updateTotalLoadingDate = new TblRepTotalLoadingDate();

	public TblRepTotalLoadingDate getUpdateTotalLoadingDate() {
		return updateTotalLoadingDate;
	}

	public void setUpdateTotalLoadingDate(
			TblRepTotalLoadingDate updateTotalLoadingDate) {
		this.updateTotalLoadingDate = updateTotalLoadingDate;
	}

	public void prerenderUpdate(ComponentSystemEvent event) {
		TblRepTotalLoadingDate	searchedDate = repTotalLoadingDao.findByDate(
				loginBean.getUpdateRepDailyDate() , loginBean.getUpdateRepDirectionId());

		if (searchedDate == null) {
			Utils.getInstance().sendRedirect(Constants.loginPage, false);
		} else {
			setUpdateTotalLoadingDate(searchedDate);
		}
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
