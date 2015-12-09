package com.ecoRecycle.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ecoRecycle.helper.HibernateLoader;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.TransactionItem;

public class TransactionItemRepository {
	
	/*To add an item to the stated transaction*/
	public void addItemTransaction(int id, int transId, double weight, double payment){
		ItemRepository item = new ItemRepository();
		Transaction t = new Transaction();
		item.getItemById(id);
		t.setId(transId);
		t.setTotalWeight(weight);
		t.setTotalPayment(payment);
		
		
	}
	
	/*To get all the items recycled in that transaction*/
	public ArrayList<TransactionItem> getAllTranscationItemById(int transid){
		
		Session session = HibernateLoader.getSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		ArrayList<TransactionItem> items = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(TransactionItem.class);
			criteria.add(Restrictions.eq("transactionId", transid));
			items = (ArrayList<TransactionItem>) criteria.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return items;
	}

}
