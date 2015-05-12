package com.freelance.maraay.beans;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblComDiscountValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class ComDiscountBean {
	ComDiscountingDao comDiscountingDao = ComDiscountingDao.getInstance();
	ProductDao productDao = ProductDao.getInstance();
	Utils utils = Utils.getInstance();

	@ManagedProperty("#{productBean}")
	private ProductBean productBean;

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	@ManagedProperty("#{service}")
	private Service service;

	public void setService(Service service) {
		this.service = service;
	}

	private TblComDiscountDate discountDate = new TblComDiscountDate();
	private List<Product> allProducts;
	private List<TblComDiscountDate> discountDates;
	private List<TblComDiscountDate> filteredDates;
	private List<TblComDiscountValue> discountValues = new ArrayList<TblComDiscountValue>();

	@PostConstruct
	public void init() {
		discountDates = service.findAllDiscountsDates();
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblComDiscountValue newDiscountValue = new TblComDiscountValue();
			newDiscountValue.setProductId(product);
			discountValues.add(newDiscountValue);
		}
	}

	public List<TblComDiscountDate> getDiscountDates() {
		return discountDates;
	}

	public List<TblComDiscountValue> getDiscountValues() {
		return discountValues;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public TblComDiscountDate getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(TblComDiscountDate discountDate) {
		this.discountDate = discountDate;
	}

	public List<TblComDiscountDate> getFilteredDates() {
		return filteredDates;
	}

	public void setFilteredDates(List<TblComDiscountDate> filteredDates) {
		this.filteredDates = filteredDates;
	}

	public String insertNewDiscount() {
		try {
			List<TblComDiscountValue> lastDiscountValues = new ArrayList<TblComDiscountValue>();
			// insert incoming date
			discountDate.setByUser(new User(loginBean.getId()));
			comDiscountingDao.insertDate(discountDate);
			// insert incoming values
			for (TblComDiscountValue discountValue : discountValues) {
				// calculate mount price
				discountValue.setDiscountDate(discountDate);
				lastDiscountValues.add(discountValue);
				comDiscountingDao.insertValue(discountValue);
			}
			discountDate.setTblComDiscountValueList(lastDiscountValues);
			comDiscountingDao.updateDate(discountDate);
			// set daily inserting date
			loginBean.setCompanyDailyDate(discountDate.getDate());
			return "newIncoming";
		} catch (Exception e) {
			return "fail";
		}

	}

	/****************** update discount logic ********************/

	private Date updatedDate;

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	private TblComDiscountDate updatedDiscountDate;

	public TblComDiscountDate getUpdatedDiscountDate() {
		return updatedDiscountDate;
	}

	public void setUpdatedDiscountDate(TblComDiscountDate updatedDiscountDate) {
		this.updatedDiscountDate = updatedDiscountDate;
	}

	public void prerender(ComponentSystemEvent event) {
		TblComDiscountDate searchedDiscountDate = comDiscountingDao
				.findByDate(loginBean.getCompanyDailyUpdatedDate());
		if (searchedDiscountDate == null) {
			utils.sendRedirect(Constants.loginPage, false);
		} else {
			setUpdatedDiscountDate(searchedDiscountDate);
		}
	}

	public String navigateToUpdate() {
		loginBean.setCompanyDailyUpdatedDate(updatedDate);
		return "updateDiscount";
	}

	public String updateDiscount() {
		try {
			List<TblComDiscountValue> lastDiscountValues = new ArrayList<TblComDiscountValue>();

			// update discount date
			updatedDiscountDate.setByUser(new User(loginBean.getId()));
			comDiscountingDao.updateDate(updatedDiscountDate);

			// update discount values
			for (TblComDiscountValue discountValue : updatedDiscountDate
					.getTblComDiscountValueList()) {
				discountValue.setDiscountDate(updatedDiscountDate);
				lastDiscountValues.add(discountValue);
				comDiscountingDao.updateValue(discountValue);
			}
			updatedDiscountDate.setTblComDiscountValueList(lastDiscountValues);
			comDiscountingDao.updateDate(updatedDiscountDate);
			return "updateIncoming";
		} catch (Exception e) {
			return "fail";
		}

	}

	/******************* validation logic ****************/

	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPLICATEDATE");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblComDiscountDate searchedDate = comDiscountingDao
				.findByDate(todayDate);

		// Let required="true" do its job.
		if (todayDate == null) {
			return;
		}

		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();

		}

		if (todayDate.after(today)) {
			FacesMessage msg = new FacesMessage(InvalidDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}

	public void validateUpdatedDate(ComponentSystemEvent event)
			throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String NOTFOUNDDateMsg = bundle.getString("UPDATEDDATENOTFOUND");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblComDiscountDate searchedDate = comDiscountingDao
				.findByDate(todayDate);

		// Let required="true" do its job.
		if (todayDate == null) {
			return;
		}

		if (searchedDate == null) {
			FacesMessage msg = new FacesMessage(NOTFOUNDDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();

		}

		if (todayDate.after(today)) {
			FacesMessage msg = new FacesMessage(InvalidDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}

}
