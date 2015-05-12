package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.freelance.maraay.model.Customer;
import com.freelance.maraay.dao.CustomerDao;
import com.freelance.maraay.model.DayWeek;
import com.freelance.maraay.dao.DayWeekDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.dao.DirectionDao;

@ManagedBean
@SessionScoped
public class CustomerBean implements Serializable {
	private static final long serialVersionUID = 1L;

	CustomerDao customerDao = CustomerDao.getInstance();
	DayWeekDao dayWeekDao = DayWeekDao.getInstance();
	DirectionDao directionDao = DirectionDao.getInstance();
	private Integer searchedDirection;
	private Integer searchedDay;
	private List<Customer> searchedCustomers;

	public List<Customer> getSearchedCustomers() {
		Direction direction = directionDao.findById(searchedDirection);
		DayWeek visitingDayWeek = dayWeekDao.findById(searchedDay);
		searchedCustomers = customerDao.findByVisitingDays(visitingDayWeek,
				direction);
		return searchedCustomers;
	}

	public void setSearchedCustomers(List<Customer> searchedCustomers) {
		this.searchedCustomers = searchedCustomers;
	}

	public Integer getSearchedDay() {
		return searchedDay;
	}

	public void setSearchedDay(Integer searchedDay) {
		this.searchedDay = searchedDay;
	}

	public Integer getSearchedDirection() {
		return searchedDirection;
	}

	public void setSearchedDirection(Integer searchedDirection) {
		this.searchedDirection = searchedDirection;
	}

}
