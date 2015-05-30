package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComBlendingDate;
import com.freelance.maraay.model.TblComBlendingValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class ComBlendingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ComBlendingDao uniqueInstance;

	public static ComBlendingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ComBlendingDao();
		}
		return uniqueInstance;
	}

	public String insertValue(TblComBlendingValue transientInstance) {
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

	public String insertDate(TblComBlendingDate transientInstance) {
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

	public String updateDate(TblComBlendingDate tblComBlendingDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(tblComBlendingDate);
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

	public List<TblComBlendingDate> listAllBlendingDates() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComBlendingDate.class);
			criteria.setFetchMode("tblComBlendingValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("byUserId", "byUserId");

			List<TblComBlendingDate> blendingDates = criteria.list();
			tx.commit();
			return blendingDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
	
	public List<TblComBlendingDate> listByDate(Date startDate, Date endDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComBlendingDate.class);
			criteria.setFetchMode("tblComBlendingValueList", FetchMode.JOIN);
			criteria.add(Restrictions.ge("date", startDate));
			criteria.add(Restrictions.le("date", endDate));
			criteria.createCriteria("byUserId", "byUserId");
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComBlendingDate> dates = criteria.list();
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

	public TblComBlendingDate findByDate(Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComBlendingDate.class);
			criteria.createCriteria("tblComBlendingValueList", "tblComBlendingValueList");
			criteria.add(Restrictions.eq("date", date));
			TblComBlendingDate blendingDate = (TblComBlendingDate) criteria
					.uniqueResult();
			if (blendingDate != null) {
				for (TblComBlendingValue v : blendingDate
						.getTblComBlendingValueList()) {
					System.out
							.println(v.getMaxMount() + " " + v.getProductId());
				}
			}

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

	public TblComBlendingValue findByDateAndProduct(Long blendingDateId,
			Product product) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComBlendingValue.class);
			criteria.add(Restrictions.eq("productId", product));
			criteria.add(Restrictions.eq("blendingDateId.blendingDateId",
					blendingDateId));
			TblComBlendingValue blendingValue = (TblComBlendingValue) criteria
					.uniqueResult();
			tx.commit();
			return blendingValue;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
