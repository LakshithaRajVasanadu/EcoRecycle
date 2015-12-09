package com.ecoRecycle.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ecoRecycle.helper.HibernateLoader;
import com.ecoRecycle.model.Administrator;

public class AdministratorRepository {

	/*Function to get the username of the administrator*/
	public Administrator getAdmin(String username) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		Administrator admin = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Administrator.class);
			criteria.add(Restrictions.eq("username", username));
			admin = (Administrator) criteria.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return admin;
	}
	
}
