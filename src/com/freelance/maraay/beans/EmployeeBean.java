package com.freelance.maraay.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;

import org.primefaces.event.RowEditEvent;

import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Employee;
import com.freelance.maraay.dao.DirectionDao;
import com.freelance.maraay.dao.EmployeeDao;
import com.freelance.maraay.model.EmployeeType;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Performance;

@ManagedBean
@ViewScoped
public class EmployeeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Employee employee = new Employee();
	


	EmployeeDao employeeDao = EmployeeDao.getInstance();
	private List<Employee> employeeList;
	private List<Employee> represintatives;
	private List<Employee> drivers;
	private List<EmployeeType> employeeTypes;
	private List<Employee> filteredEmployees;

	@ManagedProperty("#{service}")
	private Service service;

	@PostConstruct
	public void init() {
		employeeList = service.findEmployeeList();
		employeeTypes = service.findAllEmployeeTypes();
		represintatives = service.findrepresintatives();
		drivers = service.findDrivers();
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public List<Employee> getRepresintatives() {
		return represintatives;
	}

	public List<Employee> getDrivers() {
		return drivers;
	}

	public List<EmployeeType> getEmployeeTypes() {
		return employeeTypes;
	}

	public List<Employee> getFilteredEmployees() {
		return filteredEmployees;
	}

	public void setFilteredEmployees(List<Employee> filteredEmployees) {
		this.filteredEmployees = filteredEmployees;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String addEmployee() {
		try {
			employeeDao.insertEmployee(employee);
			return "allEmployee";
		} catch (Exception e) {
			return "fail";
		}

	}

	public String deleteEmployee(Employee employee) {
		try {
			employeeDao.deleteEmployee(employee);
			return "allEmployee";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}

	public void validateEmployee(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		Employee testEmployee = employeeDao.findByName_type(employee.getName(),
				employee.getEmployeeType().getId());
		System.out.println("name " + employee.getName());
		if (testEmployee != null) {
			FacesMessage message = new FacesMessage("noooooooooo");
			throw new ValidatorException(message);
		}

	}

	public void onRowEdit(RowEditEvent event) {
		try {
			System.out.println("dep date "
					+ ((Employee) event.getObject()).getDeparture_date());
			employeeDao.updateEmployee((Employee) event.getObject());
		} catch (Exception e) {

		} finally {
			Performance.releaseMemory();
		}

	}

	public void onRowCancel(RowEditEvent event) {
	}
	
	/******************* validation logic ****************/

	public void validateName(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedNameMsg = bundle.getString("DUPLICATENAME");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputName = (UIInput) components
				.findComponent("empName");
		String name = uiInputName.getLocalValue() == null ? "" : uiInputName
				.getLocalValue().toString();

		String nameId = uiInputName.getClientId();
		Employee employee = EmployeeDao.getInstance().findByName(name);

		// Let required="true" do its job.
		if (name.isEmpty()) {
			return;
		}
		if (employee != null) {
			FacesMessage msg = new FacesMessage(duplicatedNameMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(nameId, msg);
			fc.renderResponse();

		}
	}

}
