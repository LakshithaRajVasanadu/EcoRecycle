package com.ecoRecycle.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ecoRecycle.helper.HibernateLoader;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.model.RmosRcmMapping;

public class RmosRepository {
	
	/*To get all rmos */
	public List<Rmos> getAllRmos() {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		List<Rmos> rmosList = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Rmos.class);
			rmosList = criteria.list();
		
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rmosList;
	} 
	
	/*To get the rmos by id, returns an rmos object*/
	public Rmos getRmosById(int id) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		Rmos rmos = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Rmos.class);
			criteria.add(Restrictions.eq("id", id));
			rmos= (Rmos) criteria.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rmos;
	}
	
	/*To get rmos by name, returns an rmos object*/
	public Rmos getRmosByName(String name) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		Rmos rmos = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Rmos.class);
			criteria.add(Restrictions.eq("name", name));
			rmos= (Rmos) criteria.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rmos;
	}
	/*To update a rmos*/
	public boolean updateRmos(Rmos rmos){
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		boolean isSuccessful = true;
		
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(rmos);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			isSuccessful = false;
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return isSuccessful;
	}
	
	/*to get a list of rcm's that are mapped to a particular rmos*/
	public RmosRcmMapping getMappingForRcm(Rmos rmos, Rcm rcm) {
		Set<RmosRcmMapping> mappings = rmos.getRmosRcmMappings();
		RmosRcmMapping result = null;

		Iterator<RmosRcmMapping> iter = mappings.iterator();
		while (iter.hasNext()) {
			RmosRcmMapping mapping = iter.next();
			
		    if(mapping.getRcm().getId() == rcm.getId()) {
		    	result = mapping;
		    	break;
		    }
		}
		return result;
	}

}
