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
@Table(name = "tbl_rep_rat_achieved")
@NamedQueries({
    @NamedQuery(name = "TblRepRatAchieved.findAll", query = "SELECT t FROM TblRepRatAchieved t")})
public class TblRepRatAchieved implements Serializable {
    private static final long serialVersionUID = 1L;
  
    private Long achieveId;
    private Date fromDate;
    private Date toDate;
    private Integer isEdit;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double targetValue;
    private Direction direction;
    private Employee representative;
    private User byUser;

    public TblRepRatAchieved() {
    }

    public TblRepRatAchieved(Long achieveId) {
        this.achieveId = achieveId;
    }
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "achieve_id")
    public Long getAchieveId() {
        return achieveId;
    }

    public void setAchieveId(Long achieveId) {
        this.achieveId = achieveId;
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

@Column(name = "target_value")
    public Double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Double targetValue) {
        this.targetValue = targetValue;
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
        hash += (achieveId != null ? achieveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRepRatAchieved)) {
            return false;
        }
        TblRepRatAchieved other = (TblRepRatAchieved) object;
        if ((this.achieveId == null && other.achieveId != null) || (this.achieveId != null && !this.achieveId.equals(other.achieveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblRepRatAchieved[ achieveId=" + achieveId + " ]";
    }
    
}
