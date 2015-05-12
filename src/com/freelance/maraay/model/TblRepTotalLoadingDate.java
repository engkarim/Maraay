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
@Table(name = "tbl_rep_total_loading_date")
@NamedQueries({
    @NamedQuery(name = "TblRepTotalLoadingDate.findAll", query = "SELECT t FROM TblRepTotalLoadingDate t")})
public class TblRepTotalLoadingDate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long totalLoadingDateId;
    private Date date;
    private Integer isEdit = 0;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double total;
    private List<TblRepTotalLoadingValue> tblRepTotalLoadingValueList;
    private User byUserId;
    private Direction directionId;

    public TblRepTotalLoadingDate() {
    }

    public TblRepTotalLoadingDate(Long totalLoadingDateId) {
        this.totalLoadingDateId = totalLoadingDateId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "total_loading_date_id")
    public Long getTotalLoadingDateId() {
        return totalLoadingDateId;
    }

    public void setTotalLoadingDateId(Long totalLoadingDateId) {
        this.totalLoadingDateId = totalLoadingDateId;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "is_edit")
    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    @Column(name = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @OneToMany(mappedBy = "totalLoadingDateId", fetch = FetchType.LAZY)
    public List<TblRepTotalLoadingValue> getTblRepTotalLoadingValueList() {
        return tblRepTotalLoadingValueList;
    }

    public void setTblRepTotalLoadingValueList(List<TblRepTotalLoadingValue> tblRepTotalLoadingValueList) {
        this.tblRepTotalLoadingValueList = tblRepTotalLoadingValueList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (totalLoadingDateId != null ? totalLoadingDateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepTotalLoadingDate)) {
            return false;
        }
        TblRepTotalLoadingDate other = (TblRepTotalLoadingDate) object;
        if ((this.totalLoadingDateId == null && other.totalLoadingDateId != null) || (this.totalLoadingDateId != null && !this.totalLoadingDateId.equals(other.totalLoadingDateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepTotalLoadingDate[ totalLoadingDateId=" + totalLoadingDateId + " ]";
    }

}
