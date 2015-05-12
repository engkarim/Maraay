package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.freelance.maraay.model.DayWeek;
import com.freelance.maraay.dao.DayWeekDao;

@ManagedBean
@ViewScoped
public class DayWeekBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DayWeek dayWeek = new DayWeek();
	DayWeekDao dayWeekDao = DayWeekDao.getInstance();

	private List<DayWeek> days;

	public List<DayWeek> getDays() {
		days = dayWeekDao.listAllDayWeek();
		return days;
	}

	public DayWeek getDayWeek() {
		return dayWeek;
	}

	public void setDayWeek(DayWeek dayWeek) {
		this.dayWeek = dayWeek;
	}

}
