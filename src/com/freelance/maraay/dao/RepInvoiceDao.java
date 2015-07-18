package com.freelance.maraay.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.TblRepInvoice;
import com.freelance.maraay.model.TblRepInvoiceValues;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepInvoiceDao {
	private static RepInvoiceDao uniqueInstance;

	private RepInvoiceDao() {

	}

	public static RepInvoiceDao getInstance() {

		if (uniqueInstance == null) {
			uniqueInstance = new RepInvoiceDao();
		}
		return uniqueInstance;
	}

	public List<TblRepInvoice> listAllInvoicesByDirection(int directionId) {

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepInvoice.class);
			criteria.setFetchMode("tblRepInvoiceValuesList", FetchMode.JOIN);
			criteria.createCriteria("byUserId", "byUserId");
			criteria.createCriteria("customerId", "customerId");
			criteria.createCriteria("customerId.direction", "directionId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblRepInvoice> salesDates = criteria.list();
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

	public TblRepInvoice findById(long invoiceId) {

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepInvoice.class);
			criteria.setFetchMode("tblRepInvoiceValuesList", FetchMode.JOIN);
			criteria.createCriteria("tblRepInvoiceValuesList.productId", "productId");
			criteria.createCriteria("byUserId", "byUserId");
			criteria.createCriteria("customerId", "customerId");
			criteria.createCriteria("customerId.direction", "directionId");
			criteria.add(Restrictions.eq("invoiceId", invoiceId));
			TblRepInvoice invoice = (TblRepInvoice) criteria.uniqueResult();
			tx.commit();
			return invoice;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
	
	public TblRepInvoice findByNumber(BigInteger invoicenum) {

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepInvoice.class);
			criteria.add(Restrictions.eq("invoiceNumber", invoicenum));
			TblRepInvoice invoice = (TblRepInvoice) criteria.uniqueResult();
			tx.commit();
			return invoice;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public List<TblRepInvoice> listAll() {

		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepInvoice.class);
			criteria.setFetchMode("tblRepInvoiceValuesList", FetchMode.JOIN);
			criteria.createCriteria("byUserId", "byUserId");
			criteria.createCriteria("customerId", "customerId");
			criteria.createCriteria("customerId.direction", "directionId");
			criteria.addOrder(Order.asc("invoiceNumber"));
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblRepInvoice> dates = criteria.list();
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

}
