package com.freelance.maraay.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.RepBlendingDao;
import com.freelance.maraay.dao.RepFirstLoadingDao;
import com.freelance.maraay.dao.RepLastLoadingDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepBlendingDate;
import com.freelance.maraay.model.TblRepBlendingValue;
import com.freelance.maraay.model.TblRepFirstTimeDate;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.model.TblRepNewLoadingDate;
import com.freelance.maraay.model.TblRepNewLoadingValue;
import com.freelance.maraay.model.TblRepTotalLoadingDate;
import com.freelance.maraay.model.TblRepTotalLoadingValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class RepBlending implements Serializable {

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

	private List<TblRepBlendingValue> blendingValues = new ArrayList<TblRepBlendingValue>();

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public List<TblRepBlendingValue> getBlendingValues() {
		return blendingValues;
	}

	public void prerendre(ComponentSystemEvent event) {
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblRepBlendingValue blendingValue = new TblRepBlendingValue();
			blendingValue.setProductId(product);
			blendingValues.add(blendingValue);
		}
	}

	public String insertNewBlending() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			TblRepBlendingDate blendingDate = new TblRepBlendingDate();
			blendingDate.setByUserId(new User(loginBean.getId()));
			blendingDate.setDate(loginBean.getRepDailyDate());
			blendingDate.setDirectionId(new Direction(loginBean
					.getRepDirectionId()));
			session.persist(blendingDate);

			double allTotalPrice = 0.0;
			for (TblRepBlendingValue blendingValue : blendingValues) {

				double maxPrice = blendingValue.getMaxMount()
						* blendingValue.getProductId().getRepMaxUnPrice();
				double minPrice = blendingValue.getMinMount()
						* blendingValue.getProductId().getRepMinUnPrice();
				double totalPrice = maxPrice + minPrice;
				String shown_mount = blendingValue.getMaxMount() + "."
						+ blendingValue.getMinMount();

				blendingValue.setMaxMountPrice(maxPrice);
				blendingValue.setMinMountPrice(minPrice);
				blendingValue.setTotalPrice(totalPrice);
				blendingValue.setShowenMount(shown_mount);
				blendingValue.setBlendingDateId(blendingDate);
				session = SessionFactoryUtil.getSession();
				session.persist(blendingValue);
				allTotalPrice = allTotalPrice + totalPrice;
			}

			blendingDate.setTotal(allTotalPrice);
			blendingDate.setTblRepBlendingValueList(blendingValues);
			session = SessionFactoryUtil.getSession();
			session.update(blendingDate);

			tx = session.beginTransaction();
			tx.commit();
			return "repNewDefect";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
	
	/********************* update logic **********************/

	TblRepBlendingDate updateBlendingDate = new TblRepBlendingDate();

	public TblRepBlendingDate getUpdateBlendingDate() {
		return updateBlendingDate;
	}

	public void setUpdateBlendingDate(TblRepBlendingDate updateBlendingDate) {
		this.updateBlendingDate = updateBlendingDate;
	}

	public void prerenderUpdate(ComponentSystemEvent event) {
		TblRepBlendingDate	searchedDate = RepBlendingDao.getInstance().findByDate(
				loginBean.getUpdateRepDailyDate() , loginBean.getUpdateRepDirectionId());

		if (searchedDate == null) {
			Utils.getInstance().sendRedirect(Constants.loginPage, false);
		} else {
			setUpdateBlendingDate(searchedDate);
		}
	}
	
	
	public String updateBlending() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			updateBlendingDate.setByUserId(new User(loginBean.getId()));
			updateBlendingDate.setDate(loginBean.getUpdateRepDailyDate());
			updateBlendingDate.setDirectionId(new Direction(loginBean.getUpdateRepDirectionId()));
			session.update(updateBlendingDate);

			double allTotalPrice = 0.0;
			for (TblRepBlendingValue blendingValue : updateBlendingDate.getTblRepBlendingValueList()) {

				double maxPrice = blendingValue.getMaxMount()
						* blendingValue.getProductId().getRepMaxUnPrice();
				double minPrice = blendingValue.getMinMount()
						* blendingValue.getProductId().getRepMinUnPrice();
				double totalPrice = maxPrice + minPrice;
				String shown_mount = blendingValue.getMaxMount() + "."
						+ blendingValue.getMinMount();

				blendingValue.setMaxMountPrice(maxPrice);
				blendingValue.setMinMountPrice(minPrice);
				blendingValue.setTotalPrice(totalPrice);
				blendingValue.setShowenMount(shown_mount);
				blendingValue.setBlendingDateId(updateBlendingDate);
				session = SessionFactoryUtil.getSession();
				session.update(blendingValue);
				allTotalPrice = allTotalPrice + totalPrice;
			}

			updateBlendingDate.setTotal(allTotalPrice);
			session = SessionFactoryUtil.getSession();
			session.saveOrUpdate(updateBlendingDate);

			tx = session.beginTransaction();
			tx.commit();
			return "repUpdateDefect";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
	
}
