/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;
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
@Table(name = "pay_type")
@NamedQueries({
    @NamedQuery(name = "PayType.findAll", query = "SELECT p FROM PayType p")})
public class PayType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer payTypeId;
    private String payType;
    private List<TblComSupplyValue> tblComSupplyValueList;

    public PayType() {
    }

    public PayType(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pay_type_id")
    public Integer getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    @Column(name = "pay_type")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @OneToMany(mappedBy = "payTypeId", fetch = FetchType.LAZY)
    public List<TblComSupplyValue> getTblComSupplyValueList() {
        return tblComSupplyValueList;
    }

    public void setTblComSupplyValueList(List<TblComSupplyValue> tblComSupplyValueList) {
        this.tblComSupplyValueList = tblComSupplyValueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payTypeId != null ? payTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayType)) {
            return false;
        }
        PayType other = (PayType) object;
        if ((this.payTypeId == null && other.payTypeId != null) || (this.payTypeId != null && !this.payTypeId.equals(other.payTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.PayType[ payTypeId=" + payTypeId + " ]";
    }

}
