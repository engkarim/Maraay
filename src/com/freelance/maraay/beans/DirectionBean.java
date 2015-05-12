package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.freelance.maraay.model.Direction;
import com.freelance.maraay.dao.DirectionDao;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Performance;

@ManagedBean
@ViewScoped
public class DirectionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Direction direction = new Direction();
	DirectionDao directionDao = DirectionDao.getInstance();
	private List<Direction> directions;
	private List<Direction> filteredDirections;

	@ManagedProperty("#{service}")
	private Service service;

	@PostConstruct
	public void init() {
		directions = service.findAllDirections();
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Direction> getDirections() {
		return directions;
	}

	public List<Direction> getFilteredDirections() {
		return filteredDirections;
	}

	public void setFilteredDirections(List<Direction> filteredDirections) {
		this.filteredDirections = filteredDirections;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String addDirection() {
		try {
			directionDao.insertDirection(direction);
			return "allDirections";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}

	public String deleteDirection(Direction direction) {
		try {
			directionDao.deleteDirection(direction);
			return "allDirections";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}

}
