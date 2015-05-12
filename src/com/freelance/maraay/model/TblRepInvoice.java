/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author karim
 */
@Entity
@Table(name = "tbl_rep_invoice")
@NamedQueries({ @NamedQuery(name = "TblRepInvoice.findAll", query = "SELECT t FROM TblRepInvoice t") })
public class TblRepInvoice implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long invoiceId;
	private BigInteger invoiceNumber;
	private Date invoiceDate;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double totalPriceBefore;
	private Double discountRate;
	private Double additionalDiscount;
	private Double discountValue;
	private Double totalPriceAfter;
	private Integer isEdit = 0;
	private Customer customerId;
	private User byUserId;
	private List<TblRepInvoiceValues> tblRepInvoiceValuesList;

	public TblRepInvoice() {
	}

	public TblRepInvoice(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "invoice_id")
	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "invoice_number")
	public BigInteger getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(BigInteger invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Column(name = "invoice_date")
	@Temporal(TemporalType.DATE)
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column(name = "total_price_before")
	public Double getTotalPriceBefore() {
		return totalPriceBefore;
	}

	public void setTotalPriceBefore(Double totalPriceBefore) {
		this.totalPriceBefore = totalPriceBefore;
	}

	@Column(name = "discount_rate")
	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name = "additional_discount")
	public Double getAdditionalDiscount() {
		return additionalDiscount;
	}

	public void setAdditionalDiscount(Double additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}

	@Column(name = "discount_value")
	public Double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}

	@Column(name = "total_price_after")
	public Double getTotalPriceAfter() {
		return totalPriceAfter;
	}

	public void setTotalPriceAfter(Double totalPriceAfter) {
		this.totalPriceAfter = totalPriceAfter;
	}

	@Column(name = "is_edit")
	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	@JoinColumn(name = "by_user_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public User getByUserId() {
		return byUserId;
	}

	public void setByUserId(User byUserId) {
		this.byUserId = byUserId;
	}

	@OneToMany(mappedBy = "invoiceId", fetch = FetchType.LAZY)
	public List<TblRepInvoiceValues> getTblRepInvoiceValuesList() {
		return tblRepInvoiceValuesList;
	}

	public void setTblRepInvoiceValuesList(
			List<TblRepInvoiceValues> tblRepInvoiceValuesList) {
		this.tblRepInvoiceValuesList = tblRepInvoiceValuesList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (invoiceId != null ? invoiceId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblRepInvoice)) {
			return false;
		}
		TblRepInvoice other = (TblRepInvoice) object;
		if ((this.invoiceId == null && other.invoiceId != null)
				|| (this.invoiceId != null && !this.invoiceId
						.equals(other.invoiceId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblRepInvoice[ invoiceId="
				+ invoiceId + " ]";
	}

}
