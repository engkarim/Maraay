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
import com.freelance.maraay.model.TblRepBlendingDate;
import com.freelance.maraay.model.TblRepBlendingValue;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepBlendingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepBlendingDao uniqueInstance;

	private RepBlendingDao() {

	}

	public static RepBlendingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepBlendingDao();
		}
		return uniqueInstance;
	}

	public TblRepBlendingDate findByDate(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepBlendingDate.class);
			criteria.setFetchMode("tblRepBlendingValueList", FetchMode.JOIN);
			criteria.createAlias("tblRepBlendingValueList.productId",
					"productId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepBlendingDate blendingDate = (TblRepBlendingDate) criteria
					.uniqueResult();
			tx.commit();
			return blendingDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblRepBlendingValue findByDateAndProduct(Date date,
			Product product, Direction directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepBlendingValue.class);
			criteria.createAlias("blendingDateId", "blendingDateId");
			criteria.createAlias("blendingDateId.directionId", "directionId");
			criteria.add(Restrictions.eq("productId", product));

			criteria.add(Restrictions.eq("blendingDateId.date",
					date));
			criteria.add(Restrictions.eq("blendingDateId.directionId",
					directionId));
			TblRepBlendingValue value = (TblRepBlendingValue) criteria
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
