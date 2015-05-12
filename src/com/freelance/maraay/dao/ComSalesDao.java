package com.freelance.maraay.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.TblComCalculationEquation;
import com.freelance.maraay.model.TblComDefectsDate;
import com.freelance.maraay.model.TblComSalesDate;
import com.freelance.maraay.model.TblComSalesValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class ComSalesDao {
	private static ComSalesDao uniqueInstance;

	public static ComSalesDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ComSalesDao();
		}
		return uniqueInstance;
	}

	public String insertValue(TblComSalesValue transientInstance) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			tx.commit();
			return "success";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public String insertDate(TblComSalesDate transientInstance) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.save(transientInstance);
			tx.commit();
			return "success";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public String updateDate(TblComSalesDate transientInstance) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.update(transientInstance);
			tx.commit();
			return "success";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<TblComSalesDate> listAllSalesDates() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComSalesDate.class);
			criteria.setFetchMode("tblComSalesValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("byUserId", "byUserId");

			List<TblComSalesDate> salesDates = criteria.list();
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

	public List<TblComSalesDate> listByDate(Date startDate, Date endDate) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComSalesDate.class);
			criteria.setFetchMode("tblComSalesValueList", FetchMode.JOIN);
			criteria.add(Restrictions.ge("date", startDate));
			criteria.add(Restrictions.le("date", endDate));
			criteria.createCriteria("byUserId", "byUserId");
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComSalesDate> dates = criteria.list();
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

	public TblComSalesDate findByDate(Date date) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComSalesDate.class);
			criteria.add(Restrictions.eq("date", date));
			TblComSalesDate salesDate = (TblComSalesDate) criteria
					.uniqueResult();
			if (salesDate != null) {
				for (TblComSalesValue v : salesDate.getTblComSalesValueList()) {
					System.out.println(v.getTotalMount() + ""
							+ v.getProductId());
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
