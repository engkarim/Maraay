package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.model.UserType;

public class UserTypeDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UserTypeDao uniqueInstance;

	public static UserTypeDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new UserTypeDao();
		}
		return uniqueInstance;
	}

	public List<UserType> listAllUserTypes() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(UserType.class);
			List<UserType> userTypes = criteria.list();
			tx.commit();
			return userTypes;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

}
