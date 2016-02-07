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
@Table(name = "tbl_rep_rat_visiting")
@NamedQueries({
    @NamedQuery(name = "TblRepRatVisiting.findAll", query = "SELECT t FROM TblRepRatVisiting t")})
public class TblRepRatVisiting implements Serializable {
    private static final long serialVersionUID = 1L;
  
    private Long visitingId;
    private Date date;
    private Integer visitingCount;
    private Integer isEdited;
    private Direction direction;
    private Employee representative;
    private User byUser;

    public TblRepRatVisiting() {
    }

    public TblRepRatVisiting(Long visitingId) {
        this.visitingId = visitingId;
    }
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "visiting_id")
    public Long getVisitingId() {
        return visitingId;
    }

    public void setVisitingId(Long visitingId) {
        this.visitingId = visitingId;
    }

@Column(name = "date")
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

@Column(name = "visiting_count")
    public Integer getVisitingCount() {
        return visitingCount;
    }

    public void setVisitingCount(Integer visitingCount) {
        this.visitingCount = visitingCount;
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
        hash += (visitingId != null ? visitingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepRatVisiting)) {
            return false;
        }
        TblRepRatVisiting other = (TblRepRatVisiting) object;
        if ((this.visitingId == null && other.visitingId != null) || (this.visitingId != null && !this.visitingId.equals(other.visitingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepRatVisiting[ visitingId=" + visitingId + " ]";
    }
    
}
