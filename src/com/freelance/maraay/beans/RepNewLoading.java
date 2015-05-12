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

import com.freelance.maraay.dao.RepFirstLoadingDao;
import com.freelance.maraay.dao.RepLastLoadingDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepFirstTimeDate;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.model.TblRepNewLoadingDate;
import com.freelance.maraay.model.TblRepNewLoadingValue;
import com.freelance.maraay.model.TblRepTotalLoadingDate;
import com.freelance.maraay.model.TblRepTotalLoadingValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class RepNewLoading implements Serializable {

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
	private TblRepNewLoadingDate newLoadingDate = new TblRepNewLoadingDate();
	private List<TblRepNewLoadingValue> newLoadingValues = new ArrayList<TblRepNewLoadingValue>();

	public List<TblRepNewLoadingValue> getNewLoadingValues() {
		return newLoadingValues;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public TblRepNewLoadingDate getNewLoadingDate() {
		return newLoadingDate;
	}

	public void setNewLoadingDate(TblRepNewLoadingDate newLoadingDate) {
		this.newLoadingDate = newLoadingDate;
	}

	public void prerendre(ComponentSystemEvent event) {
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblRepNewLoadingValue newLoadingValue = new TblRepNewLoadingValue();
			newLoadingValue.setProductId(product);
			newLoadingValues.add(newLoadingValue);
		}
	}

	public String insertNewLodaing() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();
			newLoadingDate.setByUserId(new User(loginBean.getId()));
			newLoadingDate.setDate(loginBean.getRepDailyDate());
			newLoadingDate.setDirectionId(new Direction(loginBean
					.getRepDirectionId()));
			session.persist(newLoadingDate);

			// insert total loading date
			TblRepTotalLoadingDate totalLoadingDate = new TblRepTotalLoadingDate();

			totalLoadingDate.setByUserId(new User(loginBean.getId()));
			totalLoadingDate.setDate(loginBean.getRepDailyDate());
			totalLoadingDate.setDirectionId(new Direction(loginBean
					.getRepDirectionId()));
			session.persist(totalLoadingDate);

			// retrieve first time loading date

			TblRepFirstTimeDate firstTimeDate = RepFirstLoadingDao
					.getInstance().findByDate(loginBean.getRepDailyDate(),
							loginBean.getRepDirectionId());

			double allTotalPrice = 0.0;
			for (TblRepNewLoadingValue loadingValue : newLoadingValues) {

				double maxPrice = loadingValue.getMaxMount()
						* loadingValue.getProductId().getRepMaxUnPrice();
				double minPrice = loadingValue.getMinMount()
						* loadingValue.getProductId().getRepMinUnPrice();
				double totalPrice = maxPrice + minPrice;
				String shown_mount = loadingValue.getMaxMount() + "."
						+ loadingValue.getMinMount();

				loadingValue.setMaxMountPrice(maxPrice);
				loadingValue.setMinMountPrice(minPrice);
				loadingValue.setTotalPrice(totalPrice);
				loadingValue.setShowenMount(shown_mount);
				loadingValue.setNewLoadingDateId(newLoadingDate);
				session = SessionFactoryUtil.getSession();
				session.persist(loadingValue);
				allTotalPrice = allTotalPrice + totalPrice;

				// insert total loading value
				TblRepFirstTimeValue firstTimeValue = RepFirstLoadingDao
						.getInstance().findByDateAndProduct(
								firstTimeDate.getFirstTimeDateId(),
								loadingValue.getProductId(),
								firstTimeDate.getDirectionId());

				TblRepTotalLoadingValue totalLoadingValue = new TblRepTotalLoadingValue();
				double maxPriceT = maxPrice + firstTimeValue.getMaxMountPrice();
				double minPriceT = minPrice + firstTimeValue.getMinMountPrice();

				int maxMountT = loadingValue.getMaxMount()
						+ firstTimeValue.getMaxMount();
				int minMountT = loadingValue.getMinMount()
						+ firstTimeValue.getMinMount();

				double totalPriceT = loadingValue.getTotalPrice()
						+ firstTimeValue.getTotalPrice();
				String shown_total_t = maxMountT + "." + minMountT;

				totalLoadingValue.setMaxMount(maxMountT);
				totalLoadingValue.setMaxMountPrice(maxPriceT);
				totalLoadingValue.setMinMount(minMountT);
				totalLoadingValue.setMinMountPrice(minPriceT);
				totalLoadingValue.setShowenMount(shown_total_t);
				totalLoadingValue.setTotalPrice(totalPriceT);
				totalLoadingValue.setTotalLoadingDateId(totalLoadingDate);
				totalLoadingValue.setProductId(loadingValue.getProductId());
				session = SessionFactoryUtil.getSession();
				session.persist(totalLoadingValue);

			}

			newLoadingDate.setTotal(allTotalPrice);
			newLoadingDate.setTblRepNewLoadingValueList(newLoadingValues);
			session = SessionFactoryUtil.getSession();
			session.update(newLoadingDate);

			// update total loading date
			double allTotalPriceT = allTotalPrice + firstTimeDate.getTotal();
			totalLoadingDate.setTotal(allTotalPriceT);
			session = SessionFactoryUtil.getSession();
			session.update(totalLoadingDate);

			tx = session.beginTransaction();
			tx.commit();
			return "newTotalLoading";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
}
