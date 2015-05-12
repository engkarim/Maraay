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
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.model.TblComIncomingValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class ComIncomingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ComIncomingDao uniqueInstance;

	public static ComIncomingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ComIncomingDao();
		}
		return uniqueInstance;
	}

	public String insertValue(TblComIncomingValue transientInstance) {
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

	public String insertDate(TblComIncomingDate transientInstance) {
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

	public String updateDate(TblComIncomingDate tblComIncomingDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(tblComIncomingDate);
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

	public List<TblComIncomingDate> listAllIncomingDates() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComIncomingDate.class);
			criteria.setFetchMode("tblComIncomingValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("byUserId", "byUserId");

			List<TblComIncomingDate> incomingDates = criteria.list();
			tx.commit();
			return incomingDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<TblComIncomingDate> listByDate(Date startDate, Date endDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComIncomingDate.class);
			criteria.setFetchMode("tblComIncomingValueList", FetchMode.JOIN);
			criteria.add(Restrictions.ge("date", startDate));
			criteria.add(Restrictions.le("date", endDate));
			criteria.createCriteria("byUserId", "byUserId");
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComIncomingDate> incomingDates = criteria.list();
//			for(TblComIncomingDate d : incomingDates){
//				System.out.println(d.getTblComIncomingValueList().size());
//			}
			tx.commit();
			return incomingDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblComIncomingDate findByDate(Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComIncomingDate.class);
			criteria.setFetchMode("tblComIncomingValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("date", date));

			TblComIncomingDate incomingDate = (TblComIncomingDate) criteria
					.uniqueResult();

			if (incomingDate != null) {
				for (TblComIncomingValue v : incomingDate
						.getTblComIncomingValueList()) {
					System.out.println(v.getMaxMount()
							+ v.getProductId().getName());
				}
			}
			tx.commit();
			return incomingDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComIncomingValue findByDateAndProduct(Long incomingDateId,
			Product product) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComIncomingValue.class);
			criteria.add(Restrictions.eq("productId", product));
			criteria.add(Restrictions.eq("incomingDateId.incomingDateId",
					incomingDateId));
			TblComIncomingValue incomingValue = (TblComIncomingValue) criteria
					.uniqueResult();
			tx.commit();
			return incomingValue;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComIncomingDate findDateById(Long incomingDateId) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComIncomingDate.class);
			criteria.createCriteria("tblComIncomingValueList",
					"tblComIncomingValueList").createCriteria("productId",
					"productId");
			criteria.add(Restrictions.eq("incomingDateId", incomingDateId));
			TblComIncomingDate incomingDate = (TblComIncomingDate) criteria
					.uniqueResult();
			if (incomingDate != null) {
				for (TblComIncomingValue v : incomingDate
						.getTblComIncomingValueList()) {
					System.out.println(v.getMaxMount()
							+ v.getProductId().getName());
				}
			}
			tx.commit();
			return incomingDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
