package com.freelance.maraay.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.TblComBlendingDate;
import com.freelance.maraay.model.TblComBlendingValue;
import com.freelance.maraay.model.TblComDefectsDate;
import com.freelance.maraay.model.TblComDefectsValue;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class ComDefectDao {
	private static ComDefectDao uniqueInstance;

	public static ComDefectDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ComDefectDao();
		}
		return uniqueInstance;
	}

	public String insertValue(TblComDefectsValue transientInstance) {
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

	public String insertDate(TblComDefectsDate transientInstance) {
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

	public String updateDate(TblComDefectsDate tblComBlendingDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(tblComBlendingDate);
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

	public List<TblComDefectsDate> listAllDefectDates() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDefectsDate.class);
			criteria.setFetchMode("tblComDefectsValueList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("byUserId", "byUserId");

			List<TblComDefectsDate> defectDates = criteria.list();
			tx.commit();
			return defectDates;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
	
	public List<TblComDefectsDate> listByDate(Date startDate, Date endDate) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblComDefectsDate.class);
			criteria.setFetchMode("tblComDefectsValueList", FetchMode.JOIN);
			criteria.add(Restrictions.ge("date", startDate));
			criteria.add(Restrictions.le("date", endDate));
			criteria.createCriteria("byUserId", "byUserId");
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			List<TblComDefectsDate> dates = criteria.list();
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
	
	public TblComDefectsDate findByDate(Date date){
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(TblComDefectsDate.class);
			criteria.add(Restrictions.eq("date", date));

			TblComDefectsDate  defectDate = (TblComDefectsDate) criteria.uniqueResult();
			if(defectDate != null){
				for(TblComDefectsValue v : defectDate.getTblComDefectsValueList()){
					System.out.println(v.getMaxMount() + " " + v.getProductId());
				}
			}
			tx.commit();
			return defectDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
