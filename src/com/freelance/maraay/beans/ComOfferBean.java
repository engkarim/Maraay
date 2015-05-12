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

import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.ComOfferDao;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComDiscountValue;
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.model.TblComOfferDate;
import com.freelance.maraay.model.TblComOfferValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class ComOfferBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ComOfferDao comOfferDao = ComOfferDao.getInstance();
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

	private List<TblComOfferDate> filteredDates;
	private List<TblComOfferDate> offerDates;
	private TblComOfferDate offerDate = new TblComOfferDate();
	private List<Product> allProducts;
	private List<TblComOfferValue> offerValues = new ArrayList<TblComOfferValue>();

	@PostConstruct
	public void init() {
		offerDate.setDate(loginBean.getCompanyDailyDate());
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblComOfferValue newOfferValue = new TblComOfferValue();
			newOfferValue.setProductId(product);
			offerValues.add(newOfferValue);
		}
	}

	public TblComOfferDate getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(TblComOfferDate offerDate) {
		this.offerDate = offerDate;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public List<TblComOfferValue> getOfferValues() {
		return offerValues;
	}

	public List<TblComOfferDate> getFilteredDates() {
		return filteredDates;
	}

	public void setFilteredDates(List<TblComOfferDate> filteredDates) {
		this.filteredDates = filteredDates;
	}

	public List<TblComOfferDate> getOfferDates() {
		offerDates = comOfferDao.listAllOffersDates();
		return offerDates;
	}

	public String insertNewOffer() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			List<TblComOfferValue> lastOfferValues = new ArrayList<TblComOfferValue>();
			double offerTotalBefore = 0.0;
			double offerTotalAfter = 0.0;

			// insert incoming date
			offerDate.setByUserId(new User(loginBean.getId()));
			session.persist(offerDate);

			// insert incoming values
			for (TblComOfferValue OfferValue : offerValues) {
				// calculate mount price

				// get total mount
				double min_mount_rate = (double) OfferValue.getMinMount()
						/ OfferValue.getProductId().getMaxMinCount();

				double total_mount = OfferValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = OfferValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* OfferValue.getMaxMount();

				double productPriceMin = OfferValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* OfferValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								offerDate.getDate(), OfferValue.getProductId());

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
				String shown_mount = OfferValue.getMaxMount() + "."
						+ OfferValue.getMinMount();

				OfferValue.setTotalMount(total_mount);
				OfferValue.setShowenMount(shown_mount);
				OfferValue.setMaxMountPrice(mountPriceMax);
				OfferValue.setMinMountPrice(mountPriceMin);
				OfferValue.setTotalBefore(total_before);
				OfferValue.setTotalAfter(total_after);
				OfferValue.setOfferDateId(offerDate);
				lastOfferValues.add(OfferValue);

				session = SessionFactoryUtil.getSession();
				session.persist(OfferValue);

				offerTotalBefore = offerTotalBefore + total_before;
				offerTotalAfter = offerTotalAfter + total_after;
			}

			double discountValue = offerTotalBefore - offerTotalAfter;

			offerDate.setTotalBefore(offerTotalBefore);
			offerDate.setTotalAfter(offerTotalAfter);
			offerDate.setDiscountValue(discountValue);
			offerDate.setTblComOfferValueList(lastOfferValues);
			session.update(offerDate);
			tx = session.beginTransaction();
			tx.commit();
			return "newBlending";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	/************** update logic ****************/

	private TblComOfferDate updatedOfferDate;

	public TblComOfferDate getUpdatedOfferDate() {
		return updatedOfferDate;
	}

	public void setUpdatedOfferDate(TblComOfferDate updatedOfferDate) {
		this.updatedOfferDate = updatedOfferDate;
	}

	public void prerender(ComponentSystemEvent event) {
		TblComOfferDate searchedOfferDate = comOfferDao.findByDate(loginBean
				.getCompanyDailyUpdatedDate());
		if (searchedOfferDate == null) {
			utils.sendRedirect(Constants.loginPage, false);
		} else {
			setUpdatedOfferDate(searchedOfferDate);
		}
	}

	public String updateOffer() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			List<TblComOfferValue> lastOfferValues = new ArrayList<TblComOfferValue>();
			double offerTotalBefore = 0.0;
			double offerTotalAfter = 0.0;

			// insert incoming values
			for (TblComOfferValue OfferValue : updatedOfferDate
					.getTblComOfferValueList()) {
				// calculate mount price

				// get total mount
				double min_mount_rate = (double) OfferValue.getMinMount()
						/ OfferValue.getProductId().getMaxMinCount();

				double total_mount = OfferValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = OfferValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* OfferValue.getMaxMount();

				double productPriceMin = OfferValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* OfferValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								updatedOfferDate.getDate(),
								OfferValue.getProductId());

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
				String shown_mount = OfferValue.getMaxMount() + "."
						+ OfferValue.getMinMount();

				OfferValue.setTotalMount(total_mount);
				OfferValue.setShowenMount(shown_mount);
				OfferValue.setMaxMountPrice(mountPriceMax);
				OfferValue.setMinMountPrice(mountPriceMin);
				OfferValue.setTotalBefore(total_before);
				OfferValue.setTotalAfter(total_after);
				OfferValue.setOfferDateId(updatedOfferDate);
				lastOfferValues.add(OfferValue);

				session = SessionFactoryUtil.getSession();
				session.update(OfferValue);

				offerTotalBefore = offerTotalBefore + total_before;
				offerTotalAfter = offerTotalAfter + total_after;
			}

			double discountValue = offerTotalBefore - offerTotalAfter;

			updatedOfferDate.setTotalBefore(offerTotalBefore);
			updatedOfferDate.setTotalAfter(offerTotalAfter);
			updatedOfferDate.setDiscountValue(discountValue);
			updatedOfferDate.setTblComOfferValueList(lastOfferValues);
			updatedOfferDate.setByUserId(new User(loginBean.getId()));
			session.update(updatedOfferDate);
			tx = session.beginTransaction();
			tx.commit();
			return "updateBlending";
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
	private List<TblComOfferDate> searchedDates;

	public List<TblComOfferDate> getSearchedDates() {
		searchedDates = comOfferDao.listByDate(startDate, endDate);
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
		for (TblComOfferDate d : searchedDates) {
			tot = tot + d.getTotalAfter();
		}
		totTotalAfter = tot;
		return totTotalAfter;
	}

	public Double getTotTotalBefore() {
		double tot = 0.0;
		for (TblComOfferDate d : searchedDates) {
			tot = tot + d.getTotalBefore();
		}
		totTotalBefore = tot;
		return totTotalBefore;
	}

	public Double getTotDiscount() {
		Double discount = 0.0;
		for (TblComOfferDate d : searchedDates) {
			discount = discount + d.getDiscountValue();
		}
		totDiscount = discount;
		return totDiscount;
	}

	/******************* validate logic ****************/

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

		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblComOfferDate searchedDate = comOfferDao.findByDate(todayDate);
		TblComIncomingDate incomingDate = ComIncomingDao.getInstance()
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

		if (incomingDate == null) {
			FacesMessage msg = new FacesMessage(noDiscountDate);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}

}
