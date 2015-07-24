package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepDefectDate;
import com.freelance.maraay.model.TblRepDefectValue;
import com.freelance.maraay.model.TblRepOfferDate;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class RepOfferDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepOfferDao uniqueInstance;

	private RepOfferDao() {

	}

	public static RepOfferDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RepOfferDao();
		}
		return uniqueInstance;
	}

	public TblRepOfferDate findByDate(Date date, int directionId) {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(TblRepOfferDate.class);
			criteria.setFetchMode("tblRepOfferValueList", FetchMode.JOIN);
			criteria.createAlias("tblRepOfferValueList.productId", "productId");
			criteria.add(Restrictions.eq("directionId.id", directionId));
			criteria.add(Restrictions.eq("date", date));
			TblRepOfferDate offerDate = (TblRepOfferDate) criteria
					.uniqueResult();
			tx.commit();
			return offerDate;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}


}
