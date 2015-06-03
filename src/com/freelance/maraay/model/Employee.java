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

import org.hibernate.annotations.Proxy;

/**
 * 
 * @author karim
 */
@Entity
@Table(name = "employee")
@NamedQueries({ @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e") })
@Proxy(lazy = false)
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String address;
	private String mobile;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	private Double basicSalary;
	private String nationalId;
	private EmployeeType employeeType = new EmployeeType();
	private Date employment_date;
	private Date departure_date;
	private List<TblLuDirRepDriv> tblLuDirRepDrivList;
	private List<TblLuDirRepDriv> tblLuDirRepDrivList1;

	public Employee() {
	}

	public Employee(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "basic_salary")
	public Double getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(Double basicSalary) {
		this.basicSalary = basicSalary;
	}

	@Column(name = "national_id")
	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public Date getEmployment_date() {
		return employment_date;
	}

	public void setEmployment_date(Date employment_date) {
		this.employment_date = employment_date;
	}

	public Date getDeparture_date() {
		return departure_date;
	}

	public void setDeparture_date(Date departure_date) {
		this.departure_date = departure_date;
	}

	@JoinColumn(name = "employee_type", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	@OneToMany(mappedBy = "drivId", fetch = FetchType.LAZY)
	public List<TblLuDirRepDriv> getTblLuDirRepDrivList() {
		return tblLuDirRepDrivList;
	}

	public void setTblLuDirRepDrivList(List<TblLuDirRepDriv> tblLuDirRepDrivList) {
		this.tblLuDirRepDrivList = tblLuDirRepDrivList;
	}

	@OneToMany(mappedBy = "repId", fetch = FetchType.LAZY)
	public List<TblLuDirRepDriv> getTblLuDirRepDrivList1() {
		return tblLuDirRepDrivList1;
	}

	public void setTblLuDirRepDrivList1(
			List<TblLuDirRepDriv> tblLuDirRepDrivList1) {
		this.tblLuDirRepDrivList1 = tblLuDirRepDrivList1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Employee)) {
			return false;
		}
		Employee other = (Employee) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "maraay.Employee[ id=" + id + " ]";
	}

}
