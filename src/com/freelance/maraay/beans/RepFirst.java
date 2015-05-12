package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.RepLastLoadingDao;
import com.freelance.maraay.model.TblRepFirstTimeDate;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
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
}
