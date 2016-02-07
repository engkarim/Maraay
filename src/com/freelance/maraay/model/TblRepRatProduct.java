/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author karim
 */
@Entity
@Table(name = "tbl_rep_rat_product")
@NamedQueries({
    @NamedQuery(name = "TblRepRatProduct.findAll", query = "SELECT t FROM TblRepRatProduct t")})
public class TblRepRatProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productId;
    private Date fromDate;
    private Date toDate;
    private Integer isEdited;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double productTarget;
    private Direction direction;
    private Employee representative;
    private User byUser;

    public TblRepRatProduct() {
    }

    public TblRepRatProduct(Long productId) {
        this.productId = productId;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Column(name = "is_edited")
    public Integer getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Integer isEdited) {
        this.isEdited = isEdited;
    }

    @Column(name = "product_target")
    public Double getProductTarget() {
        return productTarget;
    }

    public void setProductTarget(Double productTarget) {
        this.productTarget = productTarget;
    }

    @JoinColumn(name = "direction", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @JoinColumn(name = "representative", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Employee getRepresentative() {
        return representative;
    }

    public void setRepresentative(Employee representative) {
        this.representative = representative;
    }

    @JoinColumn(name = "by_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepRatProduct)) {
            return false;
        }
        TblRepRatProduct other = (TblRepRatProduct) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepRatProduct[ productId=" + productId + " ]";
    }

}
