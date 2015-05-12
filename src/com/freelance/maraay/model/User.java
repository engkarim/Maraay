/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * 
 * @author karim
 */
@Entity
@Table(name = "user")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u") })
@Proxy(lazy = false)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String userName;
	private String password;
	private String name;
	private UserType userType = new UserType();
	private List<TblComIncomingDate> tblComIncomingDateList;
	private List<TblComDefectsDate> tblComDefectsDateList;
	private List<TblComBlendingDate> tblComBlendingDateList;
	private List<TblComDiscountDate> tblComDiscountDateList;
	private List<TblComSalesDate> tblComSalesDateList;
	private List<TblComOfferDate> tblComOfferDateList;
	private List<TblComSupplyValue> tblComSupplyValueList;
	private List<TblRepInvoice> tblRepInvoiceList;
	private List<TblRepOfferDate> tblRepOfferDateList;
	private List<TblRepFirstTimeDate> tblRepFirstTimeDateList;
	private List<TblRepNewLoadingDate> tblRepNewLoadingDateList;
	private List<TblRepDefectDate> tblRepDefectDateList;
	private List<TblRepSalesDate> tblRepSalesDateList;
	private List<TblRepBlendingDate> tblRepBlendingDateList;
	private List<TblRepLastTimeDate> tblRepLastTimeDateList;
	private List<TblRepTotalLoadingDate> tblRepTotalLoadingDateList;

	public User() {
	}

	public User(Integer id) {
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

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JoinColumn(name = "user_type", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblComIncomingDate> getTblComIncomingDateList() {
		return tblComIncomingDateList;
	}

	public void setTblComIncomingDateList(
			List<TblComIncomingDate> tblComIncomingDateList) {
		this.tblComIncomingDateList = tblComIncomingDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblComDefectsDate> getTblComDefectsDateList() {
		return tblComDefectsDateList;
	}

	public void setTblComDefectsDateList(
			List<TblComDefectsDate> tblComDefectsDateList) {
		this.tblComDefectsDateList = tblComDefectsDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblComBlendingDate> getTblComBlendingDateList() {
		return tblComBlendingDateList;
	}

	public void setTblComBlendingDateList(
			List<TblComBlendingDate> tblComBlendingDateList) {
		this.tblComBlendingDateList = tblComBlendingDateList;
	}

	@OneToMany(mappedBy = "byUser", fetch = FetchType.LAZY)
	public List<TblComDiscountDate> getTblComDiscountDateList() {
		return tblComDiscountDateList;
	}

	public void setTblComDiscountDateList(
			List<TblComDiscountDate> tblComDiscountDateList) {
		this.tblComDiscountDateList = tblComDiscountDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblComSalesDate> getTblComSalesDateList() {
		return tblComSalesDateList;
	}

	public void setTblComSalesDateList(List<TblComSalesDate> tblComSalesDateList) {
		this.tblComSalesDateList = tblComSalesDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblComOfferDate> getTblComOfferDateList() {
		return tblComOfferDateList;
	}

	public void setTblComOfferDateList(List<TblComOfferDate> tblComOfferDateList) {
		this.tblComOfferDateList = tblComOfferDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblComSupplyValue> getTblComSupplyValueList() {
		return tblComSupplyValueList;
	}

	public void setTblComSupplyValueList(
			List<TblComSupplyValue> tblComSupplyValueList) {
		this.tblComSupplyValueList = tblComSupplyValueList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepInvoice> getTblRepInvoiceList() {
		return tblRepInvoiceList;
	}

	public void setTblRepInvoiceList(List<TblRepInvoice> tblRepInvoiceList) {
		this.tblRepInvoiceList = tblRepInvoiceList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepOfferDate> getTblRepOfferDateList() {
		return tblRepOfferDateList;
	}

	public void setTblRepOfferDateList(List<TblRepOfferDate> tblRepOfferDateList) {
		this.tblRepOfferDateList = tblRepOfferDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepFirstTimeDate> getTblRepFirstTimeDateList() {
		return tblRepFirstTimeDateList;
	}

	public void setTblRepFirstTimeDateList(
			List<TblRepFirstTimeDate> tblRepFirstTimeDateList) {
		this.tblRepFirstTimeDateList = tblRepFirstTimeDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepNewLoadingDate> getTblRepNewLoadingDateList() {
		return tblRepNewLoadingDateList;
	}

	public void setTblRepNewLoadingDateList(
			List<TblRepNewLoadingDate> tblRepNewLoadingDateList) {
		this.tblRepNewLoadingDateList = tblRepNewLoadingDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepDefectDate> getTblRepDefectDateList() {
		return tblRepDefectDateList;
	}

	public void setTblRepDefectDateList(
			List<TblRepDefectDate> tblRepDefectDateList) {
		this.tblRepDefectDateList = tblRepDefectDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepSalesDate> getTblRepSalesDateList() {
		return tblRepSalesDateList;
	}

	public void setTblRepSalesDateList(List<TblRepSalesDate> tblRepSalesDateList) {
		this.tblRepSalesDateList = tblRepSalesDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepBlendingDate> getTblRepBlendingDateList() {
		return tblRepBlendingDateList;
	}

	public void setTblRepBlendingDateList(
			List<TblRepBlendingDate> tblRepBlendingDateList) {
		this.tblRepBlendingDateList = tblRepBlendingDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepLastTimeDate> getTblRepLastTimeDateList() {
		return tblRepLastTimeDateList;
	}

	public void setTblRepLastTimeDateList(
			List<TblRepLastTimeDate> tblRepLastTimeDateList) {
		this.tblRepLastTimeDateList = tblRepLastTimeDateList;
	}

	@OneToMany(mappedBy = "byUserId", fetch = FetchType.LAZY)
	public List<TblRepTotalLoadingDate> getTblRepTotalLoadingDateList() {
		return tblRepTotalLoadingDateList;
	}

	public void setTblRepTotalLoadingDateList(
			List<TblRepTotalLoadingDate> tblRepTotalLoadingDateList) {
		this.tblRepTotalLoadingDateList = tblRepTotalLoadingDateList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "maraay.User[ id=" + id + " ]";
	}

}
