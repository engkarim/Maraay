/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

/**
 * 
 * @author karim
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String address;
	private String mobile;
	private String phone;
	private String label;
	private List<Product> productsList;
	private Direction direction = new Direction();
	private CustomerType customerType = new CustomerType();
	private List<DayWeek> daysList;
	private List<TblRepInvoice> tblRepInvoiceList;

	public Customer() {
	}

	public Customer(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@IndexColumn(name = "product_order")
	@JoinTable(name = "product_customer", joinColumns = { @JoinColumn(name = "customer_id") }, inverseJoinColumns = { @JoinColumn(name = "product_id") })
	public List<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<Product> productsList) {
		this.productsList = productsList;
	}

	@JoinColumn(name = "direction", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@JoinColumn(name = "customer_type", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	@OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY)
	public List<TblRepInvoice> getTblRepInvoiceList() {
		return tblRepInvoiceList;
	}

	public void setTblRepInvoiceList(List<TblRepInvoice> tblRepInvoiceList) {
		this.tblRepInvoiceList = tblRepInvoiceList;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	// @IndexColumn(name = "day_order")
	@JoinTable(name = "customer_day", joinColumns = { @JoinColumn(name = "customer_id") }, inverseJoinColumns = { @JoinColumn(name = "day_id") })
	public List<DayWeek> getDaysList() {
		return daysList;
	}

	public void setDaysList(List<DayWeek> daysList) {
		this.daysList = daysList;
	}

}
