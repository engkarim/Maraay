package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.RowEditEvent;

import com.freelance.maraay.model.User;
import com.freelance.maraay.dao.UserDao;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Performance;

@ManagedBean
@ViewScoped
public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user = new User();
	UserDao userDao = UserDao.getInstance();
	private List<User> users;
	private List<User> filteredUsers;
	private Integer userId;
	private User updatedUser;

	public User getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void prerender(ComponentSystemEvent e) {
		User user = userDao.findById(getUserId());
		setUpdatedUser(user);
	}

	public String updateUser() {
		userDao.updateUser(updatedUser);
		return "allUsers";
	}

	@ManagedProperty("#{service}")
	private Service service;

	@PostConstruct
	public void init() {
		users = service.findAllUsers();
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String addUser() {
		try {
			userDao.insertuser(user);
			return "allUsers";

		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}

	public String deleteUser(User user) {
		try {

			userDao.deleteUser(user);
			return "allUsers";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}

	public void validatePassword(ComponentSystemEvent event) {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String passwordMessages = bundle.getString("VALIDATEMATCHEDPASSWORD");

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputPassword = (UIInput) components
				.findComponent("userPassword");
		String password = uiInputPassword.getLocalValue() == null ? ""
				: uiInputPassword.getLocalValue().toString();
		String passwordId = uiInputPassword.getClientId();

		// get confirm password
		UIInput uiInputConfirmPassword = (UIInput) components
				.findComponent("confirmPassword");
		String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
				: uiInputConfirmPassword.getLocalValue().toString();

		// Let required="true" do its job.
		if (password.isEmpty() || confirmPassword.isEmpty()) {
			return;
		}

		if (!password.equals(confirmPassword)) {

			FacesMessage msg = new FacesMessage(passwordMessages);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(passwordId, msg);
			fc.renderResponse();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			userDao.updateUser((User) event.getObject());

		} catch (Exception e) {

		} finally {
			Performance.releaseMemory();
		}
	}

	public void onRowCancel(RowEditEvent event) {
	}

}
