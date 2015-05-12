package com.freelance.maraay.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.ComEquationDao;
import com.freelance.maraay.dao.ComSalesDao;
import com.freelance.maraay.dao.PayTypeDao;
import com.freelance.maraay.model.PayType;
import com.freelance.maraay.model.TblComCalculationEquation;
import com.freelance.maraay.model.TblComSalesDate;
import com.freelance.maraay.model.TblComSupplyValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.Performance;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class ComEquationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PayType> payTypes;
	Utils utils = Utils.getInstance();

	public List<PayType> getPayTypes() {
		payTypes = PayTypeDao.getInstance().listAllTypes();
		return payTypes;
	}

	ComEquationDao comEquationDao = ComEquationDao.getInstance();

	private List<TblComSupplyValue> supplyValues;
	private List<TblComSupplyValue> filterdValues;
	private List<TblComCalculationEquation> filteredEqs;
	private List<TblComCalculationEquation> eqs;
	private TblComSupplyValue comSupplyValue = new TblComSupplyValue();
	private TblComCalculationEquation calculationEquation = new TblComCalculationEquation();
	private double totalSales;

	public double getTotalSales() {
		return totalSales;
	}

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public TblComSupplyValue getComSupplyValue() {
		return comSupplyValue;
	}

	public void setComSupplyValue(TblComSupplyValue comSupplyValue) {
		this.comSupplyValue = comSupplyValue;
	}

	public TblComCalculationEquation getCalculationEquation() {
		return calculationEquation;
	}

	public void setCalculationEquation(
			TblComCalculationEquation calculationEquation) {
		this.calculationEquation = calculationEquation;
	}

	public List<TblComCalculationEquation> getFilteredEqs() {
		return filteredEqs;
	}

	public void setFilteredEqs(List<TblComCalculationEquation> filteredEqs) {
		this.filteredEqs = filteredEqs;
	}

	public List<TblComCalculationEquation> getEqs() {
		eqs = comEquationDao.listAllEqs();
		return eqs;
	}

	public List<TblComSupplyValue> getSupplyValues() {
		supplyValues = comEquationDao.listAllSupplyValues();
		return supplyValues;
	}

	public List<TblComSupplyValue> getFilterdValues() {
		return filterdValues;
	}

	public void setFilterdValues(List<TblComSupplyValue> filterdValues) {
		this.filterdValues = filterdValues;
	}

	public String insertNewEqs() {
		Session session = null;
		Transaction tx = null;
		try {
			// get sales values
			TblComSalesDate salesDate = ComSalesDao.getInstance().findByDate(
					calculationEquation.getDate());
			double totalAfter = salesDate.getTotalAfter();
			double totalBefore = salesDate.getTotalBefore();
			double discountValue = totalBefore - totalAfter;
			double minus_plus = 0.0;
			double total_supply = comSupplyValue.getSupplyValue();
			// check if date existed and then insert equation date
			TblComCalculationEquation searchedDate = comEquationDao
					.findByDate(calculationEquation.getDate());

			if (searchedDate == null) {
				minus_plus = comSupplyValue.getSupplyValue() - totalAfter;
				session = SessionFactoryUtil.getSession();
				calculationEquation.setTotalBefore(totalBefore);
				calculationEquation.setTotalAfter(totalAfter);
				calculationEquation.setDiscountValue(discountValue);
				calculationEquation.setTotalSupplyValue(comSupplyValue
						.getSupplyValue());
				calculationEquation.setMinPlus(minus_plus);
				session.persist(calculationEquation);
				comSupplyValue.setCalcEquId(calculationEquation);
				comSupplyValue.setByUserId(new User(loginBean.getId()));
				session = SessionFactoryUtil.getSession();
				session.persist(comSupplyValue);
			} else {
				comSupplyValue.setCalcEquId(searchedDate);
				comSupplyValue.setByUserId(new User(loginBean.getId()));
				session = SessionFactoryUtil.getSession();
				session.persist(comSupplyValue);

				for (TblComSupplyValue supplyValue : searchedDate
						.getTblComSupplyValueList()) {
					total_supply = total_supply + supplyValue.getSupplyValue();
				}
				searchedDate.setTotalBefore(totalBefore);
				searchedDate.setTotalAfter(totalAfter);
				searchedDate.setDiscountValue(discountValue);
				searchedDate.setTotalSupplyValue(total_supply);
				minus_plus = total_supply - totalAfter;
				searchedDate.setMinPlus(minus_plus);
				session = SessionFactoryUtil.getSession();
				session.update(searchedDate);
			}
			tx = session.beginTransaction();
			tx.commit();
			return "listEqs";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
			Performance.releaseMemory();
		}
	}

	/************* update logic *******************/
	private String passedParam;
	private Long supplyId;
	private TblComSupplyValue updatedSupplyValue;

	public String getPassedParam() {
		return passedParam;
	}

	public void setPassedParam(String passedParam) {
		if (passedParam != null) {
			if (utils.isLong(passedParam)) {
				setSupplyId(Long.parseLong(passedParam));
			} else {
				utils.sendRedirect(Constants.errorPage, false);
			}
		} else {
			utils.sendRedirect(Constants.errorPage, false);
		}
	}

	public Long getSupplyId() {
		return supplyId;
	}

	public TblComSupplyValue getUpdatedSupplyValue() {
		return updatedSupplyValue;
	}

	public void setUpdatedSupplyValue(TblComSupplyValue updatedSupplyValue) {
		this.updatedSupplyValue = updatedSupplyValue;
	}

	public void setSupplyId(Long supplyId) {
		if (supplyId == 0) {
			utils.sendRedirect(Constants.errorPage, false);
		}
		this.supplyId = supplyId;
	}

	public void prerender(ComponentSystemEvent event) {
		TblComSupplyValue searchedSupplyValue = comEquationDao
				.findSupplyById(getSupplyId());
		if (searchedSupplyValue == null) {
			utils.sendRedirect(Constants.errorPage, false);
		} else {
			setUpdatedSupplyValue(searchedSupplyValue);
		}

	}

	public String updateEq() {
		Session session = null;
		Transaction tx = null;
		try {
			if (updatedSupplyValue.getPayTypeId().getPayTypeId() != 2) {
				updatedSupplyValue.setBankName(null);
				updatedSupplyValue.setNoDeposite(null);
			} else {
				updatedSupplyValue.setRecieverName(null);
			}

			session = SessionFactoryUtil.getSession();
			session.update(updatedSupplyValue);

			// get caldate and update
			TblComCalculationEquation calculationEquation_value = comEquationDao
					.findById(updatedSupplyValue.getCalcEquId().getCalcEquId());

			double total_supply = 0.0;
			double minus_plus = 0.0;
			for (TblComSupplyValue v : calculationEquation_value
					.getTblComSupplyValueList()) {
				total_supply = total_supply + v.getSupplyValue();
			}
			minus_plus = total_supply
					- calculationEquation_value.getTotalAfter();

			calculationEquation_value.setTotalSupplyValue(total_supply);
			calculationEquation_value.setMinPlus(minus_plus);
			session = SessionFactoryUtil.getSession();
			session.update(calculationEquation_value);
			tx = session.beginTransaction();
			tx.commit();
			return "listEqs";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
			Performance.releaseMemory();
		}

	}

	/******************** search logic ***********************/

	private Date startDate;
	private Date endDate;
	private List<TblComCalculationEquation> searchedDates;

	public List<TblComCalculationEquation> getSearchedDates() {
		searchedDates = comEquationDao.listByDate(startDate, endDate);
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
	private Double totSupplyValue;
	private Double totMinusOrPlus;

	public Double getTotMinusOrPlus() {
		double tot = 0.0;
		for (TblComCalculationEquation d : searchedDates) {
			tot = tot + d.getMinPlus();
		}
		totMinusOrPlus = tot;
		return totMinusOrPlus;
	}

	public Double getTotSupplyValue() {
		double tot = 0.0;
		for (TblComCalculationEquation d : searchedDates) {
			tot = tot + d.getTotalSupplyValue();
		}
		totSupplyValue = tot;
		return totSupplyValue;
	}

	public Double getTotTotalAfter() {
		double tot = 0.0;
		for (TblComCalculationEquation d : searchedDates) {
			tot = tot + d.getTotalAfter();
		}
		totTotalAfter = tot;
		return totTotalAfter;
	}

	public Double getTotTotalBefore() {
		double tot = 0.0;
		for (TblComCalculationEquation d : searchedDates) {
			tot = tot + d.getTotalBefore();
		}
		totTotalBefore = tot;
		return totTotalBefore;
	}

	public Double getTotDiscount() {
		Double discount = 0.0;
		for (TblComCalculationEquation d : searchedDates) {
			discount = discount + d.getDiscountValue();
		}
		totDiscount = discount;
		return totDiscount;
	}

	/******************* validation ******************/

	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPLICATEDATE");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");
		String noSalesDateMsg = bundle.getString("NOSALESTOTAL");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblComSalesDate searchedSalesDate = ComSalesDao.getInstance()
				.findByDate(todayDate);
		TblComCalculationEquation searchedDate = comEquationDao
				.findByDate(todayDate);

		// Let required="true" do its job.
		if (todayDate == null) {
			return;
		}

		/*
		 * if (searchedDate != null) { FacesMessage msg = new
		 * FacesMessage(duplicatedDateMsg);
		 * msg.setSeverity(FacesMessage.SEVERITY_ERROR); fc.addMessage(todayId,
		 * msg); fc.renderResponse();
		 * 
		 * }
		 */

		if (todayDate.after(today)) {
			FacesMessage msg = new FacesMessage(InvalidDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}

		if (searchedSalesDate == null) {
			FacesMessage msg = new FacesMessage(noSalesDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}

	}

}
