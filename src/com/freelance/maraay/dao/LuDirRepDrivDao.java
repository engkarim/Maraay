package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.TblLuDirRepDriv;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class LuDirRepDrivDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LuDirRepDrivDao uniqueInstance;

	public static LuDirRepDrivDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new LuDirRepDrivDao();
		}
		return uniqueInstance;
	}

	public String insertNew(TblLuDirRepDriv transientInstance) {
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

	public String updateRecord(TblLuDirRepDriv persistentInstance) {

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

	public List<TblLuDirRepDriv> listAll() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblLuDirRepDriv.class);
			criteria.createCriteria("repId", "repId");
			criteria.createCriteria("drivId", "drivId");
			List<TblLuDirRepDriv> results = criteria.list();
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

	public TblLuDirRepDriv findByDate() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblLuDirRepDriv.class);
			criteria.createCriteria("repId", "repId");
			criteria.createCriteria("drivId", "drivId");
			TblLuDirRepDriv result = (TblLuDirRepDriv) criteria.uniqueResult();
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblLuDirRepDriv findDrivByName(String name, Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblLuDirRepDriv.class);
			criteria.createCriteria("drivId", "drivId");
			criteria.add(Restrictions.eq("drivId.name", name));
			criteria.add(Restrictions.eq("date", date));
			TblLuDirRepDriv result = (TblLuDirRepDriv) criteria.uniqueResult();
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblLuDirRepDriv findDrivById(int id, Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblLuDirRepDriv.class);
			criteria.createCriteria("drivId", "drivId");
			criteria.add(Restrictions.eq("drivId.id", id));
			criteria.add(Restrictions.eq("date", date));
			TblLuDirRepDriv result = (TblLuDirRepDriv) criteria.uniqueResult();
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblLuDirRepDriv findRepByName(String name, Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblLuDirRepDriv.class);
			criteria.createCriteria("repId", "repId");
			criteria.add(Restrictions.eq("repId.name", name));
			criteria.add(Restrictions.eq("date", date));
			TblLuDirRepDriv result = (TblLuDirRepDriv) criteria.uniqueResult();
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblLuDirRepDriv findRepById(int id, Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblLuDirRepDriv.class);
			criteria.createCriteria("repId", "repId");
			criteria.add(Restrictions.eq("repId.id", id));
			criteria.add(Restrictions.eq("date", date));
			TblLuDirRepDriv result = (TblLuDirRepDriv) criteria.uniqueResult();
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
