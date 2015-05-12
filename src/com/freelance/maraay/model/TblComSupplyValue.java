/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.maraay.model;

import java.io.Serializable;
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

/**
 *
 * @author karim
 */
@Entity
@Table(name = "tbl_com_supply_value")
@NamedQueries({
    @NamedQuery(name = "TblComSupplyValue.findAll", query = "SELECT t FROM TblComSupplyValue t")})
public class TblComSupplyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long supplyValueId;
    private String senderName;
    private String recieverName;
    private String bankName;
    private String noDeposite;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double supplyValue;
    private Integer isEdited = 0;
    private Double supplyOldValue;
    private User byUserId;
    private TblComCalculationEquation calcEquId;
    private PayType payTypeId = new PayType();

    public TblComSupplyValue() {
    }

    public TblComSupplyValue(Long supplyValueId) {
        this.supplyValueId = supplyValueId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "supply_value_id")
    public Long getSupplyValueId() {
        return supplyValueId;
    }

    public void setSupplyValueId(Long supplyValueId) {
        this.supplyValueId = supplyValueId;
    }

    @Column(name = "sender_name")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Column(name = "reciever_name")
    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "no_deposite")
    public String getNoDeposite() {
        return noDeposite;
    }

    public void setNoDeposite(String noDeposite) {
        this.noDeposite = noDeposite;
    }

    @Column(name = "supply_value")
    public Double getSupplyValue() {
        return supplyValue;
    }

    public void setSupplyValue(Double supplyValue) {
        this.supplyValue = supplyValue;
    }

    @Column(name = "is_edited")
    public Integer getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Integer isEdited) {
        this.isEdited = isEdited;
    }

    @Column(name = "supply_old_value")
    public Double getSupplyOldValue() {
        return supplyOldValue;
    }

    public void setSupplyOldValue(Double supplyOldValue) {
        this.supplyOldValue = supplyOldValue;
    }

    @JoinColumn(name = "by_user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public User getByUserId() {
        return byUserId;
    }

    public void setByUserId(User byUserId) {
        this.byUserId = byUserId;
    }

    @JoinColumn(name = "calc_equ_id", referencedColumnName = "calc_equ_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TblComCalculationEquation getCalcEquId() {
        return calcEquId;
    }

    public void setCalcEquId(TblComCalculationEquation calcEquId) {
        this.calcEquId = calcEquId;
    }

    @JoinColumn(name = "pay_type_id", referencedColumnName = "pay_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public PayType getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(PayType payTypeId) {
        this.payTypeId = payTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplyValueId != null ? supplyValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblComSupplyValue)) {
            return false;
        }
        TblComSupplyValue other = (TblComSupplyValue) object;
        if ((this.supplyValueId == null && other.supplyValueId != null) || (this.supplyValueId != null && !this.supplyValueId.equals(other.supplyValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freelance.maraay.model.TblComSupplyValue[ supplyValueId=" + supplyValueId + " ]";
    }

}
