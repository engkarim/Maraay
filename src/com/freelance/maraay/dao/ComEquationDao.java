package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.TblComCalculationEquation;
import com.freelance.maraay.model.TblComSupplyValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class ComEquationDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ComEquationDao uniqueInstance;

	public static ComEquationDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ComEquationDao();
		}
		return uniqueInstance;
	}

	public String insertNew(TblComCalculationEquation transientInstance) {
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

	public String updateEq(TblComCalculationEquation calculationEquation) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(calculationEquation);
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

	public String updateSupplyValue(TblComSupplyValue supplyValue) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(supplyValue);
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

	public List<TblComCalculationEquation> listAllEqs() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComCalculationEquation.class);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComCalculationEquation> eqs = criteria.list();
			tx.commit();
			return eqs;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<TblComSupplyValue> listAllSupplyValues() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComSupplyValue.class);
			criteria.createCriteria("calcEquId", "calcEquId");
			criteria.createCriteria("payTypeId", "payTypeId");
			criteria.createCriteria("byUserId", "byUserId");
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComSupplyValue> eqs = criteria.list();
			tx.commit();
			return eqs;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<TblComCalculationEquation> listByDate(Date startDate,
			Date endDate) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComCalculationEquation.class);

			criteria.setFetchMode("tblComSupplyValueList", FetchMode.JOIN);
			/*
			 * criteria.createCriteria("tblComSupplyValueList",
			 * "tblComSupplyValueList");
			 * criteria.createAlias("tblComSupplyValueList.payTypeId",
			 * "payTypeId");
			 */
			criteria.add(Restrictions.ge("date", startDate));
			criteria.add(Restrictions.le("date", endDate));
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComCalculationEquation> dates = criteria.list();
			tx.commit();
			for (TblComCalculationEquation d : dates) {
				System.out.println("dsgsdgsdgdsg");
				System.out.println(d.getTblComSupplyValueList().size());
				for (TblComSupplyValue v : d.getTblComSupplyValueList()) {
					System.out.println(v.getSenderName());
				}
			}
			return dates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public TblComCalculationEquation findByDate(Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComCalculationEquation.class);
			criteria.createCriteria("tblComSupplyValueList",
					"tblComSupplyValueList").createCriteria("payTypeId",
					"payTypeId");

			criteria.add(Restrictions.eq("date", date));

			TblComCalculationEquation eq = (TblComCalculationEquation) criteria
					.uniqueResult();
			if (eq != null) {
				for (TblComSupplyValue v : eq.getTblComSupplyValueList()) {
					System.out.println(v.getPayTypeId().getPayType());
				}
			}
			tx.commit();
			return eq;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComCalculationEquation findById(Long id) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComCalculationEquation.class);
			criteria.createCriteria("tblComSupplyValueList",
					"tblComSupplyValueList");
			criteria.add(Restrictions.eq("calcEquId", id));

			TblComCalculationEquation eq = (TblComCalculationEquation) criteria
					.uniqueResult();
			if (eq != null) {
				for (TblComSupplyValue v : eq.getTblComSupplyValueList()) {
				}
			}
			tx.commit();
			return eq;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public TblComSupplyValue findSupplyById(Long id) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComSupplyValue.class);
			criteria.createCriteria("calcEquId", "calcEquId");
			criteria.createCriteria("payTypeId", "payTypeId");
			criteria.createCriteria("byUserId", "byUserId");
			criteria.add(Restrictions.eq("supplyValueId", id));
			TblComSupplyValue supplyValue = (TblComSupplyValue) criteria
					.uniqueResult();
			tx.commit();
			return supplyValue;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
