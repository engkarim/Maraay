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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author karim
 */
@Entity
@Table(name = "tbl_com_calculation_equation")
@NamedQueries({
    @NamedQuery(name = "TblComCalculationEquation.findAll", query = "SELECT t FROM TblComCalculationEquation t")})
public class TblComCalculationEquation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long calcEquId;
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double totalBefore;
    private Double totalAfter;
    private Double totalSupplyValue;
    private Double oldSupplyValue;
    private Double minPlus;
    private Double discountValue;
    private List<TblComSupplyValue> tblComSupplyValueList;

    public TblComCalculationEquation() {
    }

    public TblComCalculationEquation(Long calcEquId) {
        this.calcEquId = calcEquId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calc_equ_id")
    public Long getCalcEquId() {
        return calcEquId;
    }

    public void setCalcEquId(Long calcEquId) {
        this.calcEquId = calcEquId;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "total_before")
    public Double getTotalBefore() {
        return totalBefore;
    }

    public void setTotalBefore(Double totalBefore) {
        this.totalBefore = totalBefore;
    }

    @Column(name = "total_after")
    public Double getTotalAfter() {
        return totalAfter;
    }

    public void setTotalAfter(Double totalAfter) {
        this.totalAfter = totalAfter;
    }

    @Column(name = "total_supply_value")
    public Double getTotalSupplyValue() {
        return totalSupplyValue;
    }

    public void setTotalSupplyValue(Double totalSupplyValue) {
        this.totalSupplyValue = totalSupplyValue;
    }

    @Column(name = "old_supply_value")
    public Double getOldSupplyValue() {
        return oldSupplyValue;
    }

    public void setOldSupplyValue(Double oldSupplyValue) {
        this.oldSupplyValue = oldSupplyValue;
    }

    @Column(name = "min_plus")
    public Double getMinPlus() {
        return minPlus;
    }

    public void setMinPlus(Double minPlus) {
        this.minPlus = minPlus;
    }

    @Column(name = "discount_value")
    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    @OneToMany(mappedBy = "calcEquId", fetch = FetchType.LAZY)
    public List<TblComSupplyValue> getTblComSupplyValueList() {
        return tblComSupplyValueList;
    }

    public void setTblComSupplyValueList(List<TblComSupplyValue> tblComSupplyValueList) {
        this.tblComSupplyValueList = tblComSupplyValueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calcEquId != null ? calcEquId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblComCalculationEquation)) {
            return false;
        }
        TblComCalculationEquation other = (TblComCalculationEquation) object;
        if ((this.calcEquId == null && other.calcEquId != null) || (this.calcEquId != null && !this.calcEquId.equals(other.calcEquId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblComCalculationEquation[ calcEquId=" + calcEquId + " ]";
    }

}
