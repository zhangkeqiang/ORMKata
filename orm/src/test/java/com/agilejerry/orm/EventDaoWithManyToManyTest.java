package com.agilejerry.orm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agilejerry.orm.model.CustomerBean;
import com.agilejerry.orm.model.CustomerDao;
import com.agilejerry.orm.model.Event;
import com.agilejerry.orm.model.EventDao;
import com.agilejerry.orm.model.RelativeBean;


public class EventDaoWithManyToManyTest {

	Session session;
	
	@Before
	public void setUp(){
		session = HibernateUtil.getSessionFactory().openSession();		
	}
	
	@After
	public void tearDown(){	
		EventDao eventDao = new EventDao(session);
		eventDao.clearIDAbove(2);
		CustomerDao customDao = new CustomerDao();
		customDao.clearIDAbove(2);
        session.close();
	}
	
	@Test
	public void test_add_old_customerOfEvent(){
		//EventManager manager = new EventManager();
		EventDao eventDao = new EventDao(session);
		Event event = eventDao.findEventByID(1);		
		Assert.assertEquals(true, event != null);
		Assert.assertEquals(true, event.getCustomerBeans() != null);
		CustomerDao customDao = new CustomerDao();
		CustomerBean customer = customDao.findCustomerById(1);
		Assert.assertEquals(true, customer != null);
		Assert.assertEquals(customer.getId(), 1);
		Assert.assertEquals(customer.getUsername(), "李世民");		
		eventDao.addEventCustomer(event, customer);
		Assert.assertEquals(false, event.getCustomerBeans().isEmpty());
		
	}
	
	@Test
	public void test_add_new_customer_of_Event(){
		EventDao eventDao = new EventDao(session);
		Event event = eventDao.findEventByID(1);		
		Assert.assertEquals(true, event != null);
		Assert.assertEquals(true, event.getCustomerBeans() != null);
		Assert.assertEquals(1, event.getCustomerBeans().size());
		CustomerBean customer = new CustomerBean("李云龙","34561235");
		Assert.assertEquals(true, customer != null);
		Assert.assertEquals(customer.getUsername(), "李云龙");	
				
		Assert.assertEquals(false, event.getCustomerBeans().isEmpty());
		Set<CustomerBean> customerBeans = event.getCustomerBeans();
		System.out.println("Size1=====" + customerBeans.size());
		Iterator<CustomerBean> it = customerBeans.iterator();
		while(it.hasNext()) {
			CustomerBean customerIn = it.next();
			System.out.println(customerIn.getUsername() + customerIn.getPassword());
		}	 
		
		eventDao.addEventCustomer(event, customer);	
		event = eventDao.findEventByID(1);		
		customerBeans = event.getCustomerBeans();
		Assert.assertEquals(2, customerBeans.size());
		//CustomerDao customDao = new CustomerDao(session);
		//Assert.assertEquals(1, customDao.delete(customer));
		Assert.assertEquals(2, customerBeans.size());
		Event event2 = eventDao.findEventByID(1);	
		Set<CustomerBean> customerBeans2 = event2.getCustomerBeans();
		Assert.assertEquals(2, customerBeans2.size());
	}
	
	
	@Test
	public void test_add_new_some_customer_of_Event(){
		int count = 10;
		EventDao eventDao = new EventDao(session);
		Event event = eventDao.findEventByID(1);	
		Set<CustomerBean> customerBeans = event.getCustomerBeans();
		for(int i=0;i<count;i++){
			CustomerBean customer = new CustomerBean("张世强"+i,"3456123"+i);
			customerBeans.add(customer);
		}
		
		session.beginTransaction();
		session.update(event);
		
		
		customerBeans = event.getCustomerBeans();		
		Iterator<CustomerBean> it = customerBeans.iterator();
		session.getTransaction().commit();
		
		while(it.hasNext()) {
			CustomerBean customer = it.next();
			System.out.println(customer.getUsername() + customer.getPassword());
		}
		
	}
	
	
	@Test
	public void test_remove_some_customer_of_Event(){
		int count = 3;
		EventManager manager = new EventManager();
		EventDao eventDao = new EventDao(session);
		Event event = eventDao.findEventByID(1);	
		Set<CustomerBean> customerBeans = event.getCustomerBeans();
		CustomerBean customer = null;
		for(int i=0;i<count;i++){
			customer = new CustomerBean("张克"+i,"3456123"+i);
			customerBeans.add(customer);
		}
		System.out.println("customerBeans Size=====" + customerBeans.size());
		Assert.assertEquals(4, customerBeans.size());
		session.beginTransaction();
		session.update(event);
		session.getTransaction().commit();
		
//		try {
//			Thread.sleep(20000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		customerBeans.remove(customer);


		System.out.println("After remove customerBeans Size=====" + customerBeans.size());
		Assert.assertEquals(3, customerBeans.size());
		session.beginTransaction();
		session.update(event);
		session.getTransaction().commit();
		
		System.out.println("After Update customerBeans Size=====" + customerBeans.size());
		Assert.assertEquals(3, customerBeans.size());
		Iterator<CustomerBean> it = customerBeans.iterator();
		while(it.hasNext()) {
			CustomerBean customerB = it.next();
			System.out.println(customerB.getUsername() + customerB.getPassword());
		}
		System.out.println("customerBeans Size=====" + customerBeans.size());
		Event event2 = eventDao.findEventByID(1);	
		Set<CustomerBean> customerBeans2 = event2.getCustomerBeans();
		Assert.assertEquals(3, customerBeans2.size());
	}
	
	
	@Test 
	public void test_find_2relatives_by_Event1(){
		int relativecount = 0;
		EventDao eventDao = new EventDao(session);
		Event event = eventDao.findEventByID(1);
		Set<CustomerBean> customerBeans = event.getCustomerBeans();
		Iterator<CustomerBean> it = customerBeans.iterator();
		//CustomerDao customDao = new CustomerDao();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Set<RelativeBean> allrelativeBeans = new HashSet<RelativeBean>();
		while(it.hasNext()) {
			CustomerBean customerA = it.next();
			System.out.println(customerA.getUsername() + customerA.getPassword());
			CustomerBean customerB = (CustomerBean) session.get(CustomerBean.class, customerA.getId());
			Set<RelativeBean> relativeBeans = customerB.getRelativeBeans();
			allrelativeBeans.addAll(relativeBeans);
			relativecount += relativeBeans.size();
		}
		Assert.assertEquals(2, relativecount);
		Assert.assertEquals(2, allrelativeBeans.size());
	}
}
