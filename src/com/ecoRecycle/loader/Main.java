package com.ecoRecycle.loader;

// TO be moved away from here

import org.hibernate.HibernateException;
import org.hibernate.Session; 
import org.hibernate.Transaction;

import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.model.Administrator;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.repository.AdministratorRepository;
import com.ecoRecycle.repository.ItemRepository;
import com.ecoRecycle.repository.LocationRepository;
import com.ecoRecycle.repository.RcmRepository;
import com.ecoRecycle.repository.RmosRepository;

public class Main
{
    public static void main(String[] args) {
    	
    	Main main = new Main();

        /* Add few employee records in database */
    	
    	/*LocationRepository locRepo = new LocationRepository();
    	Integer locId = locRepo.addLocation("Santa Clara");
        System.out.println("Location created --- " + locId);
        
        Integer locId1 = locRepo.addLocation("Sunnyvale");
        System.out.println("Location created --- " + locId1);
        
        locRepo.getLocation(1);
        locRepo.getAllLocations();
        Location l1 = locRepo.getLocationByName("Santa Clara");
        System.out.println("Location retrived --- " + l1.getCity() + l1.getId() );*/
        
        
    	RcmRepository rcmRepo = new RcmRepository();
    	Integer rcmId = rcmRepo.addRcm("FirstRcm", "Santa Clara", 10, 100);
    	System.out.println("RCM created --- " + rcmId);
    	
    	rcmRepo.getAllRcm();
    	
    	
    	/*ItemRepository itemRepo = new ItemRepository();
    	Integer typeId = itemRepo.addItem("Aluminium");
    	System.out.println("Item added --- " + typeId);
    	
    	
    	Integer typeId1 = itemRepo.addItem("Glass");
    	System.out.println("Item added --- " + typeId1);
       
    	itemRepo.getItem(1);
    	itemRepo.getItem(2);
    	itemRepo.getItem(10);
    	
    	itemRepo.getAllItems();
    	
    	AdministratorRepository adminRepo = new AdministratorRepository();
    	Integer adminId = adminRepo.addUser("Lakshitha" , "Lakshitha123");
    	System.out.println("Admin Created --- " + adminId);
    	adminRepo.getUser("lakshitha", "lakshitha123");
    	adminRepo.getUser("lakshitha", "lakshitha");
        
        RmosRepository rmosRepo = new RmosRepository();
        Integer rmosId = rmosRepo.addRmos("LeadRmos");
        System.out.println("Rmos Created --- " + rmosId);*/
        
    }	
    
    
    
    
    
    /*public Integer addRcm(){
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = null;
        Integer rcmId = null;
        try{
           tx = session.beginTransaction();
           Rcm rcm = new Rcm();
           rcm.setName("RCMTest7");
           
           //rcm.setCity("Sunnyvale");

           Location loc = (Location)session.load(Location.class, 1);
           rcm.setLocation(loc);
           
           rcm.setTotalCapacity(10);
           rcm.setTotalCashValue(1000);
           
           rcm.setStatus(RcmStatus.ACTIVE);
           
          
           
           rcmId = (Integer) session.save(rcm); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return rcmId;
     }*/
}