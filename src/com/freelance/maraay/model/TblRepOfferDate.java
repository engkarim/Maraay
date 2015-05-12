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
@Table(name = "tbl_rep_offer_date")
@NamedQueries({
    @NamedQuery(name = "TblRepOfferDate.findAll", query = "SELECT t FROM TblRepOfferDate t")})
public class TblRepOfferDate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long offerDateId;
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double total;
    private Integer isEdit = 0;
    private User byUserId;
    private Direction directionId;
    private List<TblRepOfferValue> tblRepOfferValueList;

    public TblRepOfferDate() {
    }

    public TblRepOfferDate(Long offerDateId) {
        this.offerDateId = offerDateId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "offer_date_id")
    public Long getOfferDateId() {
        return offerDateId;
    }

    public void setOfferDateId(Long offerDateId) {
        this.offerDateId = offerDateId;
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

    @OneToMany(mappedBy = "offerDateId", fetch = FetchType.LAZY)
    public List<TblRepOfferValue> getTblRepOfferValueList() {
        return tblRepOfferValueList;
    }

    public void setTblRepOfferValueList(List<TblRepOfferValue> tblRepOfferValueList) {
        this.tblRepOfferValueList = tblRepOfferValueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (offerDateId != null ? offerDateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepOfferDate)) {
            return false;
        }
        TblRepOfferDate other = (TblRepOfferDate) object;
        if ((this.offerDateId == null && other.offerDateId != null) || (this.offerDateId != null && !this.offerDateId.equals(other.offerDateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepOfferDate[ offerDateId=" + offerDateId + " ]";
    }

}
