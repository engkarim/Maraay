/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;

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
import javax.persistence.Table;

/**
 * 
 * @author karim
 */
@Entity
@Table(name = "tbl_com_sales_value")
@NamedQueries({ @NamedQuery(name = "TblComSalesValue.findAll", query = "SELECT t FROM TblComSalesValue t") })
public class TblComSalesValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long salesValueId;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double totalMount;
	private Double totalBefore;
	private Double totalAfter;
	private String showenMount;
	private Product productId;
	private TblComSalesDate salesDateId;

	public TblComSalesValue() {
	}

	public TblComSalesValue(Long salesValueId) {
		this.salesValueId = salesValueId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "sales_value_id")
	public Long getSalesValueId() {
		return salesValueId;
	}

	public void setSalesValueId(Long salesValueId) {
		this.salesValueId = salesValueId;
	}

	@Column(name = "total_mount")
	public Double getTotalMount() {
		return totalMount;
	}

	public void setTotalMount(Double totalMount) {
		this.totalMount = totalMount;
	}

	@Column(name = "total_before")
	public Double getTotalBefore() {
		return totalBefore;
	}

	public void setTotalBefore(Double totalBefore) {
		this.totalBefore = totalBefore;
	}

	@Column(name = "total_after")
	public Double getTotalAfter() {
		return totalAfter;
	}

	public void setTotalAfter(Double totalAfter) {
		this.totalAfter = totalAfter;
	}

	@Column(name = "showen_mount")
	public String getShowenMount() {
		return showenMount;
	}

	public void setShowenMount(String showenMount) {
		this.showenMount = showenMount;
	}

	@JoinColumn(name = "product_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	@JoinColumn(name = "sales_date_id", referencedColumnName = "sales_date_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public TblComSalesDate getSalesDateId() {
		return salesDateId;
	}

	public void setSalesDateId(TblComSalesDate salesDateId) {
		this.salesDateId = salesDateId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (salesValueId != null ? salesValueId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblComSalesValue)) {
			return false;
		}
		TblComSalesValue other = (TblComSalesValue) object;
		if ((this.salesValueId == null && other.salesValueId != null)
				|| (this.salesValueId != null && !this.salesValueId
						.equals(other.salesValueId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblComSalesValue[ salesValueId="
				+ salesValueId + " ]";
	}

}
