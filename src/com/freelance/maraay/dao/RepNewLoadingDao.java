package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.model.TblRepNewLoadingDate;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepNewLoadingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepNewLoadingDao uniqueInstance;

	private RepNewLoadingDao() {

	}

	public static RepNewLoadingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepNewLoadingDao();
		}
		return uniqueInstance;
	}

	public TblRepNewLoadingDate findByDate(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepNewLoadingDate.class);
			criteria.setFetchMode("tblRepNewLoadingValueList", FetchMode.JOIN);
			criteria.createAlias("tblRepNewLoadingValueList.productId",
					"productId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepNewLoadingDate newLoadingDate = (TblRepNewLoadingDate) criteria
					.uniqueResult();
			tx.commit();
			return newLoadingDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
