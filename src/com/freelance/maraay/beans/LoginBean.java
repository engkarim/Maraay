package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblRepFirstTimeDate;
import com.freelance.maraay.model.User;
import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.RepFirstLoadingDao;
import com.freelance.maraay.dao.UserDao;
import com.freelance.maraay.utils.AppUtils;
import com.freelance.maraay.utils.Performance;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String AUTH_KEY = "loginRole";

	private Integer id;
	private String username;
	private String password;
	private Integer userType;
	private String name;
	private Date companyDailyDate;
	private Date companyDailyUpdatedDate;
	private Date repDailyDate;
	private int repDirectionId;
	private Date updateRepDailyDate;
	private int updateRepDirectionId;
	
	public Date getUpdateRepDailyDate() {
		return updateRepDailyDate;
	}

	public void setUpdateRepDailyDate(Date updateRepDailyDate) {
		this.updateRepDailyDate = updateRepDailyDate;
	}

	public int getUpdateRepDirectionId() {
		return updateRepDirectionId;
	}

	public void setUpdateRepDirectionId(int updateRepDirectionId) {
		this.updateRepDirectionId = updateRepDirectionId;
	}



	public int getRepDirectionId() {
		return repDirectionId;
	}

	public void setRepDirectionId(int repDirectionId) {
		this.repDirectionId = repDirectionId;
	}

	public Date getRepDailyDate() {
		return repDailyDate;
	}

	public void setRepDailyDate(Date repDailyDate) {
		this.repDailyDate = repDailyDate;
	}

	public Date getCompanyDailyUpdatedDate() {
		return companyDailyUpdatedDate;
	}

	public void setCompanyDailyUpdatedDate(Date companyDailyUpdatedDate) {
		this.companyDailyUpdatedDate = companyDailyUpdatedDate;
	}

	public Date getCompanyDailyDate() {
		return companyDailyDate;
	}

	public void setCompanyDailyDate(Date companyDailyDate) {
		this.companyDailyDate = companyDailyDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String checkUser() {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = request.getSession();

			String username = this.getUsername();
			String password = this.getPassword();

			User user = new User();
			user.setUserName(username);
			user.setPassword(password);

			List<User> users = new UserDao().findByExample(user);
			System.out.println(users.size());
			if (users.size() > 0) {
				session.setAttribute(LoginBean.AUTH_KEY, users.get(0)
						.getUserType().getId().toString());
				this.setId(users.get(0).getId());
				this.setName(users.get(0).getName());
				this.setUsername(users.get(0).getUserName());
				this.setUserType(users.get(0).getUserType().getId());
				// check company account if non completed operations
				
				List<TblComDiscountDate> checkedDiscountList = ComDiscountingDao.getInstance().findByCompletedCalue(0);
			   List<TblRepFirstTimeDate> checkedFirstDate = RepFirstLoadingDao.getInstance().findByCompletedRep(0);
				
				AppUtils appUtils = new AppUtils();
			    appUtils.deleteNonCompletedCom(checkedDiscountList);
			    appUtils.deleteNonCompletedRep(checkedFirstDate);
				Performance.releaseMemory();
				return "home";
			} else {
				// get access to resource bundle
				String baseName = "messages";
				ResourceBundle bundle = ResourceBundle.getBundle(baseName,
						FacesContext.getCurrentInstance().getViewRoot()
								.getLocale());
				this.setUsername(null);
				this.setPassword(null);
				String badCredential = bundle
						.getString("ERRORUSERNAMEORPASSWORD");
				String wrongTitle = bundle.getString("ERROR");

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								badCredential, ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Performance.releaseMemory();
		}
		return null;
	}

	public String logout() {
		Map<String, Object> sessKeys = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		sessKeys.clear();
		Performance.releaseMemory();
		return "logout";
	}

}
