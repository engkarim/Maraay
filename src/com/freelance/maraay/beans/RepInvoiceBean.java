package com.freelance.maraay.beans;

import java.io.Serializable;
import java.math.BigInteger;
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

import com.freelance.maraay.dao.ComSalesDao;
import com.freelance.maraay.dao.CustomerDao;
import com.freelance.maraay.dao.DirectionDao;
import com.freelance.maraay.dao.LuDirRepDrivDao;
import com.freelance.maraay.dao.RepInvoiceDao;
import com.freelance.maraay.model.Customer;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComCalculationEquation;
import com.freelance.maraay.model.TblComSalesDate;
import com.freelance.maraay.model.TblLuDirRepDriv;
import com.freelance.maraay.model.TblRepInvoice;
import com.freelance.maraay.model.TblRepInvoiceValues;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class RepInvoiceBean implements Serializable {
	/**
	 * 
	 */
	Utils utils = Utils.getInstance();
	private static final long serialVersionUID = 1L;
	CustomerDao customerDao = CustomerDao.getInstance();
	private TblRepInvoice newInvoice = new TblRepInvoice();
	private List<Product> allProducts;
	private Integer customerId;
	// private Date invoiceDate;
	private Customer customer = new Customer();
	// private TblLuDirRepDriv dirRepDriv = new TblLuDirRepDriv();
	private List<TblRepInvoiceValues> invoiceValues = new ArrayList<TblRepInvoiceValues>();
	private List<TblRepInvoice> invoices;
	private List<TblRepInvoice> filteredInvoice;

	// public Date getInvoiceDate() {
	// System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,");
	// return invoiceDate;
	// }
	//
	// public void setInvoiceDate(Date invoiceDate) {
	// System.out.println("sdsdsdsdsdsdsds");
	// this.invoiceDate = invoiceDate;
	// }

	public List<TblRepInvoice> getInvoices() {
		invoices = RepInvoiceDao.getInstance().listAll();
		return invoices;
	}

	public List<TblRepInvoice> getFilteredInvoice() {
		return filteredInvoice;
	}

	public void setFilteredInvoice(List<TblRepInvoice> filteredInvoice) {
		this.filteredInvoice = filteredInvoice;
	}

	public Customer getCustomer() {
		if (customerId != null) {
			customer = customerDao.findById(customerId);
		}
		return customer;
	}

	//
	// public TblLuDirRepDriv getDirRepDriv() {
	//
	// System.out.println( customer.getDirection() + " ............... " +
	// invoiceDate );
	// if(customer != null && customer.getDirection() != null && invoiceDate !=
	// null){
	// System.out.println("222222222222222222222222222222");
	// dirRepDriv = LuDirRepDrivDao.getInstance().findByDateِAndDir(invoiceDate,
	// customer.getDirection().getId());
	// System.out.println(dirRepDriv.getRepId().getName());
	// }
	//
	// return dirRepDriv;
	// }

	public List<TblRepInvoiceValues> getInvoiceValues() {
		return invoiceValues;
	}

	public TblRepInvoice getNewInvoice() {
		return newInvoice;
	}

	public void setNewInvoice(TblRepInvoice newInvoice) {
		this.newInvoice = newInvoice;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

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

	@PostConstruct
	public void init() {
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblRepInvoiceValues invoiceValue = new TblRepInvoiceValues();
			invoiceValue.setProductId(product);
			invoiceValues.add(invoiceValue);
		}
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public String insertNewInvoice() {
		Session session = null;
		Transaction tx = null;
		try {
			List<TblRepInvoiceValues> lastInvoiceValues = new ArrayList<TblRepInvoiceValues>();
			double invoiceTotalBefore = 0.0;
			// insert incoming date
			newInvoice.setByUserId(new User(loginBean.getId()));
			// newInvoice.setInvoiceDate(invoiceDate);

			session = SessionFactoryUtil.getSession();
			session.persist(newInvoice);

			// insert incoming values
			for (TblRepInvoiceValues itemValue : invoiceValues) {

				// get total mount

				double productPriceMax = itemValue.getProductId()
						.getRepMaxUnPrice();

				double mountPriceMax = productPriceMax
						* itemValue.getMaxMount();

				double productPriceMin = itemValue.getProductId()
						.getRepMinUnPrice();

				double mountPriceMin = productPriceMin
						* itemValue.getMinMount();

				// get total before discount
				double total_price = mountPriceMax + mountPriceMin;

				// get shown mount
				String showen_total_mount = itemValue.getMaxMount() + "."
						+ itemValue.getMinMount();

				itemValue.setShowenMount(showen_total_mount);
				itemValue.setMaxPrice(mountPriceMax);
				itemValue.setMinPrice(mountPriceMin);
				itemValue.setTotalPrice(total_price);
				itemValue.setInvoiceId(newInvoice);
				lastInvoiceValues.add(itemValue);

				session = SessionFactoryUtil.getSession();
				session.persist(itemValue);

				invoiceTotalBefore = invoiceTotalBefore + total_price;
			}

			double normal_discount_value = invoiceTotalBefore
					* (newInvoice.getDiscountRate() / 100);
			double additional_discount_value = invoiceTotalBefore
					* (newInvoice.getAdditionalDiscount() / 100);
			double total_discount = normal_discount_value
					+ additional_discount_value;
			double invoiceTotalAfter = invoiceTotalBefore - total_discount;

			newInvoice.setCustomerId(customer);
			newInvoice.setTotalPriceBefore(invoiceTotalBefore);
			newInvoice.setTotalPriceAfter(invoiceTotalAfter);
			newInvoice.setDiscountValue(total_discount);
			newInvoice.setTblRepInvoiceValuesList(lastInvoiceValues);
			session = SessionFactoryUtil.getSession();
			session.update(newInvoice);
			tx = session.beginTransaction();
			tx.commit();
			return "success";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;

		}

	}

	/*********************** Review *************************/
	private String passedParam;
	private Long invoiceId;
	private TblRepInvoice reviewedInvoice = new TblRepInvoice();

	public String getPassedParam() {
		return passedParam;
	}

	public void setPassedParam(String passedParam) {
		if (passedParam != null) {
			if (utils.isLong(passedParam)) {
				setInvoiceId(Long.parseLong(passedParam));
			} else {
				utils.sendRedirect(Constants.errorPage, false);
			}
		} else {
			utils.sendRedirect(Constants.errorPage, false);
		}
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		if (invoiceId == 0) {
			utils.sendRedirect(Constants.errorPage, false);
		}
		this.invoiceId = invoiceId;
	}

	public TblRepInvoice getReviewedInvoice() {
		return reviewedInvoice;
	}

	public void setReviewedInvoice(TblRepInvoice reviewedInvoice) {
		this.reviewedInvoice = reviewedInvoice;
	}

	public void prerender(ComponentSystemEvent event) {
		TblRepInvoice searchedInvoice = RepInvoiceDao.getInstance()
				.findById(invoiceId);
		if (searchedInvoice == null) {
			utils.sendRedirect(Constants.errorPage, false);
		} else {
			setReviewedInvoice(searchedInvoice);
		}

	}
	
	
	
	/*********************** update *************************/
	private String updatePassedParam;
	private Long updateInvoiceId;
	private TblRepInvoice updateInvoice = new TblRepInvoice();

	public String getUpdatePassedParam() {
		return updatePassedParam;
	}

	public void setUpdatePassedParam(String updatePassedParam) {
		if (updatePassedParam != null) {
			if (utils.isLong(updatePassedParam)) {
				setUpdateInvoiceId(Long.parseLong(updatePassedParam));
			} else {
				utils.sendRedirect(Constants.errorPage, false);
			}
		} else {
			utils.sendRedirect(Constants.errorPage, false);
		}
	}

	public Long getUpdateInvoiceId() {
		return updateInvoiceId;
	}

	public void setUpdateInvoiceId(Long updateInvoiceId) {
		if (updateInvoiceId == 0) {
			utils.sendRedirect(Constants.errorPage, false);
		}
		this.updateInvoiceId = updateInvoiceId;
	}

	public TblRepInvoice getUpdateInvoice() {
		return updateInvoice;
	}

	public void setUpdateInvoice(TblRepInvoice updateInvoice) {
		this.updateInvoice = updateInvoice;
	}

	public void prerenderUpdate(ComponentSystemEvent event) {
		TblRepInvoice searchedInvoice = RepInvoiceDao.getInstance()
				.findById(updateInvoiceId);
		if (searchedInvoice == null) {
			utils.sendRedirect(Constants.errorPage, false);
		} else {
			setUpdateInvoice(searchedInvoice);
		}

	}
	
	public String updateInvoice(){
		Session session = null;
		Transaction tx = null;
		try {
			List<TblRepInvoiceValues> lastInvoiceValues = new ArrayList<TblRepInvoiceValues>();
			double invoiceTotalBefore = 0.0;
			// insert incoming date
			updateInvoice.setByUserId(new User(loginBean.getId()));
			// newInvoice.setInvoiceDate(invoiceDate);

			session = SessionFactoryUtil.getSession();
			session.update(updateInvoice);

			// insert incoming values
			for (TblRepInvoiceValues itemValue : updateInvoice.getTblRepInvoiceValuesList()) {

				// get total mount

				double productPriceMax = itemValue.getProductId()
						.getRepMaxUnPrice();

				double mountPriceMax = productPriceMax
						* itemValue.getMaxMount();

				double productPriceMin = itemValue.getProductId()
						.getRepMinUnPrice();

				double mountPriceMin = productPriceMin
						* itemValue.getMinMount();

				// get total before discount
				double total_price = mountPriceMax + mountPriceMin;

				// get shown mount
				String showen_total_mount = itemValue.getMaxMount() + "."
						+ itemValue.getMinMount();

				itemValue.setShowenMount(showen_total_mount);
				itemValue.setMaxPrice(mountPriceMax);
				itemValue.setMinPrice(mountPriceMin);
				itemValue.setTotalPrice(total_price);
				itemValue.setInvoiceId(updateInvoice);
				lastInvoiceValues.add(itemValue);

				session = SessionFactoryUtil.getSession();
				session.update(itemValue);

				invoiceTotalBefore = invoiceTotalBefore + total_price;
			}

			double normal_discount_value = invoiceTotalBefore
					* (updateInvoice.getDiscountRate() / 100);
			double additional_discount_value = invoiceTotalBefore
					* (updateInvoice.getAdditionalDiscount() / 100);
			double total_discount = normal_discount_value
					+ additional_discount_value;
			double invoiceTotalAfter = invoiceTotalBefore - total_discount;


			updateInvoice.setTotalPriceBefore(invoiceTotalBefore);
			updateInvoice.setTotalPriceAfter(invoiceTotalAfter);
			updateInvoice.setDiscountValue(total_discount);
			updateInvoice.setTblRepInvoiceValuesList(lastInvoiceValues);
			session = SessionFactoryUtil.getSession();
			session.update(updateInvoice);
			tx = session.beginTransaction();
			tx.commit();
			return "success";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;

		}

	}
	
	/******************* View Numbers ********************/
	private double disVal;
	private double invoiceVal;
	private double invoiceNet;

	public double getDisVal() {
		return disVal;
	}

	public void setDisVal(double disVal) {
		this.disVal = disVal;
	}

	public double getInvoiceVal() {
		return invoiceVal;
	}

	public void setInvoiceVal(double invoiceVal) {
		this.invoiceVal = invoiceVal;
	}

	public double getInvoiceNet() {
		return invoiceNet;
	}

	public void setInvoiceNet(double invoiceNet) {
		this.invoiceNet = invoiceNet;
	}

	public void calcNewInvoice() {
		try {
			double invoiceTotalBefore = 0.0;

			// insert incoming values
			for (TblRepInvoiceValues itemValue : invoiceValues) {

				// get total mount
				double productPriceMax = itemValue.getProductId()
						.getRepMaxUnPrice();

				double mountPriceMax = productPriceMax
						* itemValue.getMaxMount();

				double productPriceMin = itemValue.getProductId()
						.getRepMinUnPrice();

				double mountPriceMin = productPriceMin
						* itemValue.getMinMount();

				// get total before discount
				double total_price = mountPriceMax + mountPriceMin;

				invoiceTotalBefore = invoiceTotalBefore + total_price;
			}

			double normal_discount_value = invoiceTotalBefore
					* (newInvoice.getDiscountRate() / 100);
			double additional_discount_value = invoiceTotalBefore
					* (newInvoice.getAdditionalDiscount() / 100);
			double total_discount = normal_discount_value
					+ additional_discount_value;
			double invoiceTotalAfter = invoiceTotalBefore - total_discount;
			setDisVal(total_discount);
			setInvoiceVal(invoiceTotalBefore);
			setInvoiceNet(invoiceTotalAfter);
		} catch (Exception e) {
				e.printStackTrace();
		} 

	}
	
	public void calcUpdateInvoice(){
		try {
			double invoiceTotalBefore = 0.0;
			// insert incoming values
			for (TblRepInvoiceValues itemValue : updateInvoice.getTblRepInvoiceValuesList()) {

				// get total mount

				double productPriceMax = itemValue.getProductId()
						.getRepMaxUnPrice();

				double mountPriceMax = productPriceMax
						* itemValue.getMaxMount();

				double productPriceMin = itemValue.getProductId()
						.getRepMinUnPrice();

				double mountPriceMin = productPriceMin
						* itemValue.getMinMount();
				// get total before discount
				double total_price = mountPriceMax + mountPriceMin;
				invoiceTotalBefore = invoiceTotalBefore + total_price;
			}
			double normal_discount_value = invoiceTotalBefore
					* (updateInvoice.getDiscountRate() / 100);
			double additional_discount_value = invoiceTotalBefore
					* (updateInvoice.getAdditionalDiscount() / 100);
			double total_discount = normal_discount_value
					+ additional_discount_value;
			double invoiceTotalAfter = invoiceTotalBefore - total_discount;
			updateInvoice.setDiscountValue(total_discount);
			updateInvoice.setTotalPriceBefore(invoiceTotalBefore);
			updateInvoice.setTotalPriceAfter(invoiceTotalAfter);
		  Session	session = SessionFactoryUtil.getSession();
			session.update(updateInvoice);
		 Transaction	tx = session.beginTransaction();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	// validate Number 

	public void validateNum(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedNumMsg = bundle.getString("DUPLICATENUM");


		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();


		// get number
		UIInput uiInputNum = (UIInput) components
				.findComponent("invNumber");
		String num = uiInputNum.getLocalValue() == null ? "" : uiInputNum
				.getLocalValue().toString();

		String numId = uiInputNum.getClientId();
		TblRepInvoice invoice = RepInvoiceDao.getInstance().findByNumber(BigInteger.valueOf(Long.parseLong(num)));

		// Let required="true" do its job.
		if (num.isEmpty()) {
			return;
		}
		if (invoice != null) {
			FacesMessage msg = new FacesMessage(duplicatedNumMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(numId, msg);
			fc.renderResponse();

		}

	}
	


}
