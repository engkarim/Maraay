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
@Table(name = "tbl_rep_new_loading_date")
@NamedQueries({
    @NamedQuery(name = "TblRepNewLoadingDate.findAll", query = "SELECT t FROM TblRepNewLoadingDate t")})
public class TblRepNewLoadingDate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long newLoadingDateId;
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double total;
    private Integer isEdit = 0;
    private User byUserId;
    private Direction directionId;
    private List<TblRepNewLoadingValue> tblRepNewLoadingValueList;

    public TblRepNewLoadingDate() {
    }

    public TblRepNewLoadingDate(Long newLoadingDateId) {
        this.newLoadingDateId = newLoadingDateId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "new_loading_date_id")
    public Long getNewLoadingDateId() {
        return newLoadingDateId;
    }

    public void setNewLoadingDateId(Long newLoadingDateId) {
        this.newLoadingDateId = newLoadingDateId;
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

    @OneToMany(mappedBy = "newLoadingDateId", fetch = FetchType.LAZY)
    public List<TblRepNewLoadingValue> getTblRepNewLoadingValueList() {
        return tblRepNewLoadingValueList;
    }

    public void setTblRepNewLoadingValueList(List<TblRepNewLoadingValue> tblRepNewLoadingValueList) {
        this.tblRepNewLoadingValueList = tblRepNewLoadingValueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (newLoadingDateId != null ? newLoadingDateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepNewLoadingDate)) {
            return false;
        }
        TblRepNewLoadingDate other = (TblRepNewLoadingDate) object;
        if ((this.newLoadingDateId == null && other.newLoadingDateId != null) || (this.newLoadingDateId != null && !this.newLoadingDateId.equals(other.newLoadingDateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepNewLoadingDate[ newLoadingDateId=" + newLoadingDateId + " ]";
    }

}
