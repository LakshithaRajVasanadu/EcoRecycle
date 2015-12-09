package com.ecoRecycle.helper;

import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.ecoRecycle.model.*;

//To get the hibernate session object
public class HibernateLoader {
	private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static
    {
        try
        {
        	java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF); // Might want to redirect logging (Logger)
            Configuration configuration = new Configuration().configure().addAnnotatedClass(Location.class)
            				.addAnnotatedClass(Item.class)
            				.addAnnotatedClass(Administrator.class)
            				.addAnnotatedClass(Rcm.class)
            				.addAnnotatedClass(Transaction.class)
            				.addAnnotatedClass(Rmos.class)
            				.addAnnotatedClass(RmosRcmMapping.class)
            				.addAnnotatedClass(TransactionItem.class)
            				.addAnnotatedClass(Location.class);

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (HibernateException he)
        {
            System.err.println("Error creating Session: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    } 

}
