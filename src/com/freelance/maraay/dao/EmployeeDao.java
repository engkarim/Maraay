package com.freelance.maraay.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.model.Employee;

public class EmployeeDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static EmployeeDao uniqueInstance;

	public static EmployeeDao getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new EmployeeDao();
		}
		return uniqueInstance;
	}

	public String insertEmployee(Employee transientInstance) {
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

	public String deleteEmployee(Employee persistentInstance) {

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

	public String updateEmployee(Employee persistentInstance) {

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

	public List<Employee> listAllEmployee() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Employee.class);
			List<Employee> results = criteria.list();
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

	public List<Employee> findByExample(Employee employee) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			List<Employee> results = (List<Employee>) session
					.createCriteria(Employee.class)
					.add(Example.create(employee)).list();

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

	public Employee findByName_type(String name, int type) {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Employee.class)
					.add(Restrictions.eq("this.name", name))
					.add(Restrictions.eq("this.employeeType.id", type));
			Employee employee = (Employee) criteria.uniqueResult();
			System.out.println(employee.getName());
			tx.commit();
			return employee;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<Employee> findAllRepresintative() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Employee.class).add(
					Restrictions.eq("this.employeeType.id", 2));
			List<Employee> represintativeList = criteria.list();
			tx.commit();
			return represintativeList;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

	public List<Employee> findAllDrivers() {
		Session session = null;
		Transaction tx = null;

		try {
			session = SessionFactoryUtil.getSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Employee.class).add(
					Restrictions.eq("this.employeeType.id", 3));
			List<Employee> driversList = criteria.list();
			tx.commit();
			return driversList;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}

}
