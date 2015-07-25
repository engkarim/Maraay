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
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblComDiscountValue;
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.model.TblComIncomingValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class ComDiscountingDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ComDiscountingDao uniqueInstance;

	public static ComDiscountingDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ComDiscountingDao();
		}
		return uniqueInstance;
	}

	public String insertValue(TblComDiscountValue transientInstance) {
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

	public String insertDate(TblComDiscountDate transientInstance) {
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

	public String updateDate(TblComDiscountDate tblComDiscountDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(tblComDiscountDate);
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

	public String updateValue(TblComDiscountValue tblComDiscountValue) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(tblComDiscountValue);
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

	public List<TblComDiscountDate> listAllDiscountDates() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDiscountDate.class);
			criteria.setFetchMode("tblComDiscountValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("byUser", "byUser");

			List<TblComDiscountDate> discountDates = criteria.list();
			tx.commit();
			return discountDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblComDiscountDate findByDate(Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDiscountDate.class);
			criteria.add(Restrictions.eq("date", date));

			TblComDiscountDate discountDate = (TblComDiscountDate) criteria
					.uniqueResult();
			if (discountDate != null) {
				for (TblComDiscountValue v : discountDate
						.getTblComDiscountValueList()) {
					System.out.println("value " + v.getDiscountValue()
							+ v.getProductId().getName());
				}
			}
			tx.commit();
			return discountDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public List<TblComDiscountDate> findByCompletedCalue(Integer comVal) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDiscountDate.class);
			criteria.createAlias("tblComDiscountValueList",
					"tblComDiscountValueList");
			criteria.add(Restrictions.eq("isCompleted", comVal));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			List<TblComDiscountDate> discountDateList = criteria.list();
//			for (TblComDiscountDate d : discountDateList) {
//				for (TblComDiscountValue v : d.getTblComDiscountValueList()) {
//					System.out.println(v.getDiscountValue());
//				}
//			}

			tx.commit();
			return discountDateList;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComDiscountValue findByDateAndProduct(Long discountDateId,
			Product product) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDiscountValue.class);
			criteria.add(Restrictions.eq("productId", product));
			criteria.add(Restrictions.eq("discountDate.dateId", discountDateId));

			TblComDiscountValue discountValue = (TblComDiscountValue) criteria
					.uniqueResult();
			tx.commit();
			return discountValue;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComDiscountDate findDateById(Long discountDateId) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDiscountDate.class);
			// criteria.setFetchMode("tblComDiscountValueList", FetchMode.JOIN);
			criteria.createCriteria("tblComDiscountValueList",
					"tblComDiscountValueList").createCriteria("productId",
					"productId");
			criteria.add(Restrictions.eq("dateId", discountDateId));
			TblComDiscountDate discountDate = (TblComDiscountDate) criteria
					.uniqueResult();
			if (discountDate != null) {
				for (TblComDiscountValue v : discountDate
						.getTblComDiscountValueList()) {
					System.out.println("value " + v.getDiscountValue()
							+ v.getProductId().getName());
				}
			}

			tx.commit();
			return discountDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComDiscountValue findByDateAndProduct(Date incomingDate,
			Product product) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDiscountValue.class);
			criteria.createCriteria("discountDate", "discountDate");
			criteria.add(Restrictions.eq("productId", product));
			criteria.add(Restrictions.eq("discountDate.date", incomingDate));

			TblComDiscountValue discountValue = (TblComDiscountValue) criteria
					.uniqueResult();
			tx.commit();
			return discountValue;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
