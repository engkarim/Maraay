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
@Table(name = "tbl_rep_rat_covering")
@NamedQueries({
    @NamedQuery(name = "TblRepRatCovering.findAll", query = "SELECT t FROM TblRepRatCovering t")})
public class TblRepRatCovering implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private Long coveringId;
    private Date fromDate;
    private Date toDate;
    private Integer coveringTarget;
    private Integer isEdited;
    private Direction direction;
    private Employee representative;
    private User byUser;

    public TblRepRatCovering() {
    }

    public TblRepRatCovering(Long coveringId) {
        this.coveringId = coveringId;
    }
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "covering_id")
    public Long getCoveringId() {
        return coveringId;
    }

    public void setCoveringId(Long coveringId) {
        this.coveringId = coveringId;
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

@Column(name = "covering_target")
    public Integer getCoveringTarget() {
        return coveringTarget;
    }

    public void setCoveringTarget(Integer coveringTarget) {
        this.coveringTarget = coveringTarget;
    }

@Column(name = "is_edited")
    public Integer getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Integer isEdited) {
        this.isEdited = isEdited;
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
        hash += (coveringId != null ? coveringId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepRatCovering)) {
            return false;
        }
        TblRepRatCovering other = (TblRepRatCovering) object;
        if ((this.coveringId == null && other.coveringId != null) || (this.coveringId != null && !this.coveringId.equals(other.coveringId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepRatCovering[ coveringId=" + coveringId + " ]";
    }
    
}
