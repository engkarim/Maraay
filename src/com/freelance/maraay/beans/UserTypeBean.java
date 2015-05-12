package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.freelance.maraay.model.UserType;
import com.freelance.maraay.dao.UserTypeDao;
import com.freelance.maraay.primeFacesService.Service;

@ManagedBean
@ViewScoped
public class UserTypeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserType userType = new UserType();
	UserTypeDao userTypeDao = UserTypeDao.getInstance();

	private List<UserType> userTypes;

	@ManagedProperty("#{service}")
	private Service service;

	@PostConstruct
	public void init() {
		userTypes = service.findAllUserTypes();
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<UserType> getUserTypes() {
		return userTypes;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
