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
@Table(name = "tbl_com_discount_value")
@NamedQueries({
    @NamedQuery(name = "TblComDiscountValue.findAll", query = "SELECT t FROM TblComDiscountValue t")})
public class TblComDiscountValue implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private Long discountId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double discountValue;
        private Integer isEdited = 0;
        private Double oldValue;
        private TblComDiscountDate discountDate;
        private Product productId;

    public TblComDiscountValue() {
    }

    public TblComDiscountValue(Long discountId) {
        this.discountId = discountId;
    }
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "discount_id")
    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

@Column(name = "discount_value")
    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

@Column(name = "is_edited")
    public Integer getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Integer isEdited) {
        this.isEdited = isEdited;
    }

@Column(name = "old_value")
    public Double getOldValue() {
        return oldValue;
    }

    public void setOldValue(Double oldValue) {
        this.oldValue = oldValue;
    }

@JoinColumn(name = "discount_date", referencedColumnName = "date_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TblComDiscountDate getDiscountDate() {
        return discountDate;
    }

    public void setDiscountDate(TblComDiscountDate discountDate) {
        this.discountDate = discountDate;
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
        hash += (discountId != null ? discountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblComDiscountValue)) {
            return false;
        }
        TblComDiscountValue other = (TblComDiscountValue) object;
        if ((this.discountId == null && other.discountId != null) || (this.discountId != null && !this.discountId.equals(other.discountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblComDiscountValue[ discountId=" + discountId + " ]";
    }
    
}
