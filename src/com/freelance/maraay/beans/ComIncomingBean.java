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
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblComDiscountValue;
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.model.TblComIncomingValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class ComIncomingBean {
	ComIncomingDao comIncomingDao = ComIncomingDao.getInstance();
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

	private TblComIncomingDate incomingDate = new TblComIncomingDate();
	private List<Product> allProducts;
	private List<TblComIncomingDate> incomingDates;
	private List<TblComIncomingDate> filteredDates;
	private List<TblComIncomingValue> incomingValues = new ArrayList<TblComIncomingValue>();

	@PostConstruct
	public void init() {
		incomingDate.setDate(loginBean.getCompanyDailyDate());
		incomingDates = service.findAllIncomingDates();
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblComIncomingValue newIncomeValue = new TblComIncomingValue();
			newIncomeValue.setProductId(product);
			incomingValues.add(newIncomeValue);
		}
	}

	public List<TblComIncomingDate> getIncomingDates() {
		return incomingDates;
	}

	public List<TblComIncomingValue> getIncomingValues() {
		return incomingValues;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public TblComIncomingDate getIncomingDate() {
		return incomingDate;
	}

	public void setIncomingDate(TblComIncomingDate incomingDate) {
		this.incomingDate = incomingDate;
	}

	public List<TblComIncomingDate> getFilteredDates() {
		return filteredDates;
	}

	public void setFilteredDates(List<TblComIncomingDate> filteredDates) {
		this.filteredDates = filteredDates;
	}

	public String insertNewIncoming() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			List<TblComIncomingValue> lastIncomingValues = new ArrayList<TblComIncomingValue>();
			double incomingTotalBefore = 0.0;
			double incomingTotalAfter = 0.0;

			// insert incoming date
			incomingDate.setByUserId(new User(loginBean.getId()));
			session.persist(incomingDate);

			// insert incoming values
			for (TblComIncomingValue incomingValue : incomingValues) {
				// calculate mount price

				// get total mount
				double min_mount_rate = (double) incomingValue.getMinMount()
						/ incomingValue.getProductId().getMaxMinCount();

				double total_mount = incomingValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = incomingValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* incomingValue.getMaxMount();

				double productPriceMin = incomingValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* incomingValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								incomingDate.getDate(),
								incomingValue.getProductId());

				double discountRate = 0;
				double total_after_minus = 0;
				double total_after = 0;

				if (discountObject == null) {
					total_after = total_before;
				} else {
					discountRate = discountObject.getDiscountValue();
					total_after_minus = (discountRate * total_before) / 100;
					total_after = total_before - total_after_minus;
				}

				// round doubles values
				total_mount = Utils.getInstance().roundDouble(total_mount, 2);
				// get showen value
				String showen_total_mount = incomingValue.getMaxMount() + "."
						+ incomingValue.getMinMount();

				incomingValue.setTotalMount(total_mount);
				incomingValue.setShowenMount(showen_total_mount);
				incomingValue.setMaxMountPrice(mountPriceMax);
				incomingValue.setMinMountPrice(mountPriceMin);
				incomingValue.setTotalBefore(total_before);
				incomingValue.setTotalAfter(total_after);
				incomingValue.setIncomingDateId(incomingDate);
				lastIncomingValues.add(incomingValue);

				session = SessionFactoryUtil.getSession();
				session.persist(incomingValue);

				incomingTotalBefore = incomingTotalBefore + total_before;
				incomingTotalAfter = incomingTotalAfter + total_after;
			}
			double discountValue = incomingTotalBefore - incomingTotalAfter;

			incomingDate.setTotalBefore(incomingTotalBefore);
			incomingDate.setDiscountValue(discountValue);
			incomingDate.setTotalAfter(incomingTotalAfter);
			incomingDate.setTblComIncomingValueList(lastIncomingValues);
			session.update(incomingDate);
			tx = session.beginTransaction();
			tx.commit();
			return "newOffer";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	/****************** update logic ********************/

	private TblComIncomingDate updatedIncomingDate;

	public TblComIncomingDate getUpdatedIncomingDate() {
		return updatedIncomingDate;
	}

	public void setUpdatedIncomingDate(TblComIncomingDate updatedIncomingDate) {
		this.updatedIncomingDate = updatedIncomingDate;
	}

	public void prerender(ComponentSystemEvent event) {
		TblComIncomingDate searchedIncomingDate = comIncomingDao
				.findByDate(loginBean.getCompanyDailyUpdatedDate());
		if (searchedIncomingDate == null) {
			utils.sendRedirect(Constants.loginPage, false);
		} else {
			setUpdatedIncomingDate(searchedIncomingDate);
		}
	}

	public String updateIncoming() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			List<TblComIncomingValue> lastIncomingValues = new ArrayList<TblComIncomingValue>();
			double incomingTotalBefore = 0.0;
			double incomingTotalAfter = 0.0;

			// insert incoming values
			for (TblComIncomingValue incomingValue : updatedIncomingDate
					.getTblComIncomingValueList()) {
				// calculate mount price

				// get total mount
				double min_mount_rate = (double) incomingValue.getMinMount()
						/ incomingValue.getProductId().getMaxMinCount();

				double total_mount = incomingValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = incomingValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* incomingValue.getMaxMount();

				double productPriceMin = incomingValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* incomingValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								updatedIncomingDate.getDate(),
								incomingValue.getProductId());

				double discountRate = 0;
				double total_after_minus = 0;
				double total_after = 0;

				if (discountObject == null) {
					total_after = total_before;
				} else {
					discountRate = discountObject.getDiscountValue();
					total_after_minus = (discountRate * total_before) / 100;
					total_after = total_before - total_after_minus;
				}

				// round doubles values
				total_mount = Utils.getInstance().roundDouble(total_mount, 2);
				// get showen value
				String showen_total_mount = incomingValue.getMaxMount() + "."
						+ incomingValue.getMinMount();

				incomingValue.setTotalMount(total_mount);
				incomingValue.setShowenMount(showen_total_mount);
				incomingValue.setMaxMountPrice(mountPriceMax);
				incomingValue.setMinMountPrice(mountPriceMin);
				incomingValue.setTotalBefore(total_before);
				incomingValue.setTotalAfter(total_after);
				incomingValue.setIncomingDateId(updatedIncomingDate);
				lastIncomingValues.add(incomingValue);

				session = SessionFactoryUtil.getSession();
				session.update(incomingValue);

				incomingTotalBefore = incomingTotalBefore + total_before;
				incomingTotalAfter = incomingTotalAfter + total_after;
			}
			double discountValue = incomingTotalBefore - incomingTotalAfter;

			updatedIncomingDate.setTotalBefore(incomingTotalBefore);
			updatedIncomingDate.setDiscountValue(discountValue);
			updatedIncomingDate.setTotalAfter(incomingTotalAfter);
			updatedIncomingDate.setByUserId(new User(loginBean.getId()));
			updatedIncomingDate.setTblComIncomingValueList(lastIncomingValues);
			session.update(updatedIncomingDate);
			tx = session.beginTransaction();
			tx.commit();
			return "updateOffer";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	/******************** search logic ***********************/

	private Date startDate;
	private Date endDate;
	private List<TblComIncomingDate> searchedIncomingDates;

	public List<TblComIncomingDate> getSearchedIncomingDates() {
		searchedIncomingDates = comIncomingDao.listByDate(startDate, endDate);
		return searchedIncomingDates;
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
		for (TblComIncomingDate d : searchedIncomingDates) {
			tot = tot + d.getTotalAfter();
		}
		totTotalAfter = tot;
		return totTotalAfter;
	}

	public Double getTotTotalBefore() {
		double tot = 0.0;
		for (TblComIncomingDate d : searchedIncomingDates) {
			tot = tot + d.getTotalBefore();
		}
		totTotalBefore = tot;
		return totTotalBefore;
	}

	public Double getTotDiscount() {
		Double discount = 0.0;
		for (TblComIncomingDate d : searchedIncomingDates) {
			discount = discount + d.getDiscountValue();
		}
		totDiscount = discount;
		return totDiscount;
	}

	/*************** validate logic *****************/
	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPLICATEDATE");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");
		String noDiscountDate = bundle.getString("NODISCOUNTDATE");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get date
		UIMessage uiInputDate = (UIMessage) components.findComponent("dateMsg");
		// Date todayDate = (Date) uiInputDate.getLocalValue();
		Date todayDate = incomingDate.getDate();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblComIncomingDate searchedDate = comIncomingDao.findByDate(todayDate);
		TblComDiscountDate discountDate = ComDiscountingDao.getInstance()
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

		if (discountDate == null) {
			FacesMessage msg = new FacesMessage(noDiscountDate);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}

}
