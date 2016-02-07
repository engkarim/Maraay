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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tbl_rep_rat_blending")
@NamedQueries({
    @NamedQuery(name = "TblRepRatBlending.findAll", query = "SELECT t FROM TblRepRatBlending t")})
public class TblRepRatBlending implements Serializable {
    private static final long serialVersionUID = 1L;
  
    private Long ratBlending;
    private Date fromDate;
    private Date toDate;
    private Integer isEdit;
    private Direction direction;
    private Employee representative;
    private User byUser;

    public TblRepRatBlending() {
    }

    public TblRepRatBlending(Long ratBlending) {
        this.ratBlending = ratBlending;
    }
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rat_blending")
    public Long getRatBlending() {
        return ratBlending;
    }

    public void setRatBlending(Long ratBlending) {
        this.ratBlending = ratBlending;
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

@Column(name = "is_edit")
    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
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
        hash += (ratBlending != null ? ratBlending.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepRatBlending)) {
            return false;
        }
        TblRepRatBlending other = (TblRepRatBlending) object;
        if ((this.ratBlending == null && other.ratBlending != null) || (this.ratBlending != null && !this.ratBlending.equals(other.ratBlending))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepRatBlending[ ratBlending=" + ratBlending + " ]";
    }
    
}
