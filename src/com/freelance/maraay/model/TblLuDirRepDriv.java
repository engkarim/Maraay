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
@Table(name = "tbl_lu_dir_rep_driv")
@NamedQueries({
    @NamedQuery(name = "TblLuDirRepDriv.findAll", query = "SELECT t FROM TblLuDirRepDriv t")})
public class TblLuDirRepDriv implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dirRepDrivId;
    private Date date;
    private User byUserId = new User();
    private Employee drivId = new Employee();
    private Employee repId = new Employee();
    private Direction directionId = new Direction();

    public TblLuDirRepDriv() {
    }

    public TblLuDirRepDriv(Long dirRepDrivId) {
        this.dirRepDrivId = dirRepDrivId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dir_rep_driv_id")
    public Long getDirRepDrivId() {
        return dirRepDrivId;
    }

    public void setDirRepDrivId(Long dirRepDrivId) {
        this.dirRepDrivId = dirRepDrivId;
    }

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JoinColumn(name = "by_user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public User getByUserId() {
        return byUserId;
    }

    public void setByUserId(User byUserId) {
        this.byUserId = byUserId;
    }

    @JoinColumn(name = "driv_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Employee getDrivId() {
        return drivId;
    }

    public void setDrivId(Employee drivId) {
        this.drivId = drivId;
    }

    @JoinColumn(name = "rep_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Employee getRepId() {
        return repId;
    }

    public void setRepId(Employee repId) {
        this.repId = repId;
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
        hash += (dirRepDrivId != null ? dirRepDrivId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblLuDirRepDriv)) {
            return false;
        }
        TblLuDirRepDriv other = (TblLuDirRepDriv) object;
        if ((this.dirRepDrivId == null && other.dirRepDrivId != null) || (this.dirRepDrivId != null && !this.dirRepDrivId.equals(other.dirRepDrivId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblLuDirRepDriv[ dirRepDrivId=" + dirRepDrivId + " ]";
    }

}
