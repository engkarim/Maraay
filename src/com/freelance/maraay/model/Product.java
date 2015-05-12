/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author karim
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String barcode;
	private Double minUnitPrice;
	private Double maxUnitPrice;
	private Double repMaxUnPrice;
	private Double repMinUnPrice;
	private Integer maxMinCount;
	private Date addingDate;
	private Unit maxUnit;
	private Unit minUnit;
	private List<Customer> customersList;
	private List<TblComDefectsValue> tblComDefectsValueList;
	private List<TblComBlendingValue> tblComBlendingValueList;
	private List<TblComSalesValue> tblComSalesValueList;
	private List<TblComIncomingValue> tblComIncomingValueList;
	private List<TblComDiscountValue> tblComDiscountValueList;
	private List<TblComOfferValue> tblComOfferValueList;
	private List<TblRepInvoiceValues> tblRepInvoiceValuesList;
	private List<TblRepTotalLoadingValue> tblRepTotalLoadingValueList;
	private List<TblRepBlendingValue> tblRepBlendingValueList;
	private List<TblRepFirstTimeValue> tblRepFirstTimeValueList;
	private List<TblRepNewLoadingValue> tblRepNewLoadingValueList;
	private List<TblRepSalesValue> tblRepSalesValueList;
	private List<TblRepDefectValue> tblRepDefectValueList;
	private List<TblRepLastTimeValue> tblRepLastTimeValueList;
	private List<TblRepOfferValue> tblRepOfferValueList;

	public Product() {
	}

	public Product(Integer id) {
		this.id = id;
	}

	public Product(String name) {
		this.name = name;
	}

	public Product(Integer id, Date addingDate) {
		this.id = id;
		this.addingDate = addingDate;
	}

	@Id
	@GeneratedValue
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

	@Column(name = "barcode")
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name = "min_unit_price")
	public Double getMinUnitPrice() {
		return minUnitPrice;
	}

	public void setMinUnitPrice(Double minUnitPrice) {
		this.minUnitPrice = minUnitPrice;
	}

	@Column(name = "max_unit_price")
	public Double getMaxUnitPrice() {
		return maxUnitPrice;
	}

	public void setMaxUnitPrice(Double maxUnitPrice) {
		this.maxUnitPrice = maxUnitPrice;
	}

	@Column(name = "max_min_count")
	public Integer getMaxMinCount() {
		return maxMinCount;
	}

	public void setMaxMinCount(Integer maxMinCount) {
		this.maxMinCount = maxMinCount;
	}

	@Column(name = "rep_max_un_price")
	public Double getRepMaxUnPrice() {
		return repMaxUnPrice;
	}

	public void setRepMaxUnPrice(Double repMaxUnPrice) {
		this.repMaxUnPrice = repMaxUnPrice;
	}

	@Column(name = "rep_min_un_price")
	public Double getRepMinUnPrice() {
		return repMinUnPrice;
	}

	public void setRepMinUnPrice(Double repMinUnPrice) {
		this.repMinUnPrice = repMinUnPrice;
	}

	@Basic(optional = false)
	@Column(name = "adding_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAddingDate() {
		return addingDate;
	}

	public void setAddingDate(Date addingDate) {
		this.addingDate = addingDate;
	}

	@JoinColumn(name = "max_unit", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Unit getMaxUnit() {
		return maxUnit;
	}

	public void setMaxUnit(Unit maxUnit) {
		this.maxUnit = maxUnit;
	}

	@JoinColumn(name = "min_unit", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Unit getMinUnit() {
		return minUnit;
	}

	public void setMinUnit(Unit minUnit) {
		this.minUnit = minUnit;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "product_customer", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "customer_id") })
	public List<Customer> getCustomersList() {
		return customersList;
	}

	public void setCustomersList(List<Customer> customersList) {
		this.customersList = customersList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblComDefectsValue> getTblComDefectsValueList() {
		return tblComDefectsValueList;
	}

	public void setTblComDefectsValueList(
			List<TblComDefectsValue> tblComDefectsValueList) {
		this.tblComDefectsValueList = tblComDefectsValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblComBlendingValue> getTblComBlendingValueList() {
		return tblComBlendingValueList;
	}

	public void setTblComBlendingValueList(
			List<TblComBlendingValue> tblComBlendingValueList) {
		this.tblComBlendingValueList = tblComBlendingValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblComSalesValue> getTblComSalesValueList() {
		return tblComSalesValueList;
	}

	public void setTblComSalesValueList(
			List<TblComSalesValue> tblComSalesValueList) {
		this.tblComSalesValueList = tblComSalesValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblComIncomingValue> getTblComIncomingValueList() {
		return tblComIncomingValueList;
	}

	public void setTblComIncomingValueList(
			List<TblComIncomingValue> tblComIncomingValueList) {
		this.tblComIncomingValueList = tblComIncomingValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblComDiscountValue> getTblComDiscountValueList() {
		return tblComDiscountValueList;
	}

	public void setTblComDiscountValueList(
			List<TblComDiscountValue> tblComDiscountValueList) {
		this.tblComDiscountValueList = tblComDiscountValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblComOfferValue> getTblComOfferValueList() {
		return tblComOfferValueList;
	}

	public void setTblComOfferValueList(
			List<TblComOfferValue> tblComOfferValueList) {
		this.tblComOfferValueList = tblComOfferValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepInvoiceValues> getTblRepInvoiceValuesList() {
		return tblRepInvoiceValuesList;
	}

	public void setTblRepInvoiceValuesList(
			List<TblRepInvoiceValues> tblRepInvoiceValuesList) {
		this.tblRepInvoiceValuesList = tblRepInvoiceValuesList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepTotalLoadingValue> getTblRepTotalLoadingValueList() {
		return tblRepTotalLoadingValueList;
	}

	public void setTblRepTotalLoadingValueList(
			List<TblRepTotalLoadingValue> tblRepTotalLoadingValueList) {
		this.tblRepTotalLoadingValueList = tblRepTotalLoadingValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepBlendingValue> getTblRepBlendingValueList() {
		return tblRepBlendingValueList;
	}

	public void setTblRepBlendingValueList(
			List<TblRepBlendingValue> tblRepBlendingValueList) {
		this.tblRepBlendingValueList = tblRepBlendingValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepFirstTimeValue> getTblRepFirstTimeValueList() {
		return tblRepFirstTimeValueList;
	}

	public void setTblRepFirstTimeValueList(
			List<TblRepFirstTimeValue> tblRepFirstTimeValueList) {
		this.tblRepFirstTimeValueList = tblRepFirstTimeValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepNewLoadingValue> getTblRepNewLoadingValueList() {
		return tblRepNewLoadingValueList;
	}

	public void setTblRepNewLoadingValueList(
			List<TblRepNewLoadingValue> tblRepNewLoadingValueList) {
		this.tblRepNewLoadingValueList = tblRepNewLoadingValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepSalesValue> getTblRepSalesValueList() {
		return tblRepSalesValueList;
	}

	public void setTblRepSalesValueList(
			List<TblRepSalesValue> tblRepSalesValueList) {
		this.tblRepSalesValueList = tblRepSalesValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepDefectValue> getTblRepDefectValueList() {
		return tblRepDefectValueList;
	}

	public void setTblRepDefectValueList(
			List<TblRepDefectValue> tblRepDefectValueList) {
		this.tblRepDefectValueList = tblRepDefectValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepLastTimeValue> getTblRepLastTimeValueList() {
		return tblRepLastTimeValueList;
	}

	public void setTblRepLastTimeValueList(
			List<TblRepLastTimeValue> tblRepLastTimeValueList) {
		this.tblRepLastTimeValueList = tblRepLastTimeValueList;
	}

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	public List<TblRepOfferValue> getTblRepOfferValueList() {
		return tblRepOfferValueList;
	}

	public void setTblRepOfferValueList(
			List<TblRepOfferValue> tblRepOfferValueList) {
		this.tblRepOfferValueList = tblRepOfferValueList;
	}

}
