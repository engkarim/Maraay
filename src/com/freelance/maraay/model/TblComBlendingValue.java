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
@Table(name = "tbl_com_blending_value")
@NamedQueries({ @NamedQuery(name = "TblComBlendingValue.findAll", query = "SELECT t FROM TblComBlendingValue t") })
public class TblComBlendingValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer maxMount;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double maxMountPrice;
	private Integer oldMaxMount;
	private Double incomingBlendingRate;
	private Integer isEdited = 0;
	private Double oldMaxMountPrice;
	private Integer minMount;
	private Double minMountPrice;
	private Integer oldMinMount;
	private Double oldMinMountPrice;
	private Double totalMount;
	private Double totalBefore;
	private Double totalAfter;
	private Double oldTotalMount;
	private String showenMount;
	private TblComBlendingDate blendingDateId;
	private Product productId;

	public TblComBlendingValue() {
	}

	public TblComBlendingValue(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Column(name = "incoming_blending_rate")
	public Double getIncomingBlendingRate() {
		return incomingBlendingRate;
	}

	public void setIncomingBlendingRate(Double incomingBlendingRate) {
		this.incomingBlendingRate = incomingBlendingRate;
	}

	@Column(name = "is_edited")
	public Integer getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Integer isEdited) {
		this.isEdited = isEdited;
	}

	@Column(name = "old_max_mount_price")
	public Double getOldMaxMountPrice() {
		return oldMaxMountPrice;
	}

	public void setOldMaxMountPrice(Double oldMaxMountPrice) {
		this.oldMaxMountPrice = oldMaxMountPrice;
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

	@JoinColumn(name = "blending_date_id", referencedColumnName = "blending_date_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public TblComBlendingDate getBlendingDateId() {
		return blendingDateId;
	}

	public void setBlendingDateId(TblComBlendingDate blendingDateId) {
		this.blendingDateId = blendingDateId;
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
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblComBlendingValue)) {
			return false;
		}
		TblComBlendingValue other = (TblComBlendingValue) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblComBlendingValue[ id=" + id
				+ " ]";
	}

}
