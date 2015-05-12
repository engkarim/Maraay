package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.model.DayWeek;

public class DayWeekDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DayWeekDao uniqueInstance;

	public static DayWeekDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DayWeekDao();
		}
		return uniqueInstance;
	}

	public String insertDayWeek(DayWeek transientInstance) {
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

	public String deleteDayWeek(DayWeek persistentInstance) {

		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.delete(persistentInstance);
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

	public String updateDayWeek(DayWeek persistentInstance) {

		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.update(persistentInstance);
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

	public List<DayWeek> listAllDayWeek() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(DayWeek.class);
			List<DayWeek> results = criteria.list();
			tx.commit();
			return results;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public DayWeek findByName(String name) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(DayWeek.class).add(
					Restrictions.eq("dayName", name));
			DayWeek dayWeek = (DayWeek) criteria.uniqueResult();
			tx.commit();
			return dayWeek;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public DayWeek findById(int id) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(DayWeek.class).add(
					Restrictions.eq("id", id));
			DayWeek dayWeek = (DayWeek) criteria.uniqueResult();
			tx.commit();
			return dayWeek;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
}
