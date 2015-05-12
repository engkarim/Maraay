package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.freelance.maraay.model.Customer;
import com.freelance.maraay.model.DayWeek;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.utils.SessionFactoryUtil;

public class CustomerDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static CustomerDao uniqueInstance;

	public static CustomerDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new CustomerDao();
		}
		return uniqueInstance;
	}

	public String insertCustomer(Customer transientInstance) {
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

	public String deleteCustomer(Customer persistentInstance) {

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

	public String updateCustomer(Customer persistentInstance) {

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

	public List<Customer> listAllCustomer() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.setFetchMode("productsList", FetchMode.JOIN);
			criteria.setFetchMode("daysList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("direction", "direction");
			criteria.createCriteria("customerType", "customerType");

			List<Customer> results = criteria.list();
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

	public List<Customer> findByVisitingDays(DayWeek visitingDayWeek,
			Direction direction) {
		Session session = null;
		Transaction tx = null;
		List<Customer> filterdCustomers = new ArrayList<Customer>();

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.setFetchMode("productsList", FetchMode.JOIN);
			criteria.setFetchMode("daysList", FetchMode.JOIN);
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			criteria.createCriteria("direction", "direction");
			criteria.createCriteria("customerType", "customerType");
			criteria.add(Restrictions.eq("direction", direction));
			List<Customer> customers = criteria.list();
			for (Customer customer : customers) {
				if (customer.getDaysList().contains(visitingDayWeek)) {
					filterdCustomers.add(customer);
				}
			}
			tx.commit();
			return filterdCustomers;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public Customer findById(int customerID) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.createCriteria("direction", "direction");
			criteria.add(Restrictions.eq("id", customerID));
			Customer customer = (Customer) criteria.uniqueResult();
			tx.commit();
			return customer;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

}
