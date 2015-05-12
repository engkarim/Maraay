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
import com.freelance.maraay.model.TblRepDefectValue;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepLastLoadingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepLastLoadingDao uniqueInstance;

	private RepLastLoadingDao() {

	}

	public static RepLastLoadingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepLastLoadingDao();
		}
		return uniqueInstance;
	}

	public TblRepLastTimeDate findByDate(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepLastTimeDate.class);
			criteria.setFetchMode("tblRepLastTimeValueList", FetchMode.JOIN);
			criteria.createAlias("tblRepLastTimeValueList.productId",
					"productId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepLastTimeDate lastTimeDate = (TblRepLastTimeDate) criteria
					.uniqueResult();
			tx.commit();
			return lastTimeDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblRepLastTimeValue findByDateAndProduct(Date date, Product product,
			Direction directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepLastTimeValue.class);
			criteria.createAlias("lastTimeDateId", "lastTimeDateId");
			criteria.createAlias("lastTimeDateId.directionId", "directionId");
			criteria.add(Restrictions.eq("productId", product));

			criteria.add(Restrictions.eq("lastTimeDateId.date", date));
			criteria.add(Restrictions.eq("lastTimeDateId.directionId",
					directionId));
			TblRepLastTimeValue value = (TblRepLastTimeValue) criteria
					.uniqueResult();
			tx.commit();
			return value;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
