package com.freelance.maraay.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
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

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.LuDirRepDrivDao;
import com.freelance.maraay.dao.RepFirstLoadingDao;
import com.freelance.maraay.dao.RepLastLoadingDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.TblLuDirRepDriv;
import com.freelance.maraay.model.TblRepFirstTimeDate;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class RepFirst implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RepLastLoadingDao lastLoadingDao = RepLastLoadingDao.getInstance();
	private TblRepLastTimeDate lastTimeDate = new TblRepLastTimeDate();
	private TblLuDirRepDriv dirRepDriv = new TblLuDirRepDriv();
	Utils utils = Utils.getInstance();


	private Integer directioId;
	private Date accountDate;


	

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public Integer getDirectioId() {
		return directioId;
	}

	public void setDirectioId(Integer directioId) {
		this.directioId = directioId;
	}
	

	public TblRepLastTimeDate getLastTimeDate() {
		lastTimeDate = lastLoadingDao.findByDate(Utils.getInstance()
				.decrementDate(accountDate), directioId);
		return lastTimeDate;
	}

	
	public TblLuDirRepDriv getDirRepDriv() {
		dirRepDriv = LuDirRepDrivDao.getInstance().findByDateِAndDir(accountDate, directioId);
		return dirRepDriv;
	}
	
	
	public String insertNewFirst() {
		Session session = null;
		Transaction tx = null;
		try {
			TblRepFirstTimeDate firstTimeDate = new TblRepFirstTimeDate();
			List<TblRepFirstTimeValue> values = new ArrayList<TblRepFirstTimeValue>();
			firstTimeDate.setByUserId(lastTimeDate.getByUserId());
			firstTimeDate.setDate(accountDate);
			firstTimeDate.setDirectionId(lastTimeDate.getDirectionId());
			firstTimeDate.setTotal(lastTimeDate.getTotal());

			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			for (TblRepLastTimeValue lastValue : lastTimeDate
					.getTblRepLastTimeValueList()) {
				TblRepFirstTimeValue firstValue = new TblRepFirstTimeValue();
				firstValue.setProductId(lastValue.getProductId());
				firstValue.setMaxMount(lastValue.getMaxMount());
				firstValue.setMaxMountPrice(lastValue.getMaxMountPrice());
				firstValue.setMinMount(lastValue.getMinMount());
				firstValue.setMinMountPrice(lastValue.getMinMountPrice());
				firstValue.setTotalPrice(lastValue.getTotalPrice());
				firstValue.setShowenMount(lastValue.getShowenMount());
				firstValue.setFirstTimeDateId(firstTimeDate);
				values.add(firstValue);
				session.persist(firstValue);
			}
			session.persist(firstTimeDate);
			tx.commit();
			loginBean.setRepDailyDate(accountDate);
			loginBean.setRepDirectionId(directioId);
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

		return "newNLoad";

	}
	
	
	/********************* update logic **********************/

	private TblRepLastTimeDate updatedLastDate;
	private TblLuDirRepDriv updateDirRepDriv = new TblLuDirRepDriv();
	
	public TblLuDirRepDriv getUpdateDirRepDriv() {
		updateDirRepDriv = LuDirRepDrivDao.getInstance().
				findByDateِAndDir(loginBean.getUpdateRepDailyDate(), loginBean.getUpdateRepDirectionId());
		return updateDirRepDriv;
	}


	public TblRepLastTimeDate getUpdatedLastDate() {
		return updatedLastDate;
	}

	public void setUpdatedLastDate(TblRepLastTimeDate updatedLastDate) {
		this.updatedLastDate = updatedLastDate;
	}

	public void prerender(ComponentSystemEvent event) {
		TblRepLastTimeDate searchedLastDate = RepLastLoadingDao.getInstance()
				.findByDate(utils.decrementDate(loginBean.getUpdateRepDailyDate()) , loginBean.getUpdateRepDirectionId());
		if (searchedLastDate == null) {
			utils.sendRedirect(Constants.loginPage, false);
		} else {
			setUpdatedLastDate(searchedLastDate);
		}
	}
	
	
	public String updateFirst() {
		Session session = null;
		Transaction tx = null;
		try {
			TblRepFirstTimeDate firstTimeDate = RepFirstLoadingDao.getInstance().findByDate(loginBean.getUpdateRepDailyDate(), loginBean.getUpdateRepDirectionId());
			firstTimeDate.setByUserId(updatedLastDate.getByUserId());
			firstTimeDate.setDate(loginBean.getUpdateRepDailyDate());
			firstTimeDate.setDirectionId(updatedLastDate.getDirectionId());
			firstTimeDate.setTotal(updatedLastDate.getTotal());

			session = SessionFactoryUtil.getSession();
			for (TblRepLastTimeValue lastValue : updatedLastDate
					.getTblRepLastTimeValueList()) {
				TblRepFirstTimeValue firstValue = RepFirstLoadingDao.getInstance().findByDateAndProduct(firstTimeDate.getFirstTimeDateId(), lastValue.getProductId(), new Direction(loginBean.getUpdateRepDirectionId()));
				firstValue.setProductId(lastValue.getProductId());
				firstValue.setMaxMount(lastValue.getMaxMount());
				firstValue.setMaxMountPrice(lastValue.getMaxMountPrice());
				firstValue.setMinMount(lastValue.getMinMount());
				firstValue.setMinMountPrice(lastValue.getMinMountPrice());
				firstValue.setTotalPrice(lastValue.getTotalPrice());
				firstValue.setShowenMount(lastValue.getShowenMount());
				firstValue.setFirstTimeDateId(firstTimeDate);
				session = SessionFactoryUtil.getSession();
				session.update(firstValue);
			}
			session = SessionFactoryUtil.getSession();
			session.update(firstTimeDate);
			tx = session.beginTransaction();
			tx.commit();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
		return "updateNewNLoading";
	}
	

	
//	
//	public void validateDrivRep(ComponentSystemEvent event) throws ParseException {
//		// get access to resource bundle
//		String baseName = "messages";
//		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
//				.getCurrentInstance().getViewRoot().getLocale());
//		String duplicatedDateMsg = bundle.getString("NODRIVANDREP");
//		
//
//		FacesContext fc = FacesContext.getCurrentInstance();
//		UIComponent components = event.getComponent();
//		
//	
//				UIInput uiInputmsg= (UIInput) components
//						.findComponent("drivText");
////				String msgs = uiInputmsg.getLocalValue() == null ? ""
////						: uiInputmsg.getLocalValue().toString();
//				String msgId = uiInputmsg.getClientId();
//		// Let required="true" do its job.
//
//			FacesMessage msg = new FacesMessage(duplicatedDateMsg);
//			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			fc.addMessage(msgId, msg);
//			fc.renderResponse();
//	}
	
	
	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPLICATEDATE");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");
	    String NOLASTMSG = bundle.getString("NOLASTMSG");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();
		
		// get direction
		UIInput uiInputDir = (UIInput) components.findComponent("customerDirection");
		Integer dir = (Integer) uiInputDir.getLocalValue();
		

		// get Date
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblRepFirstTimeDate searchedDate = RepFirstLoadingDao
				.getInstance().findByDateWithNoJoins(todayDate, dir);
		
		TblRepLastTimeDate lastTimeDate = lastLoadingDao.findByDateNoJoins(Utils.getInstance()
				.decrementDate(todayDate), dir);

		// Let required="true" do its job.
		if (todayDate == null) {
			return;
		}

		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();

		}
		
		if (lastTimeDate == null) {
			FacesMessage msg = new FacesMessage(NOLASTMSG);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
		if (todayDate.after(today)) {
			FacesMessage msg = new FacesMessage(InvalidDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}
	
	public void validateUpdatedDate(ComponentSystemEvent event)
			throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String NOTFOUNDDateMsg = bundle.getString("UPDATEDDATENOTFOUND");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();
		
		// get direction
		UIInput uiInputDir = (UIInput) components.findComponent("customerDirection");
		Integer dir = (Integer) uiInputDir.getLocalValue();

		// get date 
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblRepFirstTimeDate searchedDate = RepFirstLoadingDao.getInstance()
				.findByDateWithNoJoins(todayDate , dir);

		// Let required="true" do its job.
		if (todayDate == null) {
			return;
		}

		if (searchedDate == null) {
			FacesMessage msg = new FacesMessage(NOTFOUNDDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();

		}

		if (todayDate.after(today)) {
			FacesMessage msg = new FacesMessage(InvalidDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}
}
