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
@Table(name = "tbl_rep_total_loading_value")
@NamedQueries({
    @NamedQuery(name = "TblRepTotalLoadingValue.findAll", query = "SELECT t FROM TblRepTotalLoadingValue t")})
public class TblRepTotalLoadingValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long totalLoadingValueId;
    private int maxMount;
    private double maxMountPrice;
    private int oldMaxMount;
    private double oldMaxMountPrice;
    private Integer minMount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double minMountPrice;
    private Integer oldMinMount;
    private Double oldMinMountPrice;
    private String showenMount;
    private Double totalPrice;
    private Integer isEdit = 0;
    private Product productId;
    private TblRepTotalLoadingDate totalLoadingDateId;

    public TblRepTotalLoadingValue() {
    }

    public TblRepTotalLoadingValue(Long totalLoadingValueId) {
        this.totalLoadingValueId = totalLoadingValueId;
    }

    public TblRepTotalLoadingValue(Long totalLoadingValueId, int maxMount, double maxMountPrice, int oldMaxMount, double oldMaxMountPrice) {
        this.totalLoadingValueId = totalLoadingValueId;
        this.maxMount = maxMount;
        this.maxMountPrice = maxMountPrice;
        this.oldMaxMount = oldMaxMount;
        this.oldMaxMountPrice = oldMaxMountPrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "total_loading_value_id")
    public Long getTotalLoadingValueId() {
        return totalLoadingValueId;
    }

    public void setTotalLoadingValueId(Long totalLoadingValueId) {
        this.totalLoadingValueId = totalLoadingValueId;
    }

    @Basic(optional = false)
    @Column(name = "max_mount")
    public int getMaxMount() {
        return maxMount;
    }

    public void setMaxMount(int maxMount) {
        this.maxMount = maxMount;
    }

    @Basic(optional = false)
    @Column(name = "max_mount_price")
    public double getMaxMountPrice() {
        return maxMountPrice;
    }

    public void setMaxMountPrice(double maxMountPrice) {
        this.maxMountPrice = maxMountPrice;
    }

    @Basic(optional = false)
    @Column(name = "old_max_mount")
    public int getOldMaxMount() {
        return oldMaxMount;
    }

    public void setOldMaxMount(int oldMaxMount) {
        this.oldMaxMount = oldMaxMount;
    }

    @Basic(optional = false)
    @Column(name = "old_max_mount_price")
    public double getOldMaxMountPrice() {
        return oldMaxMountPrice;
    }

    public void setOldMaxMountPrice(double oldMaxMountPrice) {
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @JoinColumn(name = "total_loading_date_id", referencedColumnName = "total_loading_date_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TblRepTotalLoadingDate getTotalLoadingDateId() {
        return totalLoadingDateId;
    }

    public void setTotalLoadingDateId(TblRepTotalLoadingDate totalLoadingDateId) {
        this.totalLoadingDateId = totalLoadingDateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (totalLoadingValueId != null ? totalLoadingValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepTotalLoadingValue)) {
            return false;
        }
        TblRepTotalLoadingValue other = (TblRepTotalLoadingValue) object;
        if ((this.totalLoadingValueId == null && other.totalLoadingValueId != null) || (this.totalLoadingValueId != null && !this.totalLoadingValueId.equals(other.totalLoadingValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepTotalLoadingValue[ totalLoadingValueId=" + totalLoadingValueId + " ]";
    }

}
