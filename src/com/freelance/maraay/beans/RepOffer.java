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

import com.freelance.maraay.dao.RepBlendingDao;
import com.freelance.maraay.dao.RepDefectDao;
import com.freelance.maraay.dao.RepLastLoadingDao;
import com.freelance.maraay.dao.RepOfferDao;
import com.freelance.maraay.dao.RepSalesDao;
import com.freelance.maraay.dao.RepTotalLoadingDao;
import com.freelance.maraay.model.Direction;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblRepBlendingValue;
import com.freelance.maraay.model.TblRepDefectDate;
import com.freelance.maraay.model.TblRepDefectValue;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.model.TblRepOfferDate;
import com.freelance.maraay.model.TblRepOfferValue;
import com.freelance.maraay.model.TblRepSalesDate;
import com.freelance.maraay.model.TblRepSalesValue;
import com.freelance.maraay.model.TblRepTotalLoadingValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.Constants;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

@ManagedBean
@ViewScoped
public class RepOffer implements Serializable {

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

	private List<TblRepOfferValue> offerValues = new ArrayList<TblRepOfferValue>();

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public List<TblRepOfferValue> getOfferValues() {
		return offerValues;
	}

	public void prerendre(ComponentSystemEvent event) {
		allProducts = productBean.getProducts();
		for (Product product : allProducts) {
			TblRepOfferValue offerValue = new TblRepOfferValue();
			offerValue.setProductId(product);
			offerValues.add(offerValue);
		}
	}

	public String insertNewOffer() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionFactoryUtil.getSession();

			TblRepOfferDate offerDate = new TblRepOfferDate();
			offerDate.setByUserId(new User(loginBean.getId()));
			offerDate.setDate(loginBean.getRepDailyDate());
			offerDate.setDirectionId(new Direction(loginBean
					.getRepDirectionId()));
			session.persist(offerDate);

			// insert rep sales adate

			session = SessionFactoryUtil.getSession();

			TblRepSalesDate salesDate = new TblRepSalesDate();
			salesDate.setByUserId(new User(loginBean.getId()));
			salesDate.setDate(loginBean.getRepDailyDate());
			salesDate.setDirectionId(new Direction(loginBean
					.getRepDirectionId()));
			session.persist(salesDate);

			double allTotalPrice = 0.0;
			double allTotalPriceS = 0.0;
			for (TblRepOfferValue offerValue : offerValues) {

				double maxPrice = offerValue.getMaxMount()
						* offerValue.getProductId().getRepMaxUnPrice();
				double minPrice = offerValue.getMinMount()
						* offerValue.getProductId().getRepMinUnPrice();
				double totalPrice = maxPrice + minPrice;
				String shown_mount = offerValue.getMaxMount() + "."
						+ offerValue.getMinMount();

				offerValue.setMaxMountPrice(maxPrice);
				offerValue.setMinMountPrice(minPrice);
				offerValue.setTotalPrice(totalPrice);
				offerValue.setShowenMount(shown_mount);
				offerValue.setOfferDateId(offerDate);
				session = SessionFactoryUtil.getSession();
				session.persist(offerValue);
				allTotalPrice = allTotalPrice + totalPrice;

				/************** get values from DBs ****************/

				TblRepTotalLoadingValue totalLoadingValue = RepTotalLoadingDao
						.getInstance().findByDateAndProduct(
								loginBean.getRepDailyDate(),
								offerValue.getProductId(),
								new Direction(loginBean.getRepDirectionId()));

				TblRepBlendingValue blendingValue = RepBlendingDao
						.getInstance().findByDateAndProduct(
								loginBean.getRepDailyDate(),
								offerValue.getProductId(),
								new Direction(loginBean.getRepDirectionId()));

				TblRepDefectValue defectValue = RepDefectDao.getInstance()
						.findByDateAndProduct(loginBean.getRepDailyDate(),
								offerValue.getProductId(),
								new Direction(loginBean.getRepDirectionId()));

				TblRepLastTimeValue lastTimeValue = RepLastLoadingDao
						.getInstance().findByDateAndProduct(
								Utils.getInstance().decrementDate(
										loginBean.getRepDailyDate()),
								offerValue.getProductId(),
								new Direction(loginBean.getRepDirectionId()));

				// insert sales values

				TblRepSalesValue salesValue = new TblRepSalesValue();

				int maxMountS = totalLoadingValue.getMaxMount()
						- (blendingValue.getMaxMount()
								+ defectValue.getMaxMount()
								+ offerValue.getMaxMount() + lastTimeValue
									.getMaxMount());
				int minMountS = totalLoadingValue.getMinMount()
						- (blendingValue.getMinMount()
								+ defectValue.getMinMount()
								+ offerValue.getMinMount() + lastTimeValue
									.getMinMount());
				double maxPriceS = totalLoadingValue.getMaxMountPrice()
						- (blendingValue.getMaxMountPrice()
								+ defectValue.getMaxMountPrice()
								+ offerValue.getMaxMountPrice() + lastTimeValue
									.getMaxMountPrice());
				double minPriceS = totalLoadingValue.getMinMountPrice()
						- (blendingValue.getMinMountPrice()
								+ defectValue.getMinMountPrice()
								+ offerValue.getMinMountPrice() + lastTimeValue
									.getMinMountPrice());
				double totalPriceS = totalLoadingValue.getTotalPrice()
						- (blendingValue.getTotalPrice()
								+ defectValue.getTotalPrice()
								+ offerValue.getTotalPrice() + lastTimeValue
									.getTotalPrice());
				String showenMountS = maxMountS + "." + minMountS;

				salesValue.setMaxMount(maxMountS);
				salesValue.setMinMount(minMountS);
				salesValue.setMaxMountPrice(maxPriceS);
				salesValue.setMinMountPrice(minPriceS);
				salesValue.setTotalPrice(totalPriceS);
				salesValue.setShowenMount(showenMountS);
				salesValue.setProductId(offerValue.getProductId());
				salesValue.setSalesDateId(salesDate);

				session = SessionFactoryUtil.getSession();
				session.persist(salesValue);

				allTotalPriceS = allTotalPriceS + totalPriceS;

			}

