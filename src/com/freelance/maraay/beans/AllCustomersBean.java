package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.freelance.maraay.dao.CustomerDao;
import com.freelance.maraay.dao.DayWeekDao;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.Customer;
import com.freelance.maraay.model.CustomerType;
import com.freelance.maraay.model.DayWeek;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Performance;

@ManagedBean
@ViewScoped
public class AllCustomersBean implements Serializable {
	private static final long serialVersionUID = 1L;
	CustomerDao customerDao = CustomerDao.getInstance();
	ProductDao productDao = ProductDao.getInstance();
	DayWeekDao dayWeekDao = DayWeekDao.getInstance();

	private List<Customer> customers;
	private List<Customer> filteredCustomers;
	private List<Product> allProducts;
	private List<DayWeek> allDays;
	private List<CustomerType> allTypes;
	private List<String> selectedProducts;
	private List<String> selectedDays;
	private Customer customer = new Customer();
	
	public List<Customer> getFilteredCustomers() {
		return filteredCustomers;
	}

	public void setFilteredCustomers(List<Customer> filteredCustomers) {
		this.filteredCustomers = filteredCustomers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	@ManagedProperty("#{service}")
	private Service service;

	@PostConstruct
	public void init() {
		customers = service.findAllCustomers();
		allProducts = service.findAllProducts();
		allDays = service.findAllDays();
		allTypes = service.findAllCustomerTypes();
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public List<DayWeek> getAllDays() {
		return allDays;
	}

	public List<CustomerType> getAllTypes() {
		return allTypes;
	}

	public List<String> getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(List<String> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<String> getSelectedDays() {
		return selectedDays;
	}

	public void setSelectedDays(List<String> selectedDays) {
		this.selectedDays = selectedDays;
	}

	public String addCustomer() {
		// try {
		List<DayWeek> selectedWeekDays = new ArrayList<DayWeek>();
		if (selectedDays.contains(new String("0"))) {
			selectedWeekDays = dayWeekDao.listAllDayWeek();
		}
		else {
			for (String customerDay : selectedDays) {
				DayWeek dayWeek = dayWeekDao.findByName(customerDay);
				selectedWeekDays.add(dayWeek);
			}
		}
		customer.setDaysList(selectedWeekDays);
		List<Product> selectedProductsList = new ArrayList<Product>();

		if (selectedProducts.contains(new String("0"))) {
			selectedProductsList = productDao.listAllproduct();
		}
		else {
			for (String productName : selectedProducts) {
				Product product = productDao.findByName(productName);
				selectedProductsList.add(product);
			}
		}
		customer.setProductsList(selectedProductsList);
		customerDao.insertCustomer(customer);
		return "allCustomers";
		// } catch (Exception e) {
		// } finally {
		// Performance.releaseMemory();
		// }
	}

	public String deleteCustomer(Customer customer) {

		try {
			customerDao.deleteCustomer(customer);
			return "allCustomers";
		}
		catch (Exception e) {
			return "fail";
		}
		finally {
			Performance.releaseMemory();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			Customer updatedCustomer = (Customer) event.getObject();
			List<DayWeek> selectedWeekDays = new ArrayList<DayWeek>();
			if (selectedDays.contains(new String("0"))) {
				selectedWeekDays = dayWeekDao.listAllDayWeek();
			}
			else {
				for (String customerDay : selectedDays) {
					DayWeek dayWeek = dayWeekDao.findByName(customerDay);
					selectedWeekDays.add(dayWeek);
				}
			}
			updatedCustomer.setDaysList(selectedWeekDays);

			List<Product> selectedProductsList = new ArrayList<Product>();

			if (selectedProducts.contains(new String("0"))) {
				selectedProductsList = productDao.listAllproduct();
			}
			else {
				for (String productName : selectedProducts) {
					Product product = productDao.findByName(productName);
					selectedProductsList.add(product);
				}
			}
			updatedCustomer.setProductsList(selectedProductsList);
			customerDao.updateCustomer(updatedCustomer);
		}
		catch (Exception e) {

		}
		finally {
			Performance.releaseMemory();
		}

	}

	public void onRowCancel(RowEditEvent event) {
	}

}
