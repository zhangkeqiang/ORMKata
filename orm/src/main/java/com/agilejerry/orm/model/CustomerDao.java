package com.agilejerry.orm.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;

import com.agilejerry.orm.HibernateUtil;
import com.agilejerry.orm.model.CustomerBean;

public class CustomerDao {
	private static Session session;

	public CustomerDao(){
		if(session==null)
			session = HibernateUtil.getSessionFactory().openSession();
	}


	public CustomerDao(Session session) {
		if(this.session != null)
			session.close();
		this.session = session;
	}


	public CustomerBean findCustomerById(int Id) throws HibernateException {
		CustomerBean customer;
		session.beginTransaction();
		customer = (CustomerBean) session.byId(CustomerBean.class).load(Id);
		session.getTransaction().commit();
		

		return customer;
	}

	public int add(CustomerBean customer) {
		session.beginTransaction();
		session.save(customer);
		session.getTransaction().commit();
		
		return 1;

	}

	public int delete(CustomerBean customer) {
		int ret = -1;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();			
			session.delete(customer);
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

	public int clearIDAbove(int i) {
		int ret = -1;
		try {			
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("DELETE CustomerBean WHERE Id > ?");
            q.setParameter(0, i);
            ret = q.executeUpdate();
            tx.commit();
            
            Assert.assertTrue(true);
        } catch (HibernateException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }	
		return ret;
		
	}

	public void shutdown(){
		session.close();
		HibernateUtil.shutdown();
	}

}
