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
import com.freelance.maraay.model.TblRepBlendingValue;
import com.freelance.maraay.model.TblRepDefectDate;
import com.freelance.maraay.model.TblRepDefectValue;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepDefectDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepDefectDao uniqueInstance;

	private RepDefectDao() {

	}

	public static RepDefectDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepDefectDao();
		}
		return uniqueInstance;
	}

	public TblRepDefectDate findByDate(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepDefectDate.class);
			criteria.setFetchMode("tblRepDefectValueList", FetchMode.JOIN);
			criteria.createAlias("tblRepDefectValueList.productId", "productId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepDefectDate defectDate = (TblRepDefectDate) criteria
					.uniqueResult();
			tx.commit();
			return defectDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblRepDefectValue findByDateAndProduct(Date date, Product product,
			Direction directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepDefectValue.class);
			criteria.createAlias("defectDateId", "defectDateId");
			criteria.createAlias("defectDateId.directionId", "directionId");
			criteria.add(Restrictions.eq("productId", product));

			criteria.add(Restrictions.eq("defectDateId.date", date));
			criteria.add(Restrictions.eq("defectDateId.directionId",
					directionId));
			TblRepDefectValue value = (TblRepDefectValue) criteria
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
