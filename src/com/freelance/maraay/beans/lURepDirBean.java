package com.freelance.maraay.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.freelance.maraay.dao.LuDirRepDrivDao;
import com.freelance.maraay.model.TblLuDirRepDriv;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class lURepDirBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TblLuDirRepDriv dirRepDriv = new TblLuDirRepDriv();
	private List<TblLuDirRepDriv> dirRepDrivList;
	private List<TblLuDirRepDriv> filteredDirRepDrivList;
	Utils utils = Utils.getInstance();

	public List<TblLuDirRepDriv> getFilteredDirRepDrivList() {
		return filteredDirRepDrivList;
	}

	public void setFilteredDirRepDrivList(
			List<TblLuDirRepDriv> filteredDirRepDrivList) {
		this.filteredDirRepDrivList = filteredDirRepDrivList;
	}

	public List<TblLuDirRepDriv> getDirRepDrivList() {
		dirRepDrivList = LuDirRepDrivDao.getInstance().listAll();
		return dirRepDrivList;
	}

	public TblLuDirRepDriv getDirRepDriv() {
		return dirRepDriv;
	}

	public void setDirRepDriv(TblLuDirRepDriv dirRepDriv) {
		this.dirRepDriv = dirRepDriv;
	}

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String distRep() {
		try {
//			System.out.println(".... " + dirRepDriv.getDrivId().getId()
//					+ " .......... " + dirRepDriv.getRepId().getId());
//
//			if (dirRepDriv.getDrivId().getId() == 0) {
//				dirRepDriv.setDrivId();
//			}
//
//			if (dirRepDriv.getRepId().getId() == 0) {
//				dirRepDriv.setRepId(null);
//			}
			dirRepDriv.setByUserId(new User(loginBean.getId()));
			LuDirRepDrivDao.getInstance().insertNew(dirRepDriv);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	/************* update logic *******************/
	private String passedParam;
	private Long dirRepDrivId;
	private TblLuDirRepDriv updatedDirRepDriv = new TblLuDirRepDriv();

	public String getPassedParam() {
		return passedParam;
	}

	public void setPassedParam(String passedParam) {
		if (passedParam != null) {
			if (utils.isLong(passedParam)) {
				setDirRepDrivId(Long.parseLong(passedParam));
			} else {
				utils.sendRedirect(Constants.errorPage, false);
			}
		} else {
			utils.sendRedirect(Constants.errorPage, false);
		}
	}

	public Long getDirRepDrivId() {
		return dirRepDrivId;
	}

	public void setDirRepDrivId(Long dirRepDrivId) {
		if (dirRepDrivId == 0) {
			utils.sendRedirect(Constants.errorPage, false);
		}
		this.dirRepDrivId = dirRepDrivId;
	}

	public TblLuDirRepDriv getUpdatedDirRepDriv() {
		return updatedDirRepDriv;
	}

	public void setUpdatedDirRepDriv(TblLuDirRepDriv updatedDirRepDriv) {
		this.updatedDirRepDriv = updatedDirRepDriv;
	}

	public void prerender(ComponentSystemEvent event) {
		TblLuDirRepDriv searchedDirRepDriv = LuDirRepDrivDao.getInstance()
				.findById(dirRepDrivId);
		if (searchedDirRepDriv == null) {
			utils.sendRedirect(Constants.errorPage, false);
		} else {
			setUpdatedDirRepDriv(searchedDirRepDriv);
		}

	}

	public String updateDirRepDriv() {
		try {
			updatedDirRepDriv.setByUserId(new User(loginBean.getId()));
			LuDirRepDrivDao.getInstance().updateRecord(updatedDirRepDriv);
			return "repAllDirRepDriv";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

	}

	/**************** validation logic **********************/
	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPDATEANDDIR");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		UIInput uiInputDir = (UIInput) components.findComponent("dirctionName");
		Integer dirId = (Integer) uiInputDir.getLocalValue();
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		TblLuDirRepDriv searchedDate = null;
		if (todayDate != null && dirId != null) {
			searchedDate = LuDirRepDrivDao.getInstance().findByDateِAndDir(
					todayDate, dirId);
		}

		// Let required="true" do its job.
		if (todayDate == null || dirId == null) {
			return;
		}

		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();

		}
	}

	public void validateRep(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedRepMsg = bundle.getString("DUPLICATEREP");
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputRep = (UIInput) components.findComponent("repName");
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Integer repId = (Integer) uiInputRep.getLocalValue();
		Date today = (Date) uiInputDate.getLocalValue();
		String repInputId = uiInputRep.getClientId();

		TblLuDirRepDriv searchedDate = null;

		if (repId != null && today != null) {
			searchedDate = LuDirRepDrivDao.getInstance().findRepById(repId,
					today);
		}

		// Let required="true" do its job.
		if (repId == null || today == null) {
			return;
		}

		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedRepMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(repInputId, msg);
			fc.renderResponse();

		}
	}

	public void validateDriv(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDrivMsg = bundle.getString("DUPLICATEDRIV");
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		UIInput uiInputDriv = (UIInput) components.findComponent("drivName");
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Integer drivId = (Integer) uiInputDriv.getLocalValue();
		Date today = (Date) uiInputDate.getLocalValue();
		String drivInputId = uiInputDriv.getClientId();
		TblLuDirRepDriv searchedDate = null;
		if (drivId != null && today != null) {
			searchedDate = LuDirRepDrivDao.getInstance().findDrivById(drivId,
					today);
		}

		// Let required="true" do its job.
		if (drivId == null || today == null) {
			return;
		}

		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedDrivMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(drivInputId, msg);
			fc.renderResponse();

		}

	}

	/**************** update validation logic **********************/
	public void validateDateUpdate(ComponentSystemEvent event)
			throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPDATEANDDIR");
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();
		// get password
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		UIInput uiInputDir = (UIInput) components.findComponent("dirctionName");
		Integer dirId = (Integer) uiInputDir.getLocalValue();
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();
		TblLuDirRepDriv searchedDate = null;
		if (todayDate != null && dirId != null) {
			searchedDate = LuDirRepDrivDao.getInstance()
					.findByDateِAndDirUpdate(todayDate, dirId,
							updatedDirRepDriv.getDirRepDrivId());
		}
		// Let required="true" do its job.
		if (todayDate == null || dirId == null) {
			return;
		}
		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}

	public void validateRepUpdate(ComponentSystemEvent event)
			throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedRepMsg = bundle.getString("DUPLICATEREP");
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputRep = (UIInput) components.findComponent("repName");
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Integer repId = (Integer) uiInputRep.getLocalValue();
		Date today = (Date) uiInputDate.getLocalValue();
		String repInputId = uiInputRep.getClientId();

		TblLuDirRepDriv searchedDate = null;

		if (repId != null) {
			searchedDate = LuDirRepDrivDao.getInstance().findRepByIdUpdate(
					repId, updatedDirRepDriv.getDate(),
					updatedDirRepDriv.getDirRepDrivId());
		}
		// Let required="true" do its job.
		if (repId == null) {
			return;
		}

		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedRepMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(repInputId, msg);
			fc.renderResponse();

		}
	}

	public void validateDrivUpdate(ComponentSystemEvent event)
			throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDrivMsg = bundle.getString("DUPLICATEDRIV");
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		UIInput uiInputDriv = (UIInput) components.findComponent("drivName");
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Integer drivId = (Integer) uiInputDriv.getLocalValue();
		Date today = (Date) uiInputDate.getLocalValue();
		String drivInputId = uiInputDriv.getClientId();
		TblLuDirRepDriv searchedDate = null;
		if (drivId != null) {
			searchedDate = LuDirRepDrivDao.getInstance().findDrivByIdUpdate(
					drivId, updatedDirRepDriv.getDate(),
					updatedDirRepDriv.getDirRepDrivId());
		}
		// Let required="true" do its job.
		if (drivId == null) {
			return;
		}
		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedDrivMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(drivInputId, msg);
			fc.renderResponse();
		}
	}
}
