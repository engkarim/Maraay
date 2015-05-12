package com.freelance.maraay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.model.PayType;
import com.freelance.maraay.model.TblComSalesDate;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class PayTypeDao {
	private static PayTypeDao uniqueInstance;

	public static PayTypeDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new PayTypeDao();
		}
		return uniqueInstance;
	}

	public List<PayType> listAllTypes() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(PayType.class);
			List<PayType> types = criteria.list();
			tx.commit();
			return types;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
}
