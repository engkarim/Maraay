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
@Table(name = "tbl_com_defects_date")
@NamedQueries({ @NamedQuery(name = "TblComDefectsDate.findAll", query = "SELECT t FROM TblComDefectsDate t") })
public class TblComDefectsDate implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long defectsDateId;
	private Date date;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double totalBefore;
	private Double totalAfter;
	private Double discountValue;
	private User byUserId;
	private List<TblComDefectsValue> tblComDefectsValueList;

	public TblComDefectsDate() {
	}

	public TblComDefectsDate(Long defectsDateId) {
		this.defectsDateId = defectsDateId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "defects_date_id")
	public Long getDefectsDateId() {
		return defectsDateId;
	}

	public void setDefectsDateId(Long defectsDateId) {
		this.defectsDateId = defectsDateId;
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

	@OneToMany(mappedBy = "defectsDateId", fetch = FetchType.LAZY)
	public List<TblComDefectsValue> getTblComDefectsValueList() {
		return tblComDefectsValueList;
	}

	public void setTblComDefectsValueList(
			List<TblComDefectsValue> tblComDefectsValueList) {
		this.tblComDefectsValueList = tblComDefectsValueList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (defectsDateId != null ? defectsDateId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblComDefectsDate)) {
			return false;
		}
		TblComDefectsDate other = (TblComDefectsDate) object;
		if ((this.defectsDateId == null && other.defectsDateId != null)
				|| (this.defectsDateId != null && !this.defectsDateId
						.equals(other.defectsDateId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblComDefectsDate[ defectsDateId="
				+ defectsDateId + " ]";
	}

}
