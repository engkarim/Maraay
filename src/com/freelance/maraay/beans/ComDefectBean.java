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
import com.freelance.maraay.dao.ComDefectDao;
import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.ComOfferDao;
import com.freelance.maraay.dao.ComSalesDao;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComBlendingDate;
import com.freelance.maraay.model.TblComBlendingValue;
import com.freelance.maraay.model.TblComDefectsDate;
import com.freelance.maraay.model.TblComDefectsValue;
import com.freelance.maraay.model.TblComDiscountValue;
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.model.TblComIncomingValue;
import com.freelance.maraay.model.TblComOfferDate;
import com.freelance.maraay.model.TblComOfferValue;
import com.freelance.maraay.model.TblComSalesDate;
import com.freelance.maraay.model.TblComSalesValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class ComDefectBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ComDefectDao comDefectDao = ComDefectDao.getInstance();
	ComIncomingDao comIncomingDao = ComIncomingDao.getInstance();
	ComBlendingDao comBlendingDao = ComBlendingDao.getInstance();
	ComOfferDao comOfferDao = ComOfferDao.getInstance();
	ComSalesDao comSalesDao = ComSalesDao.getInstance();
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

	private TblComDefectsDate defectDate = new TblComDefectsDate();
	private List<Product> allProducts;
	private List<TblComDefectsDate> defectDates;
	private List<TblComDefectsValue> defectValues = new ArrayList<TblComDefectsValue>();
	private List<TblComDefectsDate> filteredDates;

	@PostConstruct
	public void init() {
		defectDate.setDate(loginBean.getCompanyDailyDate());
		defectDates = service.findAllDefectDates();
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblComDefectsValue newDefectValue = new TblComDefectsValue();
			newDefectValue.setProductId(product);
			defectValues.add(newDefectValue);
		}
	}

	public List<TblComDefectsDate> getDefectDates() {
		return defectDates;
	}

	public List<TblComDefectsValue> getDefectValues() {
		return defectValues;
	}

	public TblComDefectsDate getDefectDate() {
		return defectDate;
	}

	public void setDefectDate(TblComDefectsDate defectDate) {
		this.defectDate = defectDate;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public List<TblComDefectsDate> getFilteredDates() {
		return filteredDates;
	}

	public void setFilteredDates(List<TblComDefectsDate> filteredDates) {
		this.filteredDates = filteredDates;
	}

	public String insertNewDefect() {

		Session session = null;
		Transaction tx = null;
		try {

			List<TblComDefectsValue> lastDefectValues = new ArrayList<TblComDefectsValue>();
			// get incoming date
			TblComIncomingDate incomingDate = comIncomingDao
					.findByDate(defectDate.getDate());

			// get blending date
			TblComBlendingDate blendingDate = comBlendingDao
					.findByDate(defectDate.getDate());

			// get offer date
			TblComOfferDate offerDate = comOfferDao.findByDate(defectDate
					.getDate());

			double defectTotalAfter = 0.0;
			double defectTotalBefore = 0.0;
			double salesTotalAfter = 0.0;
			double salesTotalBefore = 0.0;

			// insert defect date
			defectDate.setByUserId(new User(loginBean.getId()));
			session = SessionFactoryUtil.getSession();
			session.persist(defectDate);

			// insert sales date
			TblComSalesDate salesDate = new TblComSalesDate();
			salesDate.setDate(defectDate.getDate());
			salesDate.setByUserId(new User(loginBean.getId()));
			session = SessionFactoryUtil.getSession();
			session.persist(salesDate);

			// insert defect values
			for (TblComDefectsValue defectValue : defectValues) {
				// calculate mount price

				// get total mount
				double min_mount_rate = (double) defectValue.getMinMount()
						/ defectValue.getProductId().getMaxMinCount();

				double total_mount = defectValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = defectValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* defectValue.getMaxMount();

				double productPriceMin = defectValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* defectValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								defectDate.getDate(),
								defectValue.getProductId());

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
				String showen_total_mount = defectValue.getMaxMount() + "."
						+ defectValue.getMinMount();
				defectValue.setTotalMount(total_mount);
				defectValue.setShowenMount(showen_total_mount);
				defectValue.setMaxMountPrice(mountPriceMax);
				defectValue.setMinMountPrice(mountPriceMin);
				defectValue.setTotalBefore(total_before);
				defectValue.setTotalAfter(total_after);
				defectValue.setDefectsDateId(defectDate);
				lastDefectValues.add(defectValue);

				session = SessionFactoryUtil.getSession();
				session.persist(defectValue);

				defectTotalBefore = defectTotalBefore + total_before;
				defectTotalAfter = defectTotalAfter + total_after;

				/************** insert company sales values ***************/
				// create an instance of sales model
				TblComSalesValue salesValue = new TblComSalesValue();

				// initialze salse properties
				double salesTotalMount = 0.0;
				int salesMaxMount = 0;
				int salesMinMount = 0;
				double salesMountPrice_after = 0.0;
				double salesMountPrice_before = 0.0;
				String salesShowenMount = null;

				// get incoming value
				TblComIncomingValue incomingValue = comIncomingDao
						.findByDateAndProduct(incomingDate.getIncomingDateId(),
								defectValue.getProductId());

				// get blending value
				TblComBlendingValue blendingValue = comBlendingDao
						.findByDateAndProduct(blendingDate.getBlendingDateId(),
								defectValue.getProductId());

				if (offerDate == null) {
					salesTotalMount = incomingValue.getTotalMount()
							- (blendingValue.getTotalMount() + defectValue
									.getTotalMount());

					salesMaxMount = incomingValue.getMaxMount()
							- (blendingValue.getMaxMount() + defectValue
									.getMaxMount());

					salesMinMount = incomingValue.getMinMount()
							- (blendingValue.getMinMount() + defectValue
									.getMinMount());

					salesShowenMount = salesMaxMount + "." + salesMinMount;

					salesMountPrice_before = incomingValue.getTotalBefore()
							- (blendingValue.getTotalBefore() + defectValue
									.getTotalBefore());

					salesMountPrice_after = incomingValue.getTotalAfter()
							- (blendingValue.getTotalAfter() + defectValue
									.getTotalAfter());

				} else {
					// get offer value
					TblComOfferValue offerValue = comOfferDao
							.findByDateAndProduct(offerDate.getOfferDateId(),
									defectValue.getProductId());

					salesTotalMount = incomingValue.getTotalMount()
							- (blendingValue.getTotalMount()
									+ defectValue.getTotalMount() + offerValue
										.getTotalMount());

					salesMaxMount = incomingValue.getMaxMount()
							- (blendingValue.getMaxMount()
									+ defectValue.getMaxMount() + offerValue
										.getMaxMount());

					salesMinMount = incomingValue.getMinMount()
							- (blendingValue.getMinMount()
									+ defectValue.getMinMount() + offerValue
										.getMinMount());

					salesShowenMount = salesMaxMount + "." + salesMinMount;

					salesMountPrice_before = incomingValue.getTotalBefore()
							- (blendingValue.getTotalBefore()
									+ defectValue.getTotalBefore() + offerValue
										.getTotalBefore());

					salesMountPrice_after = incomingValue.getTotalAfter()
							- (blendingValue.getTotalAfter()
									+ defectValue.getTotalAfter() + offerValue
										.getTotalAfter());

				}

				salesTotalMount = Utils.getInstance().roundDouble(
						salesTotalMount, 2);
				salesValue.setTotalMount(salesTotalMount);
				salesValue.setShowenMount(salesShowenMount);
				salesValue.setTotalBefore(salesMountPrice_before);
				salesValue.setTotalAfter(salesMountPrice_after);
				salesValue.setProductId(blendingValue.getProductId());
				salesValue.setSalesDateId(salesDate);

				session = SessionFactoryUtil.getSession();
				session.persist(salesValue);

			}
			// get discount value
			double discount_value = defectTotalBefore - defectTotalAfter;
			// update defect date
			defectDate.setTotalBefore(defectTotalBefore);
			defectDate.setTotalAfter(defectTotalAfter);
			defectDate.setDiscountValue(discount_value);
			defectDate.setTblComDefectsValueList(lastDefectValues);
			session = SessionFactoryUtil.getSession();
			session.update(defectDate);

			// update salse date
			if (offerDate == null) {
				salesTotalBefore = incomingDate.getTotalBefore()
						- (blendingDate.getTotalBefore() + defectDate
								.getTotalBefore());
				salesTotalAfter = incomingDate.getTotalAfter()
						- (blendingDate.getTotalAfter() + defectDate
								.getTotalAfter());
			} else {
				salesTotalBefore = incomingDate.getTotalBefore()
						- (blendingDate.getTotalBefore()
								+ defectDate.getTotalBefore() + offerDate
									.getTotalBefore());
				salesTotalAfter = incomingDate.getTotalAfter()
						- (blendingDate.getTotalAfter()
								+ defectDate.getTotalAfter() + offerDate
									.getTotalAfter());
			}

			double sales_discount_value = salesTotalBefore - salesTotalAfter;

			salesDate.setTotalBefore(salesTotalBefore);
			salesDate.setTotalAfter(salesTotalAfter);
			salesDate.setDiscountValue(sales_discount_value);
			session = SessionFactoryUtil.getSession();
			session.update(salesDate);
			tx = session.beginTransaction();
			tx.commit();
			return "allSales";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	/*********************** update logic ****************/
	private TblComDefectsDate updatedDefectDate;

	public TblComDefectsDate getUpdatedDefectDate() {
		return updatedDefectDate;
	}

	public void setUpdatedDefectDate(TblComDefectsDate updatedDefectDate) {
		this.updatedDefectDate = updatedDefectDate;
	}

	public void prerender(ComponentSystemEvent event) {
		TblComDefectsDate searchedDefectDate = comDefectDao
				.findByDate(loginBean.getCompanyDailyUpdatedDate());
		if (searchedDefectDate == null) {
			utils.sendRedirect(Constants.loginPage, false);
		} else {
			setUpdatedDefectDate(searchedDefectDate);
		}
	}

	public String updateDefect() {
		Session session = null;
		Transaction tx = null;
		try {

			List<TblComDefectsValue> lastDefectValues = new ArrayList<TblComDefectsValue>();
			// get incoming date
			TblComIncomingDate incomingDate = comIncomingDao
					.findByDate(updatedDefectDate.getDate());

			// get blending date
			TblComBlendingDate blendingDate = comBlendingDao
					.findByDate(updatedDefectDate.getDate());

			// get offer date
			TblComOfferDate offerDate = comOfferDao
					.findByDate(updatedDefectDate.getDate());

			double defectTotalAfter = 0.0;
			double defectTotalBefore = 0.0;
			double salesTotalAfter = 0.0;
			double salesTotalBefore = 0.0;

			// retreive sales date
			TblComSalesDate salesDate = comSalesDao
					.findByDate(updatedDefectDate.getDate());

			System.out.println("salesdate @@@@@@@@@@@@@@@@@@@@@@@@");

			// insert defect values
			for (TblComDefectsValue defectValue : updatedDefectDate
					.getTblComDefectsValueList()) {
				// calculate mount price

				// get total mount
				double min_mount_rate = (double) defectValue.getMinMount()
						/ defectValue.getProductId().getMaxMinCount();

				double total_mount = defectValue.getMaxMount().doubleValue()
						+ min_mount_rate;

				double productPriceMax = defectValue.getProductId()
						.getMaxUnitPrice();

				double mountPriceMax = productPriceMax
						* defectValue.getMaxMount();

				double productPriceMin = defectValue.getProductId()
						.getMinUnitPrice();

				double mountPriceMin = productPriceMin
						* defectValue.getMinMount();
				// get total before discount
				double total_before = mountPriceMax + mountPriceMin;

				// get total after discount
				TblComDiscountValue discountObject = ComDiscountingDao
						.getInstance().findByDateAndProduct(
								updatedDefectDate.getDate(),
								defectValue.getProductId());

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
				String showen_total_mount = defectValue.getMaxMount() + "."
						+ defectValue.getMinMount();
				defectValue.setTotalMount(total_mount);
				defectValue.setShowenMount(showen_total_mount);
				defectValue.setMaxMountPrice(mountPriceMax);
				defectValue.setMinMountPrice(mountPriceMin);
				defectValue.setTotalBefore(total_before);
				defectValue.setTotalAfter(total_after);
				defectValue.setDefectsDateId(updatedDefectDate);
				lastDefectValues.add(defectValue);
				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				session = SessionFactoryUtil.getSession();
				session.update(defectValue);

				defectTotalBefore = defectTotalBefore + total_before;
				defectTotalAfter = defectTotalAfter + total_after;

				/************** update company sales values ***************/

				// initialze salse properties
				double salesTotalMount = 0.0;
				int salesMaxMount = 0;
				int salesMinMount = 0;
				double salesMountPrice_after = 0.0;
				double salesMountPrice_before = 0.0;
				String salesShowenMount = null;
				System.out.println("1111111111111111111111111111111111");
				for (TblComSalesValue updatedSalesvalue : salesDate
						.getTblComSalesValueList()) {
//					if (updatedSalesvalue.getProductId() == defectValue
//							.getProductId()) {
						System.out
								.println("on second for looooooooooooooooooooooooooop%%%%%%%%%%%%%%%%%%%%%%%%%%%");
						// get incoming value
						TblComIncomingValue incomingValue = comIncomingDao
								.findByDateAndProduct(
										incomingDate.getIncomingDateId(),
										defectValue.getProductId());

						// get blending value
						TblComBlendingValue blendingValue = comBlendingDao
								.findByDateAndProduct(
										blendingDate.getBlendingDateId(),
										defectValue.getProductId());

						if (offerDate == null) {
							salesTotalMount = incomingValue.getTotalMount()
									- (blendingValue.getTotalMount() + defectValue
											.getTotalMount());

							salesMaxMount = incomingValue.getMaxMount()
									- (blendingValue.getMaxMount() + defectValue
											.getMaxMount());

							salesMinMount = incomingValue.getMinMount()
									- (blendingValue.getMinMount() + defectValue
											.getMinMount());

							salesShowenMount = salesMaxMount + "."
									+ salesMinMount;

							salesMountPrice_before = incomingValue
									.getTotalBefore()
									- (blendingValue.getTotalBefore() + defectValue
											.getTotalBefore());

							salesMountPrice_after = incomingValue
									.getTotalAfter()
									- (blendingValue.getTotalAfter() + defectValue
											.getTotalAfter());

						} else {
							// get offer value
							TblComOfferValue offerValue = comOfferDao
									.findByDateAndProduct(
											offerDate.getOfferDateId(),
											defectValue.getProductId());

							salesTotalMount = incomingValue.getTotalMount()
									- (blendingValue.getTotalMount()
											+ defectValue.getTotalMount() + offerValue
												.getTotalMount());

							salesMaxMount = incomingValue.getMaxMount()
									- (blendingValue.getMaxMount()
											+ defectValue.getMaxMount() + offerValue
												.getMaxMount());

							salesMinMount = incomingValue.getMinMount()
									- (blendingValue.getMinMount()
											+ defectValue.getMinMount() + offerValue
												.getMinMount());

							salesShowenMount = salesMaxMount + "."
									+ salesMinMount;

							salesMountPrice_before = incomingValue
									.getTotalBefore()
									- (blendingValue.getTotalBefore()
											+ defectValue.getTotalBefore() + offerValue
												.getTotalBefore());

							salesMountPrice_after = incomingValue
									.getTotalAfter()
									- (blendingValue.getTotalAfter()
											+ defectValue.getTotalAfter() + offerValue
												.getTotalAfter());

						}

						salesTotalMount = Utils.getInstance().roundDouble(
								salesTotalMount, 2);
						updatedSalesvalue.setTotalMount(salesTotalMount);
						updatedSalesvalue.setShowenMount(salesShowenMount);
						updatedSalesvalue
								.setTotalBefore(salesMountPrice_before);
						updatedSalesvalue.setTotalAfter(salesMountPrice_after);
						updatedSalesvalue.setProductId(updatedSalesvalue
								.getProductId());
						updatedSalesvalue.setSalesDateId(salesDate);

						session = SessionFactoryUtil.getSession();
						session.update(updatedSalesvalue);
//					} else {
//						continue;
//					}

				}

			}
			System.out
					.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			// get discount value
			double discount_value = defectTotalBefore - defectTotalAfter;
			// update defect date
			updatedDefectDate.setTotalBefore(defectTotalBefore);
			updatedDefectDate.setTotalAfter(defectTotalAfter);
			updatedDefectDate.setDiscountValue(discount_value);
			updatedDefectDate.setTblComDefectsValueList(lastDefectValues);
			updatedDefectDate.setByUserId(new User(loginBean.getId()));
			session = SessionFactoryUtil.getSession();
			session.update(updatedDefectDate);

			// update salse date
			if (offerDate == null) {
				salesTotalBefore = incomingDate.getTotalBefore()
						- (blendingDate.getTotalBefore() + updatedDefectDate
								.getTotalBefore());
				salesTotalAfter = incomingDate.getTotalAfter()
						- (blendingDate.getTotalAfter() + updatedDefectDate
								.getTotalAfter());
			} else {
				salesTotalBefore = incomingDate.getTotalBefore()
						- (blendingDate.getTotalBefore()
								+ updatedDefectDate.getTotalBefore() + offerDate
									.getTotalBefore());
				salesTotalAfter = incomingDate.getTotalAfter()
						- (blendingDate.getTotalAfter()
								+ updatedDefectDate.getTotalAfter() + offerDate
									.getTotalAfter());
			}

			double sales_discount_value = salesTotalBefore - salesTotalAfter;

			salesDate.setDate(updatedDefectDate.getDate());
			salesDate.setByUserId(new User(loginBean.getId()));
			salesDate.setTotalBefore(salesTotalBefore);
			salesDate.setTotalAfter(salesTotalAfter);
			salesDate.setDiscountValue(sales_discount_value);
			session = SessionFactoryUtil.getSession();
			session.update(salesDate);
			System.out.println("?????????????????????????????????????????????");
			tx = session.beginTransaction();
			tx.commit();
			return "allSales";
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
	private List<TblComDefectsDate> searchedDates;

	public List<TblComDefectsDate> getSearchedDates() {
		searchedDates = comDefectDao.listByDate(startDate, endDate);
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
		for (TblComDefectsDate d : searchedDates) {
			tot = tot + d.getTotalAfter();
		}
		totTotalAfter = tot;
		return totTotalAfter;
	}

	public Double getTotTotalBefore() {
		double tot = 0.0;
		for (TblComDefectsDate d : searchedDates) {
			tot = tot + d.getTotalBefore();
		}
		totTotalBefore = tot;
		return totTotalBefore;
	}

	public Double getTotDiscount() {
		Double discount = 0.0;
		for (TblComDefectsDate d : searchedDates) {
			discount = discount + d.getDiscountValue();
		}
		totDiscount = discount;
		return totDiscount;
	}

	/**************** validation logic **********************/
	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPLICATEDATE");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");
		String noBlendingDateMsg = bundle.getString("NOBLENDINGDATE");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblComDefectsDate searchedDate = comDefectDao.findByDate(todayDate);
		TblComBlendingDate blendingSearshDate = ComBlendingDao.getInstance()
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

		if (blendingSearshDate == null) {
			FacesMessage msg = new FacesMessage(noBlendingDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}

}
