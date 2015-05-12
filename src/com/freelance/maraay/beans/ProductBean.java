package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.freelance.maraay.model.Product;
import com.freelance.maraay.dao.ProductDao;
import com.freelance.maraay.model.Unit;
import com.freelance.maraay.primeFacesService.Service;
import com.freelance.maraay.utils.Performance;
import com.freelance.maraay.utils.Utils;

@ManagedBean(name = "productBean")
@ViewScoped
public class ProductBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Product product = new Product();
	ProductDao productDao = ProductDao.getInstance();
	Utils utils = Utils.getInstance();
	private List<Product> products;
	private List<Product> filteredProducts;

	@ManagedProperty("#{service}")
	private Service service;

	@PostConstruct
	public void init() {
		products = service.findAllProducts();
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Product> getProducts() {
		return products;
	}

	public List<Product> getFilteredProducts() {
		return filteredProducts;
	}

	public void setFilteredProducts(List<Product> filteredProducts) {
		this.filteredProducts = filteredProducts;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String addProduct() {
		try {
			Date now = utils.getCurrentTimeStamp();
			product.setAddingDate(now);
			product.setMaxUnit(new Unit(1));
			product.setMinUnit(new Unit(2));
			Double minUnitPrice = (Double) product.getMaxUnitPrice()
					/ product.getMaxMinCount();
			product.setMinUnitPrice(minUnitPrice);

			Double repMinUnitPrice = (Double) product.getRepMaxUnPrice()
					/ product.getMaxMinCount();
			product.setRepMinUnPrice(repMinUnitPrice);
			System.out.println(product.getName());
			productDao.insertProduct(product);
			return "allProducts";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}

	}

	public String deleteProduct(Product product) {
		try {
			productDao.deleteProduct(product);
			return "allProducts";
		} catch (Exception e) {
			return "fail";
		} finally {
			Performance.releaseMemory();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			Product updatedProduct = (Product) event.getObject();
			Date now = utils.getCurrentTimeStamp();
			updatedProduct.setAddingDate(now);
			Double minUnitPrice = (Double) updatedProduct.getMaxUnitPrice()
					/ updatedProduct.getMaxMinCount();
			Double repMinUnitPrice = (Double) updatedProduct.getRepMaxUnPrice()
					/ updatedProduct.getMaxMinCount();
			updatedProduct.setMinUnitPrice(minUnitPrice);
			updatedProduct.setRepMinUnPrice(repMinUnitPrice);
			productDao.updateProduct(updatedProduct);
		} catch (Exception e) {
		} finally {
			Performance.releaseMemory();
		}

	}

	public void onRowCancel(RowEditEvent event) {
	}

}
