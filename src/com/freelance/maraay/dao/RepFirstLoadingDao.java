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
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblRepFirstTimeDate;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepFirstLoadingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepFirstLoadingDao uniqueInstance;

	private RepFirstLoadingDao() {

	}

	public static RepFirstLoadingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepFirstLoadingDao();
		}
		return uniqueInstance;
	}

	public TblRepFirstTimeDate findByDate(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepFirstTimeDate.class);
			criteria.setFetchMode("tblRepFirstTimeValueList", FetchMode.JOIN);
			criteria.createAlias("tblRepFirstTimeValueList.productId",
					"productId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepFirstTimeDate firstTimeDate = (TblRepFirstTimeDate) criteria
					.uniqueResult();
			tx.commit();
			return firstTimeDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblRepFirstTimeDate findByDateWithNoJoins(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepFirstTimeDate.class);
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepFirstTimeDate firstTimeDate = (TblRepFirstTimeDate) criteria
					.uniqueResult();
			tx.commit();
			return firstTimeDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblRepFirstTimeValue findByDateAndProduct(Long firstDateId,
			Product product, Direction directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepFirstTimeValue.class);
			criteria.createAlias("firstTimeDateId", "firstTimeDateId");
			criteria.createAlias("firstTimeDateId.directionId", "directionId");
			criteria.add(Restrictions.eq("productId", product));

			criteria.add(Restrictions.eq("firstTimeDateId.firstTimeDateId",
					firstDateId));
			criteria.add(Restrictions.eq("firstTimeDateId.directionId",
					directionId));
			TblRepFirstTimeValue firstValue = (TblRepFirstTimeValue) criteria
					.uniqueResult();
			tx.commit();
			return firstValue;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public List<TblRepFirstTimeDate> findByCompletedRep(Integer comVal) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblRepFirstTimeDate.class);
			criteria.setFetchMode("tblRepFirstTimeValueList", FetchMode.JOIN);
			criteria.add(Restrictions.eq("isCompleted", comVal));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<TblRepFirstTimeDate> dateList = criteria.list();
			// for (TblComDiscountDate d : discountDateList) {
			// for (TblComDiscountValue v : d.getTblComDiscountValueList()) {
			// System.out.println(v.getDiscountValue());
			// }
			// }
			tx.commit();
			return dateList;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
