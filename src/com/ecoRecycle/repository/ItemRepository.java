package com.ecoRecycle.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ecoRecycle.helper.HibernateLoader;
import com.ecoRecycle.model.Item;

public class ItemRepository {
	
	/*To get item by id and returns an item object */
	public Item getItemById(int id) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		Item item = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Item.class);
			criteria.add(Restrictions.eq("id", id));
			item = (Item) criteria.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return item;
	}
	
	/*To get item object by type of the item*/
	public Item getItemByType(String type) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		Item item = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Item.class);
			criteria.add(Restrictions.eq("type", type));
			item = (Item) criteria.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return item;
	}
	
	/*To return the items that are valid*/
	public List<Item> getItemByValidity(boolean isValid) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		List<Item> items = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Item.class);
			criteria.add(Restrictions.eq("isValid", isValid));
			items = criteria.list();

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
	
	/*To get all the items*/
	public List<Item> getAllItems() {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		List<Item> items = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Item.class);
			items = criteria.list();

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
	
	/*to update the values in the item*/
	public boolean updateItem(Item item){
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		boolean isSuccessful = true;
		
		try {
			tx = session.beginTransaction();
			session.update(item);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			isSuccessful = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return isSuccessful;
	}

}
