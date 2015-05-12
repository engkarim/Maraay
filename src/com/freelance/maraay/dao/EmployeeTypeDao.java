package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.model.EmployeeType;
import com.sun.org.apache.regexp.internal.recompile;

public class EmployeeTypeDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static EmployeeTypeDao uniqueIstance;

	public static EmployeeTypeDao getInstance() {
		if (uniqueIstance == null) {
			uniqueIstance = new EmployeeTypeDao();
		}
		return uniqueIstance;
	}

	public String insertNewJob(EmployeeType job) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.persist(job);
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

	public String updateJob(EmployeeType job) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.update(job);
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

	public List<EmployeeType> listEmployeeTypes() {
		List<EmployeeType> employeeTypesList = new ArrayList<EmployeeType>();
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(EmployeeType.class);
			employeeTypesList = criteria.list();
			tx.commit();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
		return employeeTypesList;
	}
	
	public EmployeeType findById(int id) {

		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(EmployeeType.class);
			criteria.add(Restrictions.eq("id", id));
			EmployeeType job = (EmployeeType) criteria.uniqueResult();
			tx.commit();
			return job;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public EmployeeType findByName(String jobName) {

		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(EmployeeType.class);
			criteria.add(Restrictions.eq("job", jobName));
			EmployeeType job = (EmployeeType) criteria.uniqueResult();
			tx.commit();
			return job;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public EmployeeType findByNameAndId(String jobName, int id) {

		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(EmployeeType.class);
			criteria.add(Restrictions.eq("job", jobName));
			criteria.add(Restrictions.ne("id", id));
			EmployeeType job = (EmployeeType) criteria.uniqueResult();
			tx.commit();
			return job;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
