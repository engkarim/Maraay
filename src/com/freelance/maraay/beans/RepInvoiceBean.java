package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.CustomerDao;
import com.freelance.maraay.dao.RepInvoiceDao;
import com.freelance.maraay.model.Customer;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepInvoice;
import com.freelance.maraay.model.TblRepInvoiceValues;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.SessionFactoryUtil;

@ManagedBean
@ViewScoped
public class RepInvoiceBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomerDao customerDao = CustomerDao.getInstance();
	private TblRepInvoice newInvoice = new TblRepInvoice();
	private List<Product> allProducts;
	private Integer customerId;
	private Customer customer = new Customer();
	private List<TblRepInvoiceValues> invoiceValues = new ArrayList<TblRepInvoiceValues>();
	private List<TblRepInvoice> invoices;
	private List<TblRepInvoice> filteredInvoice;

	public List<TblRepInvoice> getInvoices() {
		invoices = RepInvoiceDao.getInstance().listAll();
		System.out.println(invoices.size() + "))))))))))))))))))))))))");
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
				;
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

}
