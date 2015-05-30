package com.freelance.maraay.utils;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.freelance.maraay.dao.ComBlendingDao;
import com.freelance.maraay.dao.ComDefectDao;
import com.freelance.maraay.dao.ComIncomingDao;
import com.freelance.maraay.dao.ComOfferDao;
import com.freelance.maraay.dao.ComSalesDao;
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

public class AppUtils {

	public void deleteNonCompletedCom(
			List<TblComDiscountDate> checkedDiscountList) {
		if (checkedDiscountList.size() > 0 || checkedDiscountList != null) {
			Session session = null;
			Transaction tx = null;
			try {
				
				for (TblComDiscountDate disDate : checkedDiscountList) {
					session = SessionFactoryUtil.getSession();
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
				// TODO: handle exception
//				System.out.println("111111111111111111111111111");
//				e.printStackTrace();
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

}
