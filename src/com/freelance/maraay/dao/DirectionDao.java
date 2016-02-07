package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.model.Direction;

public class DirectionDao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DirectionDao uniqueInstance;

	public static DirectionDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DirectionDao();
		}
		return uniqueInstance;
	}

	public String insertDirection(Direction transientInstance) {
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

	public String deleteDirection(Direction persistentInstance) {

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

	public String updateDirection(Direction persistentInstance) {

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

	public List<Direction> listAllDirection() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Direction.class);
			List<Direction> results = criteria.list();
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

	public Direction findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Direction.class).add(
					Restrictions.eq("id", id));
			Direction direction = (Direction) criteria.uniqueResult();
			tx.commit();
			return direction;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public Direction findByName(String name) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Direction.class).add(
					Restrictions.eq("name", name));
			Direction direction = (Direction) criteria.uniqueResult();
			tx.commit();
			return direction;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
	public Direction findByNameAndNotId(String dirName, int id) {

		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Direction.class);
			criteria.add(Restrictions.eq("name", dirName));
			criteria.add(Restrictions.ne("id", id));
			Direction dir = (Direction) criteria.uniqueResult();
			tx.commit();
			return dir;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
