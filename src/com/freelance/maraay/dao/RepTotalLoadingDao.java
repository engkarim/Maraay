package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComBlendingDate;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.model.TblRepTotalLoadingDate;
import com.freelance.maraay.model.TblRepTotalLoadingValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepTotalLoadingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepTotalLoadingDao uniqueInstance;

	private RepTotalLoadingDao() {

	}

	public static RepTotalLoadingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepTotalLoadingDao();
		}
		return uniqueInstance;
	}

	public TblRepTotalLoadingDate findByDate(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepTotalLoadingDate.class);
			criteria.setFetchMode("tblRepTotalLoadingValueList", FetchMode.JOIN);
			criteria.createAlias("tblRepTotalLoadingValueList.productId",
					"productId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepTotalLoadingDate totalLoadingDate = (TblRepTotalLoadingDate) criteria
					.uniqueResult();
			tx.commit();
			return totalLoadingDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblRepTotalLoadingValue findByDateAndProduct(Date date,
			Product product, Direction directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepTotalLoadingValue.class);
			criteria.createAlias("totalLoadingDateId", "totalLoadingDateId");
			criteria.createAlias("totalLoadingDateId.directionId",
					"directionId");
			criteria.add(Restrictions.eq("productId", product));

			criteria.add(Restrictions.eq(
					"totalLoadingDateId.date", date));
			criteria.add(Restrictions.eq("totalLoadingDateId.directionId",
					directionId));
			TblRepTotalLoadingValue value = (TblRepTotalLoadingValue) criteria
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
