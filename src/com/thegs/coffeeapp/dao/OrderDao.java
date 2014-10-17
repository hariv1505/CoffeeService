package com.thegs.coffeeapp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javassist.bytecode.stackmap.TypeData.ClassName;

import com.thegs.coffeeapp.model.Order;



public class OrderDao {
   
	private static final Logger log = Logger.getLogger( ClassName.class.getName() );
	private Session session;
    
	public OrderDao() {
    	
    	log.info("Trying to create a connection with the database");
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
        session = sessionFactory.openSession();
        log.info("Connection with the database created successfuly");
        
        
    }
	public void addOrder(Order order) {

        session.beginTransaction();

        //parameter checks

        session.saveOrUpdate(order);
        session.getTransaction().commit();
        session.close();
        
        log.info("Created order with id: " + order.getId());
    }
	
	public Order getOrderById(String id) {
		Query query = session.createQuery("from Order where id=:id");
		query.setParameter("id", id);
		java.util.List order;
		order = query.list();
		   if(order.size() > 0) {
				Order o = (Order) (order.get(0));
				return o;
			}
			else
				return null;
    }
	
	public List<Order> getAllOrders() {
		Query query = session.createQuery("from Order");
		List<Order> orderList = new ArrayList<Order>();
		java.util.List allOrders;
		allOrders = query.list();
		  for (int i = 0; i < allOrders.size(); i++) {
			  Order order = (Order) allOrders.get(i);
			  orderList.add(order);
		  }
        return orderList;
    }
	
	public void deleteOrder(Order o) {
		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
        session.close();
	}

}
