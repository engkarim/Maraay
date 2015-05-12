package com.freelance.maraay.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.TblRepSalesDate;
import com.freelance.maraay.model.TblRepSalesValue;
import com.freelance.maraay.model.TblRepSalesDate;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepSalesDao {
	private static RepSalesDao uniqueInstance;

	public static RepSalesDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepSalesDao();
		}
		return uniqueInstance;
	}

	public List<TblRepSalesDate> listAllSalesDates() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepSalesDate.class);
			criteria.setFetchMode("tblRepSalesValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("byUserId", "byUserId");

			List<TblRepSalesDate> salesDates = criteria.list();
			tx.commit();
			return salesDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<TblRepSalesDate> listAllSalesByDirection(int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepSalesDate.class);
			criteria.setFetchMode("tblRepSalesValueList", FetchMode.JOIN);
			criteria.createCriteria("byUserId", "byUserId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblRepSalesDate> salesDates = criteria.list();
			tx.commit();
			return salesDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public List<TblRepSalesDate> listByDate(Date startDate, Date endDate) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepSalesDate.class);
			criteria.setFetchMode("TblRepSalesValueList", FetchMode.JOIN);
			criteria.add(Restrictions.ge("date", startDate));
			criteria.add(Restrictions.le("date", endDate));
			criteria.createCriteria("byUserId", "byUserId");
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblRepSalesDate> dates = criteria.list();
			tx.commit();
			return dates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblRepSalesDate findByDate(Date date) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepSalesDate.class);
			criteria.add(Restrictions.eq("date", date));
			TblRepSalesDate salesDate = (TblRepSalesDate) criteria
					.uniqueResult();
			if (salesDate != null) {
				for (TblRepSalesValue v : salesDate.getTblRepSalesValueList()) {
					System.out.println(v.getProductId());
				}
			}
			tx.commit();
			return salesDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

}
