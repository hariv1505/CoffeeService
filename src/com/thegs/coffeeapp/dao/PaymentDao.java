package com.thegs.coffeeapp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.thegs.coffeeapp.model.Payment;



public class PaymentDao {
    
    private static final Logger log = Logger.getLogger( ClassName.class.getName() );
    private SessionFactory sessionFactory;

    public PaymentDao() {
    	
    	log.info("Trying to create a connection with the database");
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
        log.info("Connection with the database created successfuly");

    	//not created yet
        //Payment p = new Payment("3", "card", "3.20");
		//p.setCardDetails("9874654321");
        //contentStore.put(p.getId(), p);
    }
    
    public void addPayment(Payment pay) {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();
	
	    //parameter checks
	
	    session.save(pay);
	    session.getTransaction().commit();
	    session.close();
	    
	    log.info("Created order with id: " + pay.getId());
	}
    
    public Payment getPaymentById(String id) {
    	Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Payment where id=:id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Payment> pays = query.list();
		session.close();
		
		if(pays.size() > 0) 
			return (Payment) pays.get(0);
		else 
			return null;
    }
	
	//TODO: why can't we just return query.list()?
	public List<Payment> getAllPayments() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Payment");
		List<Payment> paymentList = new ArrayList<Payment>();
		@SuppressWarnings("unchecked")
		List<Payment> allPayments = query.list();
		session.close();
		for (int i = 0; i < allPayments.size(); i++) {
			Payment order = (Payment) allPayments.get(i);
			paymentList.add(order);
		}
        return paymentList;
    }
	
	public void deletePayment(Payment p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.delete(p);
			session.getTransaction().commit();
		} catch (IllegalArgumentException ie) {
			ie.printStackTrace();
		}
        session.close();
	}

	public void updatePayment(Payment newPayment) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        //parameter checks
       
        session.saveOrUpdate(newPayment);
        session.getTransaction().commit();
        session.close();
        log.info("Updated payment with id: " + newPayment.getId());
        log.info(newPayment.getCardDetails());
    }
	
    /*
     * 	public Map<String, Payment> getStore(){
     *  	return contentStore;
     *  }
     */
       

}
