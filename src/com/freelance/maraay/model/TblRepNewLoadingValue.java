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
@Table(name = "tbl_rep_new_loading_value")
@NamedQueries({
    @NamedQuery(name = "TblRepNewLoadingValue.findAll", query = "SELECT t FROM TblRepNewLoadingValue t")})
public class TblRepNewLoadingValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long newLoadingValueId;
    private Integer maxMount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double maxMountPrice;
    private Integer oldMaxMount;
    private Double oldMaxMountPrice;
    private Integer minMount;
    private Double minMountPrice;
    private Integer oldMinMount;
    private Double oldMinMountPrice;
    private String showenMount;
    private Double totalPrice;
    private Integer isEdit = 0;
    private Product productId;
    private TblRepNewLoadingDate newLoadingDateId;

    public TblRepNewLoadingValue() {
    }

    public TblRepNewLoadingValue(Long newLoadingValueId) {
        this.newLoadingValueId = newLoadingValueId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "new_loading_value_id")
    public Long getNewLoadingValueId() {
        return newLoadingValueId;
    }

    public void setNewLoadingValueId(Long newLoadingValueId) {
        this.newLoadingValueId = newLoadingValueId;
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

    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @JoinColumn(name = "new_loading_date_id", referencedColumnName = "new_loading_date_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TblRepNewLoadingDate getNewLoadingDateId() {
        return newLoadingDateId;
    }

    public void setNewLoadingDateId(TblRepNewLoadingDate newLoadingDateId) {
        this.newLoadingDateId = newLoadingDateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (newLoadingValueId != null ? newLoadingValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepNewLoadingValue)) {
            return false;
        }
        TblRepNewLoadingValue other = (TblRepNewLoadingValue) object;
        if ((this.newLoadingValueId == null && other.newLoadingValueId != null) || (this.newLoadingValueId != null && !this.newLoadingValueId.equals(other.newLoadingValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepNewLoadingValue[ newLoadingValueId=" + newLoadingValueId + " ]";
    }

}
