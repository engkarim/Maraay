package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.SessionFactoryUtil;

@ManagedBean
@ViewScoped
public class RepLast implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{productBean}")
	private ProductBean productBean;

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	private TblRepLastTimeDate lastTimeDate = new TblRepLastTimeDate();
	private List<TblRepLastTimeValue> lastTimeValues = new ArrayList<TblRepLastTimeValue>();

	private List<Product> allProducts;

	public TblRepLastTimeDate getLastTimeDate() {
		return lastTimeDate;
	}

	public void setLastTimeDate(TblRepLastTimeDate lastTimeDate) {
		this.lastTimeDate = lastTimeDate;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public List<TblRepLastTimeValue> getLastTimeValues() {
		return lastTimeValues;
	}

	@PostConstruct
	public void init() {
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblRepLastTimeValue lastTimeValue = new TblRepLastTimeValue();
			lastTimeValue.setProductId(product);
			lastTimeValues.add(lastTimeValue);
		}
	}

	public String insertNewLast() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			List<TblRepLastTimeValue> lastLastValues = new ArrayList<TblRepLastTimeValue>();

			double total = 0.0;

			// insert incoming date
			lastTimeDate.setByUserId(new User(loginBean.getId()));
			session.persist(lastTimeDate);

			// insert incoming values
			for (TblRepLastTimeValue lastValue : lastTimeValues) {
				// calculate mount price

				// get total mount

				double productPriceMax = lastValue.getProductId()
						.getRepMaxUnPrice();

				double mountPriceMax = productPriceMax
						* lastValue.getMaxMount();

				double productPriceMin = lastValue.getProductId()
						.getRepMinUnPrice();

				double mountPriceMin = productPriceMin
						* lastValue.getMinMount();
				// get total before discount
				double totalPrice = mountPriceMax + mountPriceMin;

				// get shown mount
				String shown_mount = lastValue.getMaxMount() + "."
						+ lastValue.getMinMount();

				lastValue.setShowenMount(shown_mount);
				lastValue.setMaxMountPrice(mountPriceMax);
				lastValue.setMinMountPrice(mountPriceMin);
				lastValue.setTotalPrice(totalPrice);
				lastValue.setLastTimeDateId(lastTimeDate);
				lastLastValues.add(lastValue);

				session = SessionFactoryUtil.getSession();
				session.persist(lastValue);

				total = total + totalPrice;

			}
			lastTimeDate.setTotal(total);
			lastTimeDate.setTblRepLastTimeValueList(lastLastValues);
			session.update(lastTimeDate);
			tx = session.beginTransaction();
			tx.commit();
			return "success";
		} catch (Exception e) {
			tx.rollback();
			return "fail";
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}
}
