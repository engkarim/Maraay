package com.freelance.maraay.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComOfferDate;
import com.freelance.maraay.model.TblComOfferValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class ComOfferDao {
	private static ComOfferDao uniqueInstance;

	public static ComOfferDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ComOfferDao();
		}
		return uniqueInstance;
	}

	public String insertValue(TblComOfferValue transientInstance) {
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

	public String insertDate(TblComOfferDate transientInstance) {
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

	public String updateDate(TblComOfferDate transientInstance) {
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

	public List<TblComOfferDate> listAllOffersDates() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComOfferDate.class);
			criteria.setFetchMode("tblComOfferValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("byUserId", "byUserId");

			List<TblComOfferDate> offersDates = criteria.list();
			tx.commit();
			return offersDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<TblComOfferDate> listByDate(Date startDate, Date endDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComOfferDate.class);
			criteria.setFetchMode("tblComOfferValueList", FetchMode.JOIN);
			criteria.add(Restrictions.ge("date", startDate));
			criteria.add(Restrictions.le("date", endDate));
			criteria.createCriteria("byUserId", "byUserId");
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComOfferDate> offersDates = criteria.list();
			tx.commit();
			return offersDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblComOfferDate findByDate(Date date) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComOfferDate.class);
			criteria.createCriteria("tblComOfferValueList", "tblComOfferValueList");
			criteria.add(Restrictions.eq("date", date));
			TblComOfferDate offerDate = (TblComOfferDate) criteria
					.uniqueResult();
			if (offerDate != null) {
				for (TblComOfferValue v : offerDate.getTblComOfferValueList()) {
					System.out
							.println(v.getMaxMount() + " " + v.getProductId());
				}
			}
			tx.commit();
			return offerDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComOfferValue findByDateAndProduct(Long offerDateId,
			Product product) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComOfferValue.class);
			criteria.add(Restrictions.eq("productId", product));
			criteria.add(Restrictions
					.eq("offerDateId.offerDateId", offerDateId));
			TblComOfferValue offerValue = (TblComOfferValue) criteria
					.uniqueResult();
			tx.commit();
			return offerValue;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

}
