package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.freelance.maraay.model.EmployeeType;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.Utils;
import com.freelance.maraay.dao.EmployeeTypeDao;

@ManagedBean
@ViewScoped
public class EmployeeTypeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EmployeeTypeDao employeeTypeDao = EmployeeTypeDao.getInstance();
	private List<EmployeeType> emlployeeTypes;
	private EmployeeType employeeType = new EmployeeType();
	private EmployeeType editedJob = new EmployeeType();
	private String jobIdString;

	public String getJobIdString() {
		return jobIdString;
	}

	public void setJobIdString(String jobIdString) {
		if (jobIdString == null || !Utils.getInstance().isInt(jobIdString)) {
			Utils.getInstance().sendRedirect(Constants.errorPage, false);
		} else {
			this.jobIdString = jobIdString;
		}
	}

	public EmployeeType getEditedJob() {
		return editedJob;
	}

	public void setEditedJob(EmployeeType editedJob) {
		this.editedJob = editedJob;
	}

	public List<EmployeeType> getEmlployeeTypes() {
		emlployeeTypes = employeeTypeDao.listEmployeeTypes();
		return emlployeeTypes;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	public void prerendre(ComponentSystemEvent event) {

		EmployeeType job = employeeTypeDao.findById(Integer
				.parseInt(getJobIdString()));
		setEditedJob(job);
	}

	public String addNewJob() {
		try {
			employeeTypeDao.insertNewJob(employeeType);
			employeeType = new EmployeeType();
			return "allJobs";
		} catch (Exception e) {
			return "fail";
		}
	}
	
	public String deleteJob(EmployeeType delEmployeeType) {
		try {
			employeeTypeDao.deleteJob(delEmployeeType);
			return "allJobs";
		} catch (Exception e) {
			return "fail";
		}
	}

	public String editJob() {
		try {
			employeeTypeDao.updateJob(editedJob);
			return "allJobs";
		} catch (Exception e) {
			return "fail";
		}
	}

	public void validateJob(ComponentSystemEvent event) {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String jobMessage = bundle.getString("VALIDATEJOBNAME");

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputJob = (UIInput) components.findComponent("jobName");
		String job = uiInputJob.getLocalValue() == null ? "" : uiInputJob
				.getLocalValue().toString();
		String jobId = uiInputJob.getClientId();

		EmployeeType retrievedJob = employeeTypeDao.findByName(job);
		// Let required="true" do its job.
		if (job.isEmpty()) {
			return;
		}

		if (retrievedJob != null) {
			FacesMessage msg = new FacesMessage(jobMessage);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(jobId, msg);
			fc.renderResponse();
		}

	}

	public void validateEditedJob(ComponentSystemEvent event) {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String jobMessage = bundle.getString("VALIDATEJOBNAME");

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputJob = (UIInput) components.findComponent("jobName");
		String job = uiInputJob.getLocalValue() == null ? "" : uiInputJob
				.getLocalValue().toString();
		String jobId = uiInputJob.getClientId();

		EmployeeType editedRetrivedJob = null;
		if (editedJob != null) {
			editedRetrivedJob = employeeTypeDao.findByNameAndId(job,
					editedJob.getId());
		}

		// Let required="true" do its job.
		if (job.isEmpty()) {
			return;
		}

		if (editedRetrivedJob != null) {
			FacesMessage msg = new FacesMessage(jobMessage);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(jobId, msg);
			fc.renderResponse();
		}

	}
}
