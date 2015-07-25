package com.freelance.maraay.utils;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.ComBlendingDao;
import com.freelance.maraay.dao.ComDefectDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.ComOfferDao;
import com.freelance.maraay.dao.ComSalesDao;
import com.freelance.maraay.dao.RepBlendingDao;
import com.freelance.maraay.dao.RepDefectDao;
import com.freelance.maraay.dao.RepNewLoadingDao;
import com.freelance.maraay.dao.RepOfferDao;
import com.freelance.maraay.dao.RepSalesDao;
import com.freelance.maraay.dao.RepTotalLoadingDao;
import com.freelance.maraay.model.TblComBlendingDate;
import com.freelance.maraay.model.TblComBlendingValue;
import com.freelance.maraay.model.TblComDefectsDate;
import com.freelance.maraay.model.TblComDefectsValue;
import com.freelance.maraay.model.TblComDiscountDate;
import com.freelance.maraay.model.TblComDiscountValue;
import com.freelance.maraay.model.TblComIncomingDate;
import com.freelance.maraay.model.TblComIncomingValue;
import com.freelance.maraay.model.TblComOfferDate;
import com.freelance.maraay.model.TblComOfferValue;
import com.freelance.maraay.model.TblComSalesDate;
import com.freelance.maraay.model.TblComSalesValue;
import com.freelance.maraay.model.TblRepBlendingDate;
import com.freelance.maraay.model.TblRepBlendingValue;
import com.freelance.maraay.model.TblRepDefectDate;
import com.freelance.maraay.model.TblRepDefectValue;
import com.freelance.maraay.model.TblRepFirstTimeDate;
import com.freelance.maraay.model.TblRepFirstTimeValue;
import com.freelance.maraay.model.TblRepNewLoadingDate;
import com.freelance.maraay.model.TblRepNewLoadingValue;
import com.freelance.maraay.model.TblRepOfferDate;
import com.freelance.maraay.model.TblRepOfferValue;
import com.freelance.maraay.model.TblRepSalesDate;
import com.freelance.maraay.model.TblRepTotalLoadingDate;
import com.freelance.maraay.model.TblRepTotalLoadingValue;

public class AppUtils {

