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

import com.thegs.coffeeapp.model.Order;



public class OrderDao {
   
	private static final Logger log = Logger.getLogger( ClassName.class.getName() );
	private SessionFactory sessionFactory;
    
	public OrderDao() {
    	
    	log.info("Trying to create a connection with the database");
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
        log.info("Connection with the database created successfuly");
        
    }
	public void addOrder(Order order) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        //parameter checks

        session.save(order);
        session.getTransaction().commit();
        session.close();
        log.info("Created order with id: " + order.getId());
    }
	
	public void updateOrder(Order newOrder) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        //parameter checks
        

        session.update(newOrder);
        session.getTransaction().commit();
        session.close();
        log.info("Updated order with id: " + newOrder.getId());
    }
	
	public Order getOrderById(String id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Order where id=:id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Order> order = query.list();
		session.close();
		if(order.size() > 0) {
			return order.get(0);
		}
		else
			return null;
		   
    }
	
	public List<Order> getAllOrders() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Order");
		List<Order> orderList = new ArrayList<Order>();
		@SuppressWarnings("unchecked")
		List<Order> allOrders = query.list();
		session.close();
		for (int i = 0; i < allOrders.size(); i++) {
			Order order = (Order) allOrders.get(i);
			orderList.add(order);
		}
        return orderList;
    }
	
	public void deleteOrder(Order o) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
		session.close();
	}
	
	protected void finalize() throws Throwable {
		super.finalize();
		while (!sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}

}
