package com.freelance.maraay.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.RepLastLoadingDao;
import com.freelance.maraay.model.Product;
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblRepLastTimeDate;
import com.freelance.maraay.model.TblRepLastTimeValue;
import com.freelance.maraay.model.User;
import com.freelance.maraay.utils.SessionFactoryUtil;
import com.freelance.maraay.utils.Utils;

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

	public void validateDate(ComponentSystemEvent event) throws ParseException {
		// get access to resource bundle
		String baseName = "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, FacesContext
				.getCurrentInstance().getViewRoot().getLocale());
		String duplicatedDateMsg = bundle.getString("DUPLICATEDATE");
		String InvalidDateMsg = bundle.getString("INVALIDDATE");

		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();
		
		// get direction
		UIInput uiInputDir = (UIInput) components.findComponent("customerDirection");
		Integer dir = (Integer) uiInputDir.getLocalValue();
		

		// get Date
		UIInput uiInputDate = (UIInput) components.findComponent("today");
		Date todayDate = (Date) uiInputDate.getLocalValue();
		String todayId = uiInputDate.getClientId();

		Date today = Utils.getInstance().getCurrentDate();

		TblRepLastTimeDate searchedDate = RepLastLoadingDao
				.getInstance().findByDateNoJoins(todayDate, dir);

		// Let required="true" do its job.
		if (todayDate == null) {
			return;
		}

		if (searchedDate != null) {
			FacesMessage msg = new FacesMessage(duplicatedDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();

		}

		if (todayDate.after(today)) {
			FacesMessage msg = new FacesMessage(InvalidDateMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todayId, msg);
			fc.renderResponse();
		}
	}
}
