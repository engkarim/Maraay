package com.freelance.maraay.beans;

import java.io.Serializable;
import java.text.ParseException;
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
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;

import com.freelance.maraay.dao.DirectionDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.Performance;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class DirectionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Direction direction = new Direction();
	DirectionDao directionDao = DirectionDao.getInstance();
	private List<Direction> directions;
	private List<Direction> filteredDirections;
	private String dirID;
	private Direction updateedDir = new Direction();

	@ManagedProperty("#{service}")
	private Service service;

	@PostConstruct
	public void init() {
		directions = service.findAllDirections();
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Direction> getDirections() {
		return directions;
	}

	public List<Direction> getFilteredDirections() {
		return filteredDirections;
	}

	public void setFilteredDirections(List<Direction> filteredDirections) {
		this.filteredDirections = filteredDirections;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public String getDirID() {
		return dirID;
	}

	public void setDirID(String dirID) {
		if (dirID == null || !Utils.getInstance().isInt(dirID)) {
			Utils.getInstance().sendRedirect(Constants.errorPage, false);
		} else {
			this.dirID = dirID;
		}
	}
	
	public Direction getUpdateedDir() {
		return updateedDir;
	}

	public void setUpdateedDir(Direction updateedDir) {
		this.updateedDir = updateedDir;
	}
	
	public void prerendre(ComponentSystemEvent event) {
		Direction dir = directionDao.findById(Integer.parseInt(getDirID()));
		setUpdateedDir(dir);
	}
	


	public String addDirection() {
		try {
			directionDao.insertDirection(direction);
			return "allDirections";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}public String updateDirection(){
		try {
			directionDao.updateDirection(updateedDir);
			return "allDirections";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
		
	}

	public String deleteDirection(Direction direction) {
		try {
			directionDao.deleteDirection(direction);
			return "allDirections";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}

	/******************* validation logic ****************/

	public void validateName(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedNameMsg = bundle.getString("DUPLICATENAME");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputName = (UIInput) components
				.findComponent("dirctionName");
		String name = uiInputName.getLocalValue() == null ? "" : uiInputName
				.getLocalValue().toString();

		String nameId = uiInputName.getClientId();
		Direction direction = DirectionDao.getInstance().findByName(name);

		// Let required="true" do its job.
		if (name.isEmpty()) {
			return;
		}
		if (direction != null) {
			FacesMessage msg = new FacesMessage(duplicatedNameMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(nameId, msg);
			fc.renderResponse();

		}
	}
	public void validateUpdateDirName(FacesContext context, UIComponent components,
			Object value) throws ValidatorException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String jobMessage = bundle.getString("VALIDATEDIRNAME");
		String dirName = value.toString();
		
		
		Direction direction = DirectionDao.getInstance().findByNameAndNotId(dirName, getUpdateedDir().getId());
		if (direction != null) {
			FacesMessage msg = new FacesMessage(jobMessage);
			throw new ValidatorException(msg);
		}
	}

}
