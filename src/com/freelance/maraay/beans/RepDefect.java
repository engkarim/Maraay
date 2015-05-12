package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepDefectDate;
import com.freelance.maraay.model.TblRepDefectValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.SessionFactoryUtil;

@ManagedBean
@ViewScoped
public class RepDefect implements Serializable {

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

	private List<Product> allProducts;

	private List<TblRepDefectValue> defectValues = new ArrayList<TblRepDefectValue>();

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public List<TblRepDefectValue> getDefectValues() {
		return defectValues;
	}

	public void prerendre(ComponentSystemEvent event) {
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblRepDefectValue defectValue = new TblRepDefectValue();
			defectValue.setProductId(product);
			defectValues.add(defectValue);
		}
	}

	public String insertNewDefect() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			TblRepDefectDate defectDate = new TblRepDefectDate();
			defectDate.setByUserId(new User(loginBean.getId()));
			defectDate.setDate(loginBean.getRepDailyDate());
			defectDate.setDirectionId(new Direction(loginBean
					.getRepDirectionId()));
			session.persist(defectDate);

			double allTotalPrice = 0.0;
			for (TblRepDefectValue defectValue : defectValues) {

				double maxPrice = defectValue.getMaxMount()
						* defectValue.getProductId().getRepMaxUnPrice();
				double minPrice = defectValue.getMinMount()
						* defectValue.getProductId().getRepMinUnPrice();
				double totalPrice = maxPrice + minPrice;
				String shown_mount = defectValue.getMaxMount() + "."
						+ defectValue.getMinMount();

				defectValue.setMaxMountPrice(maxPrice);
				defectValue.setMinMountPrice(minPrice);
				defectValue.setTotalPrice(totalPrice);
				defectValue.setShowenMount(shown_mount);
				defectValue.setDefectDateId(defectDate);
				session = SessionFactoryUtil.getSession();
				session.persist(defectValue);
				allTotalPrice = allTotalPrice + totalPrice;
			}

			defectDate.setTotal(allTotalPrice);
			defectDate.setTblRepDefectValueList(defectValues);
			session = SessionFactoryUtil.getSession();
			session.update(defectDate);

			tx = session.beginTransaction();
			tx.commit();
			return "repNewOffer";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
}