	public void deleteNonCompletedCom(
			List<TblComDiscountDate> checkedDiscountList) {
		if (checkedDiscountList.size() > 0 || checkedDiscountList != null) {
			Session session = null;
			Transaction tx = null;
			try {
				session = SessionFactoryUtil.getSession();
				for (TblComDiscountDate disDate : checkedDiscountList) {
					// delete date all values for discount
					deleteItemValues(disDate.getTblComDiscountValueList(), session);
					session.delete(disDate);
					// get and delete date and values incoming 
					TblComIncomingDate incomingDate = ComIncomingDao.getInstance().findByDate(disDate.getDate());
					if(incomingDate != null){
						deleteItemValues(incomingDate.getTblComIncomingValueList(), session);
						session = SessionFactoryUtil.getSession();
						session.delete(incomingDate);
						
						// get and delete date and values for offer
						TblComOfferDate offerDate = ComOfferDao.getInstance().findByDate(disDate.getDate());
						if(offerDate != null){
							deleteItemValues(offerDate.getTblComOfferValueList(), session);
							session = SessionFactoryUtil.getSession();
							session.delete(offerDate);
							
							//get and delete date and values for blending
							TblComBlendingDate blendingDate = ComBlendingDao.getInstance().findByDate(disDate.getDate());
							if(blendingDate != null){
								deleteItemValues(blendingDate.getTblComBlendingValueList(), session);
								session = SessionFactoryUtil.getSession();
								session.delete(blendingDate);
								
								// get and delete date and values for defect
								TblComDefectsDate defectsDate = ComDefectDao.getInstance().findByDate(disDate.getDate());
								if(defectsDate != null){
									deleteItemValues(defectsDate.getTblComDefectsValueList(), session);
									session = SessionFactoryUtil.getSession();
									session.delete(defectsDate);
									
									// get and delete date and values for sales
									TblComSalesDate salesDate = ComSalesDao.getInstance().findByDate(disDate.getDate());
									if(salesDate != null){
										deleteItemValues(salesDate.getTblComSalesValueList(), session);
										session = SessionFactoryUtil.getSession();
										session.delete(salesDate);
									}
								}
							}
						}
					}
					tx = session.beginTransaction();
					tx.commit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(session != null){
					if (session.isOpen()){
						session.close();
					}
				}
				tx = null;
			}
		}

	}
	
	public void deleteNonCompletedRep(
			List<TblRepFirstTimeDate> checkedFirstList) {
		if (checkedFirstList.size() > 0 || checkedFirstList != null) {
			Session session = null;
			Transaction tx = null;
			try {
				session = SessionFactoryUtil.getSession();
				for (TblRepFirstTimeDate firDate : checkedFirstList) {
					// delete date all values for discount
					deleteItemValuesRep(firDate.getTblRepFirstTimeValueList(), session);
					session.delete(firDate);
					// get and delete date and values NewLoading
					TblRepNewLoadingDate newLoadingdate = RepNewLoadingDao.getInstance().findByDate(firDate.getDate() , firDate.getDirectionId().getId());
					if(newLoadingdate != null){
						deleteItemValuesRep(newLoadingdate.getTblRepNewLoadingValueList(), session);
						session = SessionFactoryUtil.getSession();
						session.delete(newLoadingdate);
						
						// get and delete date and values for totalOffer
						TblRepTotalLoadingDate totatlDate = RepTotalLoadingDao.getInstance().findByDate(firDate.getDate() , firDate.getDirectionId().getId());
						if(totatlDate != null){
							deleteItemValuesRep(totatlDate.getTblRepTotalLoadingValueList(), session);
							session = SessionFactoryUtil.getSession();
							session.delete(totatlDate);
							
							//get and delete date and values for blending
							TblRepBlendingDate blendingDate = RepBlendingDao.getInstance().findByDate(firDate.getDate() , firDate.getDirectionId().getId());
							if(blendingDate != null){
								deleteItemValuesRep(blendingDate.getTblRepBlendingValueList(), session);
								session = SessionFactoryUtil.getSession();
								session.delete(blendingDate);
								
								// get and delete date and values for defect
								TblRepDefectDate defectsDate = RepDefectDao.getInstance().findByDate(firDate.getDate() , firDate.getDirectionId().getId());
								if(defectsDate != null){
									deleteItemValuesRep(defectsDate.getTblRepDefectValueList(), session);
									session = SessionFactoryUtil.getSession();
									session.delete(defectsDate);
									
									// get and delete date and values for Offer
									TblRepOfferDate offerDate = RepOfferDao.getInstance().findByDate(firDate.getDate() , firDate.getDirectionId().getId());
									if(offerDate != null){
										deleteItemValuesRep(offerDate.getTblRepOfferValueList(), session);
										session = SessionFactoryUtil.getSession();
										session.delete(offerDate);
										
										// get and delete date and values for sales
										TblRepSalesDate salesDate = RepSalesDao.getInstance().findByDate(firDate.getDate() , firDate.getDirectionId().getId());
										if(salesDate != null){
											deleteItemValuesRep(salesDate.getTblRepSalesValueList(), session);
											session = SessionFactoryUtil.getSession();
											session.delete(salesDate);
										}
									}
								}
							}
						}
					}
					tx = session.beginTransaction();
					tx.commit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(session != null){
					if (session.isOpen()){
						session.close();
					}
				}
				tx = null;
			}
		}

	}

	public void deleteItemValues(List<?> valuesList , Session session) {
		if (valuesList.size() > 0 || valuesList != null) {
			try {
				for (Object o : valuesList) {
					if (o.getClass().equals(TblComDiscountValue.class)) {
						session.delete((TblComDiscountValue) o);
					}else if (o.getClass().equals(TblComIncomingValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblComIncomingValue) o);
					} 
					else if (o.getClass().equals(TblComOfferValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblComOfferValue) o);
					} else if (o.getClass().equals(TblComBlendingValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblComBlendingValue) o);
					} else if (o.getClass().equals(TblComDefectsValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblComDefectsValue) o);
					} else if (o.getClass().equals(TblComSalesValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblComSalesValue) o);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} 
		}
	}
	
	public void deleteItemValuesRep(List<?> valuesList , Session session) {
		if (valuesList.size() > 0 || valuesList != null) {
			try {
				for (Object o : valuesList) {
					if (o.getClass().equals(TblRepFirstTimeValue.class)) {
						session.delete((TblRepFirstTimeValue) o);
					}else if (o.getClass().equals(TblRepNewLoadingValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblRepNewLoadingValue) o);
					} 
					else if (o.getClass().equals(TblRepTotalLoadingValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblRepTotalLoadingValue) o);
					} else if (o.getClass().equals(TblRepBlendingValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblRepBlendingValue) o);
					} else if (o.getClass().equals(TblRepDefectValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblRepDefectValue) o);
					} else if (o.getClass().equals(TblRepOfferValue.class)) {
						session = SessionFactoryUtil.getSession();
						session.delete((TblRepOfferValue) o);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} 
		}
	}

}
