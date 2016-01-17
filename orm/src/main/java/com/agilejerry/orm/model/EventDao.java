package com.agilejerry.orm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;

import com.agilejerry.orm.HibernateUtil;

public class EventDao {
	private Session session;
	public EventDao(Session session) {
		this.session = session;
	}



	public List<Event> listEvents() {
		List<Event> eventlist = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			eventlist = session.createQuery("from Event").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			Assert.assertTrue(false);
		} finally {
			
		}
		return eventlist;
	}

	public int deleteEvent(Integer Id) {
		Event event = (Event) session.get(Event.class, Id);
		int ret = deleteEvent(event);
		return ret;
	}


	public int addEvent(Event event) {
		session.beginTransaction();
		session.save(event);
		session.getTransaction().commit();
		return 1;
	}

	public int updateEvent(Event event) {
		int ret = -1;
		try {
			session.beginTransaction();
			session.update(event);
			session.getTransaction().commit();
			ret = 1;
		} catch (HibernateException e) {
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			e.printStackTrace();
			ret = -99;
		} finally {
			
		}
		return ret;
	}

	public int deleteEvent(Event event) {
		int ret = -1;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();			
			session.delete(event);
			tx.commit();
			ret = 1;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			ret = -99;
		} finally {
			
		}
		return ret;
	}

	public List<Event> searchEventsByTitle(String title) {
		List<Event> list = (List<Event>) session.createQuery("from Event where Title like :title")
				.setParameter("title", "%" + title +"%")
				.list();	

		return list;
	}

	public Event findEventByID(int Id) {
		Event event = (Event) session.get(Event.class, Id);
		return event;
	}

	public int clearIDAbove(int i) {
		int ret = -1;
		try {			
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("DELETE Event WHERE Id > :aboveId");
            q.setParameter("aboveId", i);
            ret = q.executeUpdate();
            tx.commit();
            Assert.assertTrue(true);
        } catch (HibernateException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }	
		return ret;
	}



	public void addEventCustomer(Event event, CustomerBean customer) {
		Set<CustomerBean> customerBeans = event.getCustomerBeans();
		Iterator<CustomerBean> it = customerBeans.iterator();
		while(it.hasNext()) {
			CustomerBean customerIn = it.next();
			if(customerIn.getId() == customer.getId()) return;
			if(customerIn.getUsername() == customer.getUsername() 
					&& customerIn.getPassword() == customer.getPassword())
				return;
		}		
		session.beginTransaction();
		customerBeans.add(customer);
		session.update(event);
		session.getTransaction().commit();
	}

}
