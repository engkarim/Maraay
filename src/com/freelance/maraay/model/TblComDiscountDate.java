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
@Table(name = "tbl_com_discount_date")
@NamedQueries({
    @NamedQuery(name = "TblComDiscountDate.findAll", query = "SELECT t FROM TblComDiscountDate t")})
public class TblComDiscountDate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dateId;
    private Date date;
    private User byUser;
    private List<TblComDiscountValue> tblComDiscountValueList;

    public TblComDiscountDate() {
    }

    public TblComDiscountDate(Long dateId) {
        this.dateId = dateId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "date_id")
    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JoinColumn(name = "by_user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    @OneToMany(mappedBy = "discountDate", fetch = FetchType.LAZY)
    public List<TblComDiscountValue> getTblComDiscountValueList() {
        return tblComDiscountValueList;
    }

    public void setTblComDiscountValueList(List<TblComDiscountValue> tblComDiscountValueList) {
        this.tblComDiscountValueList = tblComDiscountValueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dateId != null ? dateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblComDiscountDate)) {
            return false;
        }
        TblComDiscountDate other = (TblComDiscountDate) object;
        if ((this.dateId == null && other.dateId != null) || (this.dateId != null && !this.dateId.equals(other.dateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblComDiscountDate[ dateId=" + dateId + " ]";
    }

}
