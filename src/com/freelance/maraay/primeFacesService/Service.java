package com.freelance.maraay.primeFacesService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.freelance.maraay.model.Customer;
import com.freelance.maraay.model.TblComBlendingDate;
import com.freelance.maraay.model.TblComDefectsDate;
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.dao.ComBlendingDao;
import com.freelance.maraay.dao.ComDefectDao;
import com.freelance.maraay.dao.ComDiscountingDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.CustomerDao;
import com.freelance.maraay.model.CustomerType;
import com.freelance.maraay.dao.CustomerTypeDao;
import com.freelance.maraay.model.DayWeek;
import com.freelance.maraay.dao.DayWeekDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.dao.DirectionDao;
import com.freelance.maraay.model.Employee;
import com.freelance.maraay.dao.EmployeeDao;
import com.freelance.maraay.model.EmployeeType;
import com.freelance.maraay.dao.EmployeeTypeDao;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.User;
import com.freelance.maraay.dao.UserDao;
import com.freelance.maraay.model.UserType;
import com.freelance.maraay.dao.UserTypeDao;

@ManagedBean(name = "service")
@ApplicationScoped
public class Service implements Serializable {

	EmployeeDao employeeDao = EmployeeDao.getInstance();
	EmployeeTypeDao employeeTypeDao = EmployeeTypeDao.getInstance();
	UserDao userDao = UserDao.getInstance();
	UserTypeDao userTypeDao = UserTypeDao.getInstance();
	ProductDao productDao = ProductDao.getInstance();
	DayWeekDao dayWeekDao = DayWeekDao.getInstance();
	CustomerDao customerDao = CustomerDao.getInstance();
	CustomerTypeDao customerTypeDao = CustomerTypeDao.getInstance();
	DirectionDao directionDao = DirectionDao.getInstance();
	ComIncomingDao incomingDao = ComIncomingDao.getInstance();
	ComBlendingDao comBlendingDao = ComBlendingDao.getInstance();

	public List<EmployeeType> findAllEmployeeTypes() {
		List<EmployeeType> emlployeeTypes = employeeTypeDao.listEmployeeTypes();
		return emlployeeTypes;
	}

	public List<Employee> findDrivers() {
		List<Employee> drivers = employeeDao.findAllDrivers();
		return drivers;
	}

	public List<Employee> findrepresintatives() {
		List<Employee> represintatives = employeeDao.findAllRepresintative();
		return represintatives;
	}

	public List<Employee> findEmployeeList() {
		List<Employee> employeeList = employeeDao.listAllEmployee();
		return employeeList;
	}

	public List<User> findAllUsers() {
		List<User> users = userDao.listAllUsers();
		return users;
	}

	public List<UserType> findAllUserTypes() {
		List<UserType> userTypes = userTypeDao.listAllUserTypes();
		return userTypes;
	}

	public List<Product> findAllProducts() {
		List<Product> products = productDao.listAllproduct();
		return products;
	}

	public List<DayWeek> findAllDays() {
		List<DayWeek> days = dayWeekDao.listAllDayWeek();
		return days;
	}

	public List<Customer> findAllCustomers() {
		List<Customer> customers = customerDao.listAllCustomer();
		return customers;
	}

	public List<CustomerType> findAllCustomerTypes() {
		List<CustomerType> allTypes = customerTypeDao.listAllCustomerType();
		return allTypes;
	}

	public List<Direction> findAllDirections() {
		List<Direction> allDirections = directionDao.listAllDirection();
		return allDirections;
	}

	public List<TblComIncomingDate> findAllIncomingDates() {
		List<TblComIncomingDate> allIncomingDates = incomingDao
				.listAllIncomingDates();
		return allIncomingDates;
	}

	public List<TblComBlendingDate> findAllBlendingDates() {
		List<TblComBlendingDate> allBlendingDates = comBlendingDao
				.listAllBlendingDates();
		return allBlendingDates;
	}

	public List<TblComDefectsDate> findAllDefectDates() {
		List<TblComDefectsDate> allDefectDates = ComDefectDao.getInstance()
				.listAllDefectDates();
		return allDefectDates;
	}
	

	public List<TblComDiscountDate> findAllDiscountsDates() {
		List<TblComDiscountDate> allDates = ComDiscountingDao.getInstance()
				.listAllDiscountDates();
		return allDates;
	}
}