			offerDate.setTotal(allTotalPrice);
			offerDate.setTblRepOfferValueList(offerValues);
			session = SessionFactoryUtil.getSession();
			session.update(offerDate);

			// update rep sales date
			salesDate.setTotal(allTotalPriceS);
			session = SessionFactoryUtil.getSession();
			session.update(salesDate);

			tx = session.beginTransaction();
			tx.commit();
			return "success";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
	
	/********************* update logic **********************/

	TblRepOfferDate  updateOfferDate = new TblRepOfferDate();

	public TblRepOfferDate getUpdateOfferDate() {
		return updateOfferDate;
	}

	public void setUpdateOfferDate(TblRepOfferDate updateOfferDate) {
		this.updateOfferDate = updateOfferDate;
	}

	public void prerenderUpdate(ComponentSystemEvent event) {
		TblRepOfferDate	searchedDate = RepOfferDao.getInstance().findByDate(
				loginBean.getUpdateRepDailyDate() , loginBean.getUpdateRepDirectionId());

		if (searchedDate == null) {
			Utils.getInstance().sendRedirect(Constants.loginPage, false);
		} else {
			setUpdateOfferDate(searchedDate);
		}
	}
	
	public String updateOffer() {
		Session session = null;
		Transaction tx = null;
		try {
			updateOfferDate.setByUserId(new User(loginBean.getId()));
			updateOfferDate.setDate(loginBean.getUpdateRepDailyDate());
			updateOfferDate.setDirectionId(new Direction( loginBean.getUpdateRepDirectionId()));
			session = SessionFactoryUtil.getSession();
			session.update(updateOfferDate);

			// get and update sales date 
			TblRepSalesDate salesDate = RepSalesDao.getInstance().findByDate(loginBean.getUpdateRepDailyDate(), loginBean.getUpdateRepDirectionId());
			salesDate.setByUserId(new User(loginBean.getId()));
			salesDate.setDate(loginBean.getUpdateRepDailyDate());
			salesDate.setDirectionId(new Direction(loginBean.getUpdateRepDirectionId()));
			session = SessionFactoryUtil.getSession();
			session.update(salesDate);
			double allTotalPrice = 0.0;
			double allTotalPriceS = 0.0;
			for (TblRepOfferValue offerValue : updateOfferDate.getTblRepOfferValueList()) {

				double maxPrice = offerValue.getMaxMount()
						* offerValue.getProductId().getRepMaxUnPrice();
				double minPrice = offerValue.getMinMount()
						* offerValue.getProductId().getRepMinUnPrice();
				double totalPrice = maxPrice + minPrice;
				String shown_mount = offerValue.getMaxMount() + "."
						+ offerValue.getMinMount();

				offerValue.setMaxMountPrice(maxPrice);
				offerValue.setMinMountPrice(minPrice);
				offerValue.setTotalPrice(totalPrice);
				offerValue.setShowenMount(shown_mount);
				offerValue.setOfferDateId(updateOfferDate);
				session = SessionFactoryUtil.getSession();
				session.update(offerValue);
				allTotalPrice = allTotalPrice + totalPrice;

				/************** get values from DBs ****************/

				TblRepTotalLoadingValue totalLoadingValue = RepTotalLoadingDao
						.getInstance().findByDateAndProduct(
								loginBean.getUpdateRepDailyDate(),
								offerValue.getProductId(),
								new Direction(loginBean.getUpdateRepDirectionId()));

				TblRepBlendingValue blendingValue = RepBlendingDao
						.getInstance().findByDateAndProduct(
								loginBean.getUpdateRepDailyDate(),
								offerValue.getProductId(),
								new Direction(loginBean.getUpdateRepDirectionId()));

				TblRepDefectValue defectValue = RepDefectDao.getInstance()
						.findByDateAndProduct(loginBean.getUpdateRepDailyDate(),
								offerValue.getProductId(),
								new Direction(loginBean.getUpdateRepDirectionId()));

				TblRepLastTimeValue lastTimeValue = RepLastLoadingDao
						.getInstance().findByDateAndProduct(
								Utils.getInstance().decrementDate(
										loginBean.getUpdateRepDailyDate()),
								offerValue.getProductId(),
								new Direction(loginBean.getUpdateRepDirectionId()));

				// insert sales values

				TblRepSalesValue salesValue = RepSalesDao.getInstance().findByproductId(offerValue.getProductId().getId());

				int maxMountS = totalLoadingValue.getMaxMount()
						- (blendingValue.getMaxMount()
								+ defectValue.getMaxMount()
								+ offerValue.getMaxMount() + lastTimeValue
									.getMaxMount());
				int minMountS = totalLoadingValue.getMinMount()
						- (blendingValue.getMinMount()
								+ defectValue.getMinMount()
								+ offerValue.getMinMount() + lastTimeValue
									.getMinMount());
				double maxPriceS = totalLoadingValue.getMaxMountPrice()
						- (blendingValue.getMaxMountPrice()
								+ defectValue.getMaxMountPrice()
								+ offerValue.getMaxMountPrice() + lastTimeValue
									.getMaxMountPrice());
				double minPriceS = totalLoadingValue.getMinMountPrice()
						- (blendingValue.getMinMountPrice()
								+ defectValue.getMinMountPrice()
								+ offerValue.getMinMountPrice() + lastTimeValue
									.getMinMountPrice());
				double totalPriceS = totalLoadingValue.getTotalPrice()
						- (blendingValue.getTotalPrice()
								+ defectValue.getTotalPrice()
								+ offerValue.getTotalPrice() + lastTimeValue
									.getTotalPrice());
				String showenMountS = maxMountS + "." + minMountS;

				salesValue.setMaxMount(maxMountS);
				salesValue.setMinMount(minMountS);
				salesValue.setMaxMountPrice(maxPriceS);
				salesValue.setMinMountPrice(minPriceS);
				salesValue.setTotalPrice(totalPriceS);
				salesValue.setShowenMount(showenMountS);
				salesValue.setProductId(offerValue.getProductId());
				salesValue.setSalesDateId(salesDate);

				session = SessionFactoryUtil.getSession();
				session.update(salesValue);

				allTotalPriceS = allTotalPriceS + totalPriceS;

			}
			updateOfferDate.setTotal(allTotalPrice);
			session = SessionFactoryUtil.getSession();
			session.update(updateOfferDate);
			// update rep sales date
			salesDate.setTotal(allTotalPriceS);
			session = SessionFactoryUtil.getSession();
			session.update(salesDate);
			tx = session.beginTransaction();
			tx.commit();
			return "success";
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}

	}
}
