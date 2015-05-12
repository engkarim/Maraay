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
@Table(name = "tbl_com_blending_date")
@NamedQueries({ @NamedQuery(name = "TblComBlendingDate.findAll", query = "SELECT t FROM TblComBlendingDate t") })
public class TblComBlendingDate implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long blendingDateId;
	private Date date;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double totalBefore;
	private Double incomingBlendingRateTotal;
	private Double incomingTotalAfter;
	private Double totalAfter;
	private User byUserId;
	private Double discountValue;
	private List<TblComBlendingValue> tblComBlendingValueList;

	public TblComBlendingDate() {
	}

	public TblComBlendingDate(Long blendingDateId) {
		this.blendingDateId = blendingDateId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "blending_date_id")
	public Long getBlendingDateId() {
		return blendingDateId;
	}

	public void setBlendingDateId(Long blendingDateId) {
		this.blendingDateId = blendingDateId;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "total_before")
	public Double getTotalBefore() {
		return totalBefore;
	}

	public void setTotalBefore(Double totalBefore) {
		this.totalBefore = totalBefore;
	}

	@Column(name = "incoming_blending_rate_total")
	public Double getIncomingBlendingRateTotal() {
		return incomingBlendingRateTotal;
	}

	public void setIncomingBlendingRateTotal(Double incomingBlendingRateTotal) {
		this.incomingBlendingRateTotal = incomingBlendingRateTotal;
	}
	
	@Column(name = "incoming_total_after")
	public Double getIncomingTotalAfter() {
		return incomingTotalAfter;
	}

	public void setIncomingTotalAfter(Double incomingTotalAfter) {
		this.incomingTotalAfter = incomingTotalAfter;
	}

	@Column(name = "total_after")
	public Double getTotalAfter() {
		return totalAfter;
	}

	public void setTotalAfter(Double totalAfter) {
		this.totalAfter = totalAfter;
	}
	

    @Column(name = "discount_value")
    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

	@JoinColumn(name = "by_user_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public User getByUserId() {
		return byUserId;
	}

	public void setByUserId(User byUserId) {
		this.byUserId = byUserId;
	}

	@OneToMany(mappedBy = "blendingDateId", fetch = FetchType.LAZY)
	public List<TblComBlendingValue> getTblComBlendingValueList() {
		return tblComBlendingValueList;
	}

	public void setTblComBlendingValueList(
			List<TblComBlendingValue> tblComBlendingValueList) {
		this.tblComBlendingValueList = tblComBlendingValueList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (blendingDateId != null ? blendingDateId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblComBlendingDate)) {
			return false;
		}
		TblComBlendingDate other = (TblComBlendingDate) object;
		if ((this.blendingDateId == null && other.blendingDateId != null)
				|| (this.blendingDateId != null && !this.blendingDateId
						.equals(other.blendingDateId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblComBlendingDate[ blendingDateId="
				+ blendingDateId + " ]";
	}

}
