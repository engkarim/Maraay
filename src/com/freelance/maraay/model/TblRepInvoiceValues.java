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
@Table(name = "tbl_rep_invoice_values")
@NamedQueries({ @NamedQuery(name = "TblRepInvoiceValues.findAll", query = "SELECT t FROM TblRepInvoiceValues t") })
public class TblRepInvoiceValues implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long invoiceValueId;
	private Integer maxMount;
	private Integer oldMaxMount;
	private Integer minMount;
	private Integer oldMinMount;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double maxPrice;
	private Double oldMaxPrice;
	private Double minPrice;
	private Double oldMinPrice;
	private String showenMount;
	private Double totalPrice;
	private Integer isEdit = 0;
	private TblRepInvoice invoiceId;
	private Product productId;

	public TblRepInvoiceValues() {
	}

	public TblRepInvoiceValues(Long invoiceValueId) {
		this.invoiceValueId = invoiceValueId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "invoice_value_id")
	public Long getInvoiceValueId() {
		return invoiceValueId;
	}

	public void setInvoiceValueId(Long invoiceValueId) {
		this.invoiceValueId = invoiceValueId;
	}

	@Column(name = "max_mount")
	public Integer getMaxMount() {
		return maxMount;
	}

	public void setMaxMount(Integer maxMount) {
		this.maxMount = maxMount;
	}

	@Column(name = "old_max_mount")
	public Integer getOldMaxMount() {
		return oldMaxMount;
	}

	public void setOldMaxMount(Integer oldMaxMount) {
		this.oldMaxMount = oldMaxMount;
	}

	@Column(name = "min_mount")
	public Integer getMinMount() {
		return minMount;
	}

	public void setMinMount(Integer minMount) {
		this.minMount = minMount;
	}

	@Column(name = "old_min_mount")
	public Integer getOldMinMount() {
		return oldMinMount;
	}

	public void setOldMinMount(Integer oldMinMount) {
		this.oldMinMount = oldMinMount;
	}

	@Column(name = "max_price")
	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	@Column(name = "old_max_price")
	public Double getOldMaxPrice() {
		return oldMaxPrice;
	}

	public void setOldMaxPrice(Double oldMaxPrice) {
		this.oldMaxPrice = oldMaxPrice;
	}

	@Column(name = "min_price")
	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	@Column(name = "old_min_price")
	public Double getOldMinPrice() {
		return oldMinPrice;
	}

	public void setOldMinPrice(Double oldMinPrice) {
		this.oldMinPrice = oldMinPrice;
	}

	@Column(name = "showen_mount")
	public String getShowenMount() {
		return showenMount;
	}

	public void setShowenMount(String showenMount) {
		this.showenMount = showenMount;
	}

	@Column(name = "total_price")
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "is_edit")
	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	@JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
	@ManyToOne(fetch = FetchType.LAZY)
	public TblRepInvoice getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(TblRepInvoice invoiceId) {
		this.invoiceId = invoiceId;
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
		hash += (invoiceValueId != null ? invoiceValueId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TblRepInvoiceValues)) {
			return false;
		}
		TblRepInvoiceValues other = (TblRepInvoiceValues) object;
		if ((this.invoiceValueId == null && other.invoiceValueId != null)
				|| (this.invoiceValueId != null && !this.invoiceValueId
						.equals(other.invoiceValueId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.freelance.maraay.model.TblRepInvoiceValues[ invoiceValueId="
				+ invoiceValueId + " ]";
	}

}
