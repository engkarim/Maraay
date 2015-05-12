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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author karim
 */
@Entity
@Table(name = "direction")
public class Direction implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Employee representative = new Employee();
	private Employee driver = new Employee();
	private List<Customer> customerList;
	private List<TblRepOfferDate> tblRepOfferDateList;
	private List<TblRepFirstTimeDate> tblRepFirstTimeDateList;
	private List<TblRepNewLoadingDate> tblRepNewLoadingDateList;
	private List<TblRepDefectDate> tblRepDefectDateList;
	private List<TblRepSalesDate> tblRepSalesDateList;
	private List<TblRepBlendingDate> tblRepBlendingDateList;
	private List<TblRepLastTimeDate> tblRepLastTimeDateList;
	private List<TblRepTotalLoadingDate> tblRepTotalLoadingDateList;

	public Direction() {
	}

	public Direction(Integer id) {
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

	@JoinColumn(name = "representative", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Employee getRepresentative() {
		return representative;
	}

	public void setRepresentative(Employee representative) {
		this.representative = representative;
	}

	@JoinColumn(name = "driver", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Employee getDriver() {
		return driver;
	}

	public void setDriver(Employee driver) {
		this.driver = driver;
	}

	@OneToMany(mappedBy = "direction", fetch = FetchType.LAZY)
	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblRepOfferDate> getTblRepOfferDateList() {
		return tblRepOfferDateList;
	}

	public void setTblRepOfferDateList(List<TblRepOfferDate> tblRepOfferDateList) {
		this.tblRepOfferDateList = tblRepOfferDateList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblRepFirstTimeDate> getTblRepFirstTimeDateList() {
		return tblRepFirstTimeDateList;
	}

	public void setTblRepFirstTimeDateList(
			List<TblRepFirstTimeDate> tblRepFirstTimeDateList) {
		this.tblRepFirstTimeDateList = tblRepFirstTimeDateList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblRepNewLoadingDate> getTblRepNewLoadingDateList() {
		return tblRepNewLoadingDateList;
	}

	public void setTblRepNewLoadingDateList(
			List<TblRepNewLoadingDate> tblRepNewLoadingDateList) {
		this.tblRepNewLoadingDateList = tblRepNewLoadingDateList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblRepDefectDate> getTblRepDefectDateList() {
		return tblRepDefectDateList;
	}

	public void setTblRepDefectDateList(
			List<TblRepDefectDate> tblRepDefectDateList) {
		this.tblRepDefectDateList = tblRepDefectDateList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblRepSalesDate> getTblRepSalesDateList() {
		return tblRepSalesDateList;
	}

	public void setTblRepSalesDateList(List<TblRepSalesDate> tblRepSalesDateList) {
		this.tblRepSalesDateList = tblRepSalesDateList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblRepBlendingDate> getTblRepBlendingDateList() {
		return tblRepBlendingDateList;
	}

	public void setTblRepBlendingDateList(
			List<TblRepBlendingDate> tblRepBlendingDateList) {
		this.tblRepBlendingDateList = tblRepBlendingDateList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblRepLastTimeDate> getTblRepLastTimeDateList() {
		return tblRepLastTimeDateList;
	}

	public void setTblRepLastTimeDateList(
			List<TblRepLastTimeDate> tblRepLastTimeDateList) {
		this.tblRepLastTimeDateList = tblRepLastTimeDateList;
	}

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
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
		if (!(object instanceof Direction)) {
			return false;
		}
		Direction other = (Direction) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "maraay.Direction[ id=" + id + " ]";
	}

}