package com.freelance.maraay.beans;

import java.io.Serializable;
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

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.ComBlendingDao;
import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComBlendingDate;
import com.freelance.maraay.model.TblComBlendingValue;
import com.freelance.maraay.model.TblComDiscountValue;
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.model.User;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class ComBlendingBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ComBlendingDao comBlendingDao = ComBlendingDao.getInstance();
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

	private TblComBlendingDate blendingDate = new TblComBlendingDate();
	private List<Product> allProducts;
	private List<TblComBlendingDate> blendingDates;
	private List<TblComBlendingDate> filteredDates;
	private List<TblComBlendingValue> blendingValues = new ArrayList<TblComBlendingValue>();

	@PostConstruct
	public void init() {
		blendingDate.setDate(loginBean.getCompanyDailyDate());
		blendingDates = service.findAllBlendingDates();
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblComBlendingValue newBlendingValue = new TblComBlendingValue();
			newBlendingValue.setProductId(product);
			blendingValues.add(newBlendingValue);
		}
	}

	public List<TblComBlendingDate> getBlendingDates() {
		return blendingDates;
	}

	public List<TblComBlendingValue> getBlendingValues() {
		return blendingValues;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public TblComBlendingDate getBlendingDate() {
		return blendingDate;
	}

	public void setBlendingDate(TblComBlendingDate blendingDate) {
		this.blendingDate = blendingDate;
	}

	public List<TblComBlendingDate> getFilteredDates() {
		return filteredDates;
	}

	public void setFilteredDates(List<TblComBlendingDate> filteredDates) {
		this.filteredDates = filteredDates;
	}

	public String insertNewBlending() {
		Session session = null;
		Transaction tx = null;
		try {
			List<TblComBlendingValue> lastBlendingValues = new ArrayList<TblComBlendingValue>();
			double blendingTotalBefore = 0.0;
			double blendingTotalAfter = 0.0;
			double blending_incoming_rate = 0.0;
			// insert incoming date
			blendingDate.setByUserId(new User(loginBean.getId()));

			session = SessionFactoryUtil.getSession();
			session.persist(blendingDate);

			// insert incoming values
			for (TblComBlendingValue blendingValue : blendingValues) {

				// calculate mount price

				// get total mount
				double min_mount_rate = (double) blendingValue.getMinMount()
						/ blendingValue.getProductId().getMaxMinCount();

				double total_mount = blendingValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = blendingValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* blendingValue.getMaxMount();

				double productPriceMin = blendingValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* blendingValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								blendingDate.getDate(),
								blendingValue.getProductId());

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

				// get shown mount
				String showen_total_mount = blendingValue.getMaxMount() + "."
						+ blendingValue.getMinMount();
				blendingValue.setTotalMount(total_mount);
				blendingValue.setShowenMount(showen_total_mount);
				blendingValue.setMaxMountPrice(mountPriceMax);
				blendingValue.setMinMountPrice(mountPriceMin);
				blendingValue.setTotalBefore(total_before);
				blendingValue.setTotalAfter(total_after);
				blendingValue.setIncomingBlendingRate(blending_incoming_rate);
				blendingValue.setBlendingDateId(blendingDate);
				lastBlendingValues.add(blendingValue);

				session = SessionFactoryUtil.getSession();
				session.persist(blendingValue);

				blendingTotalBefore = blendingTotalBefore + total_before;
				blendingTotalAfter = blendingTotalAfter + total_after;
			}
			double incoming_total_after = comIncomingDao.findByDate(
					blendingDate.getDate()).getTotalAfter();
			double total_blending_incoming_rate = (blendingTotalAfter / incoming_total_after);

			double discount_value = blendingTotalBefore - blendingTotalAfter;

			blendingDate
					.setIncomingBlendingRateTotal(total_blending_incoming_rate);
			blendingDate.setIncomingTotalAfter(incoming_total_after);
			blendingDate.setTotalBefore(blendingTotalBefore);
			blendingDate.setDiscountValue(discount_value);
			blendingDate.setTotalAfter(blendingTotalAfter);
			blendingDate.setTblComBlendingValueList(lastBlendingValues);
			session = SessionFactoryUtil.getSession();
			session.update(blendingDate);
			tx = session.beginTransaction();
			tx.commit();
			return "newDefect";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;

		}

	}

	/**************** update logic ************************/
	private TblComBlendingDate updatedBlendingDate;

	public TblComBlendingDate getUpdatedBlendingDate() {
		return updatedBlendingDate;
	}

	public void setUpdatedBlendingDate(TblComBlendingDate updatedBlendingDate) {
		this.updatedBlendingDate = updatedBlendingDate;
	}

	public void prerender(ComponentSystemEvent event) {
		TblComBlendingDate searchedBlendingDate = comBlendingDao
				.findByDate(loginBean.getCompanyDailyUpdatedDate());
		if (searchedBlendingDate == null) {
			utils.sendRedirect(Constants.loginPage, false);
		} else {
			setUpdatedBlendingDate(searchedBlendingDate);
		}
	}

	public String updateBlending() {
		Session session = null;
		Transaction tx = null;
		try {
			List<TblComBlendingValue> lastBlendingValues = new ArrayList<TblComBlendingValue>();
			double blendingTotalBefore = 0.0;
			double blendingTotalAfter = 0.0;
			double blending_incoming_rate = 0.0;

			// insert Blending values
			for (TblComBlendingValue blendingValue : updatedBlendingDate
					.getTblComBlendingValueList()) {

				// calculate mount price

				// get total mount
				double min_mount_rate = (double) blendingValue.getMinMount()
						/ blendingValue.getProductId().getMaxMinCount();

				double total_mount = blendingValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = blendingValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* blendingValue.getMaxMount();

				double productPriceMin = blendingValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* blendingValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								updatedBlendingDate.getDate(),
								blendingValue.getProductId());

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

				// get shown mount
				String showen_total_mount = blendingValue.getMaxMount() + "."
						+ blendingValue.getMinMount();
				blendingValue.setTotalMount(total_mount);
				blendingValue.setShowenMount(showen_total_mount);
				blendingValue.setMaxMountPrice(mountPriceMax);
				blendingValue.setMinMountPrice(mountPriceMin);
				blendingValue.setTotalBefore(total_before);
				blendingValue.setTotalAfter(total_after);
				blendingValue.setIncomingBlendingRate(blending_incoming_rate);
				blendingValue.setBlendingDateId(updatedBlendingDate);
				lastBlendingValues.add(blendingValue);

				session = SessionFactoryUtil.getSession();
				session.update(blendingValue);

				blendingTotalBefore = blendingTotalBefore + total_before;
				blendingTotalAfter = blendingTotalAfter + total_after;
			}
			double incoming_total_after = comIncomingDao.findByDate(
					updatedBlendingDate.getDate()).getTotalAfter();
			double total_blending_incoming_rate = (blendingTotalAfter / incoming_total_after);

			double discount_value = blendingTotalBefore - blendingTotalAfter;

			updatedBlendingDate
					.setIncomingBlendingRateTotal(total_blending_incoming_rate);
			updatedBlendingDate.setIncomingTotalAfter(incoming_total_after);
			updatedBlendingDate.setTotalBefore(blendingTotalBefore);
			updatedBlendingDate.setDiscountValue(discount_value);
			updatedBlendingDate.setTotalAfter(blendingTotalAfter);
			updatedBlendingDate.setTblComBlendingValueList(lastBlendingValues);
			updatedBlendingDate.setByUserId(new User(loginBean.getId()));
			session = SessionFactoryUtil.getSession();
			session.update(updatedBlendingDate);
			tx = session.beginTransaction();
			tx.commit();
			return "updateDefect";
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
	private List<TblComBlendingDate> searchedDates;

	public List<TblComBlendingDate> getSearchedDates() {
		searchedDates = comBlendingDao.listByDate(startDate, endDate);
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
		for (TblComBlendingDate d : searchedDates) {
			tot = tot + d.getTotalAfter();
		}
		totTotalAfter = tot;
		return totTotalAfter;
	}

	public Double getTotTotalBefore() {
		double tot = 0.0;
		for (TblComBlendingDate d : searchedDates) {
			tot = tot + d.getTotalBefore();
		}
		totTotalBefore = tot;
		return totTotalBefore;
	}

	public Double getTotDiscount() {
		Double discount = 0.0;
		for (TblComBlendingDate d : searchedDates) {
			discount = discount + d.getDiscountValue();
		}
		totDiscount = discount;
		return totDiscount;
	}

	/********************** validate logic ********************/

	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPLICATEDATE");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");
		String noIncomingDateMsg = bundle.getString("NOIMCOMINGDATE");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblComBlendingDate searchedDate = comBlendingDao.findByDate(todayDate);
		TblComIncomingDate searchedIncomingDate = comIncomingDao
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

		if (searchedIncomingDate == null) {
			FacesMessage msg = new FacesMessage(noIncomingDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}

	}

}
