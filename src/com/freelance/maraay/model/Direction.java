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
	private List<Customer> customerList;
	private List<TblRepOfferDate> tblRepOfferDateList;
	private List<TblRepFirstTimeDate> tblRepFirstTimeDateList;
	private List<TblRepNewLoadingDate> tblRepNewLoadingDateList;
	private List<TblRepDefectDate> tblRepDefectDateList;
	private List<TblRepSalesDate> tblRepSalesDateList;
	private List<TblRepBlendingDate> tblRepBlendingDateList;
	private List<TblRepLastTimeDate> tblRepLastTimeDateList;
	private List<TblRepTotalLoadingDate> tblRepTotalLoadingDateList;
	private List<TblLuDirRepDriv> tblLuDirRepDrivList;
	private List<TblRepRatAchieved> tblRepRatAchievedList;
	private List<TblRepRatVisiting> tblRepRatVisitingList;
	private List<TblRepRatCovering> tblRepRatCoveringList;
	private List<TblRepRatProduct> tblRepRatProductList;
	private List<TblRepRatBlending> tblRepRatBlendingList;

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

	@OneToMany(mappedBy = "directionId", fetch = FetchType.LAZY)
	public List<TblLuDirRepDriv> getTblLuDirRepDrivList() {
		return tblLuDirRepDrivList;
	}

	public void setTblLuDirRepDrivList(List<TblLuDirRepDriv> tblLuDirRepDrivList) {
		this.tblLuDirRepDrivList = tblLuDirRepDrivList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "direction", fetch = FetchType.LAZY)
	public List<TblRepRatAchieved> getTblRepRatAchievedList() {
		return tblRepRatAchievedList;
	}

	public void setTblRepRatAchievedList(
			List<TblRepRatAchieved> tblRepRatAchievedList) {
		this.tblRepRatAchievedList = tblRepRatAchievedList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "direction", fetch = FetchType.LAZY)
	public List<TblRepRatVisiting> getTblRepRatVisitingList() {
		return tblRepRatVisitingList;
	}

	public void setTblRepRatVisitingList(
			List<TblRepRatVisiting> tblRepRatVisitingList) {
		this.tblRepRatVisitingList = tblRepRatVisitingList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "direction", fetch = FetchType.LAZY)
	public List<TblRepRatCovering> getTblRepRatCoveringList() {
		return tblRepRatCoveringList;
	}

	public void setTblRepRatCoveringList(
			List<TblRepRatCovering> tblRepRatCoveringList) {
		this.tblRepRatCoveringList = tblRepRatCoveringList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "direction", fetch = FetchType.LAZY)
	public List<TblRepRatProduct> getTblRepRatProductList() {
		return tblRepRatProductList;
	}

	public void setTblRepRatProductList(
			List<TblRepRatProduct> tblRepRatProductList) {
		this.tblRepRatProductList = tblRepRatProductList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "direction", fetch = FetchType.LAZY)
	public List<TblRepRatBlending> getTblRepRatBlendingList() {
		return tblRepRatBlendingList;
	}

	public void setTblRepRatBlendingList(
			List<TblRepRatBlending> tblRepRatBlendingList) {
		this.tblRepRatBlendingList = tblRepRatBlendingList;
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
