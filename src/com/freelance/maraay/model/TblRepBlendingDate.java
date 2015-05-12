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
@Table(name = "tbl_rep_blending_date")
@NamedQueries({
    @NamedQuery(name = "TblRepBlendingDate.findAll", query = "SELECT t FROM TblRepBlendingDate t")})
public class TblRepBlendingDate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blendingDateId;
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double total;
    private Integer isEdit = 0;
    private User byUserId;
    private Direction directionId;
    private List<TblRepBlendingValue> tblRepBlendingValueList;

    public TblRepBlendingDate() {
    }

    public TblRepBlendingDate(Long blendingDateId) {
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

    @Column(name = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Column(name = "is_edit")
    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    @JoinColumn(name = "by_user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public User getByUserId() {
        return byUserId;
    }

    public void setByUserId(User byUserId) {
        this.byUserId = byUserId;
    }

    @JoinColumn(name = "direction_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Direction getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Direction directionId) {
        this.directionId = directionId;
    }

    @OneToMany(mappedBy = "blendingDateId", fetch = FetchType.LAZY)
    public List<TblRepBlendingValue> getTblRepBlendingValueList() {
        return tblRepBlendingValueList;
    }

    public void setTblRepBlendingValueList(List<TblRepBlendingValue> tblRepBlendingValueList) {
        this.tblRepBlendingValueList = tblRepBlendingValueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blendingDateId != null ? blendingDateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepBlendingDate)) {
            return false;
        }
        TblRepBlendingDate other = (TblRepBlendingDate) object;
        if ((this.blendingDateId == null && other.blendingDateId != null) || (this.blendingDateId != null && !this.blendingDateId.equals(other.blendingDateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepBlendingDate[ blendingDateId=" + blendingDateId + " ]";
    }

}
