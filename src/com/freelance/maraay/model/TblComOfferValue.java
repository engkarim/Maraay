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
@Table(name = "tbl_com_offer_value")
@NamedQueries({ @NamedQuery(name = "TblComOfferValue.findAll", query = "SELECT t FROM TblComOfferValue t") })
public class TblComOfferValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long offerValueId;
	private Integer maxMount;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double maxMountPrice;
	private Integer oldMaxMount;
	private Double oldMaxMountPrice;
	private Integer minMount;
	private Double minMountPrice;
	private Integer oldMinMount;
	private Double oldMinMountPrice;
	private Double totalBefore;
	private Double totalAfter;
	private Double totalMount;
	private Double oldTotalMount;
	private Integer isEdited = 0;
	private String showenMount;
	private TblComOfferDate offerDateId;
	private Product productId;

	public TblComOfferValue() {
	}

	public TblComOfferValue(Long offerValueId) {
		this.offerValueId = offerValueId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "offer_value_id")
	public Long getOfferValueId() {
		return offerValueId;
	}

	public void setOfferValueId(Long offerValueId) {
		this.offerValueId = offerValueId;
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

	@Column(name = "is_edited")
	public Integer getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Integer isEdited) {
		this.isEdited = isEdited;
	}

	@Column(name = "showen_mount")
	public String getShowenMount() {
		return showenMount;
	}

	public void setShowenMount(String showenMount) {
		this.showenMount = showenMount;
	}

	@JoinColumn(name = "offer_date_id", referencedColumnName = "offer_date_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public TblComOfferDate getOfferDateId() {
		return offerDateId;
	}

	public void setOfferDateId(TblComOfferDate offerDateId) {
		this.offerDateId = offerDateId;
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
		hash += (offerValueId != null ? offerValueId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblComOfferValue)) {
			return false;
		}
		TblComOfferValue other = (TblComOfferValue) object;
		if ((this.offerValueId == null && other.offerValueId != null)
				|| (this.offerValueId != null && !this.offerValueId
						.equals(other.offerValueId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblComOfferValue[ offerValueId="
				+ offerValueId + " ]";
	}

}
