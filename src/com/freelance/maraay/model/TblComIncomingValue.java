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
@Table(name = "tbl_com_incoming_value")
@NamedQueries({ @NamedQuery(name = "TblComIncomingValue.findAll", query = "SELECT t FROM TblComIncomingValue t") })
public class TblComIncomingValue implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long incomingValueId;
	private Integer maxMount;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double maxMountPrice;
	private Integer oldMaxMount;
	private Integer isEdited = 0;
	private Double totalBefore;
	private Integer minMount;
	private Double minMountPrice;
	private Integer oldMinMount;
	private Double oldMinMountPrice;
	private Double oldMaxMountPrice;
	private Double totalAfter;
	private Double totalMount;
	private Double oldTotalMount;
	private String showenMount;
	private TblComIncomingDate incomingDateId;
	private Product productId;

	public TblComIncomingValue() {
	}

	public TblComIncomingValue(Long incomingValueId) {
		this.incomingValueId = incomingValueId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "incoming_value_id")
	public Long getIncomingValueId() {
		return incomingValueId;
	}

	public void setIncomingValueId(Long incomingValueId) {
		this.incomingValueId = incomingValueId;
	}

	@Column(name = "max_mount")
	public Integer getMaxMount() {
		return maxMount;
	}

	public void setMaxMount(Integer maxMount) {
		this.maxMount = maxMount;
	}

	@Column(name = "max_mount_price")
	public Double getMaxMountPrice() {
		return maxMountPrice;
	}

	public void setMaxMountPrice(Double maxMountPrice) {
		this.maxMountPrice = maxMountPrice;
	}

	@Column(name = "old_max_mount")
	public Integer getOldMaxMount() {
		return oldMaxMount;
	}

	public void setOldMaxMount(Integer oldMaxMount) {
		this.oldMaxMount = oldMaxMount;
	}

	@Column(name = "is_edited")
	public Integer getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Integer isEdited) {
		this.isEdited = isEdited;
	}

	@Column(name = "total_before")
	public Double getTotalBefore() {
		return totalBefore;
	}

	public void setTotalBefore(Double totalBefore) {
		this.totalBefore = totalBefore;
	}

	@Column(name = "min_mount")
	public Integer getMinMount() {
		return minMount;
	}

	public void setMinMount(Integer minMount) {
		this.minMount = minMount;
	}

	@Column(name = "min_mount_price")
	public Double getMinMountPrice() {
		return minMountPrice;
	}

	public void setMinMountPrice(Double minMountPrice) {
		this.minMountPrice = minMountPrice;
	}

	@Column(name = "old_min_mount")
	public Integer getOldMinMount() {
		return oldMinMount;
	}

	public void setOldMinMount(Integer oldMinMount) {
		this.oldMinMount = oldMinMount;
	}

	@Column(name = "old_min_mount_price")
	public Double getOldMinMountPrice() {
		return oldMinMountPrice;
	}

	public void setOldMinMountPrice(Double oldMinMountPrice) {
		this.oldMinMountPrice = oldMinMountPrice;
	}

	@Column(name = "old_max_mount_price")
	public Double getOldMaxMountPrice() {
		return oldMaxMountPrice;
	}

	public void setOldMaxMountPrice(Double oldMaxMountPrice) {
		this.oldMaxMountPrice = oldMaxMountPrice;
	}

	@Column(name = "total_after")
	public Double getTotalAfter() {
		return totalAfter;
	}

	public void setTotalAfter(Double totalAfter) {
		this.totalAfter = totalAfter;
	}

	@Column(name = "total_mount")
	public Double getTotalMount() {
		return totalMount;
	}

	public void setTotalMount(Double totalMount) {
		this.totalMount = totalMount;
	}

	@Column(name = "old_total_mount")
	public Double getOldTotalMount() {
		return oldTotalMount;
	}

	public void setOldTotalMount(Double oldTotalMount) {
		this.oldTotalMount = oldTotalMount;
	}

	@Column(name = "showen_mount")
	public String getShowenMount() {
		return showenMount;
	}

	public void setShowenMount(String showenMount) {
		this.showenMount = showenMount;
	}

	@JoinColumn(name = "incoming_date_id", referencedColumnName = "incoming_date_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public TblComIncomingDate getIncomingDateId() {
		return incomingDateId;
	}

	public void setIncomingDateId(TblComIncomingDate incomingDateId) {
		this.incomingDateId = incomingDateId;
	}

	@JoinColumn(name = "product_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (incomingValueId != null ? incomingValueId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblComIncomingValue)) {
			return false;
		}
		TblComIncomingValue other = (TblComIncomingValue) object;
		if ((this.incomingValueId == null && other.incomingValueId != null)
				|| (this.incomingValueId != null && !this.incomingValueId
						.equals(other.incomingValueId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblComIncomingValue[ incomingValueId="
				+ incomingValueId + " ]";
	}

}
