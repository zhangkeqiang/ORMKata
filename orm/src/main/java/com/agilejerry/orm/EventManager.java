package com.agilejerry.orm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import com.agilejerry.orm.model.CustomerBean;
import com.agilejerry.orm.model.Event;

public class EventManager {

//	public void addEventCustomer(Event event, CustomerBean customer) {
//		//Set<CustomerBean> customerBeans = new HashSet<CustomerBean>();
//		Set<CustomerBean> customerBeans = event.getCustomerBeans();
//		Iterator<CustomerBean> it = customerBeans.iterator();
//		while(it.hasNext()) {
//			CustomerBean customerIn = it.next();
//			if(customerIn.getId() == customer.getId()) return;
//			if(customerIn.getUsername() == customer.getUsername() 
//					&& customerIn.getPassword() == customer.getPassword())
//				return;
//		}		
//
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		customerBeans.add(customer);
//		//event.setCustomerBeans(customerBeans);
//		session.update(event);
//		session.getTransaction().commit();
//		session.close();
//	}

}
