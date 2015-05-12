package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.freelance.maraay.model.CustomerType;
import com.freelance.maraay.dao.CustomerTypeDao;

@ManagedBean
@ViewScoped
public class CustomerTypeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomerTypeDao customerTypeDao = CustomerTypeDao.getInstance();
	private CustomerType customerType = new CustomerType();
	private List<CustomerType> customerTypes;

	public List<CustomerType> getCustomerTypes() {
		customerTypes = customerTypeDao.listAllCustomerType();
		return customerTypes;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
}
