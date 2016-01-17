package com.agilejerry.orm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agilejerry.orm.model.CustomerNoAnno;
import com.agilejerry.orm.model.CustomerAnno;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CustomerAnnoTest {
	
	SessionFactory sf;
	static int rowCount = 0;
	
	@Before
	public void setUp(){
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	



	private void addSomeCustomers(int count) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		for (int i = 0; i < count; i++) {
		    CustomerAnno customer = new CustomerAnno();
		    customer.setUsername("AnnoCustomer" + i);
		    customer.setPassword("Anno");
		    session.save(customer);
		}

		tx.commit();
		session.close();
	}
	
	@Test
	public void deleteSomeCustomer(){
		int countAdded =12;
		Session session;
		Transaction tx;
		Query q;
        int countA = getMaxCustomerId();
        System.out.println(countA);
		addSomeCustomers(countAdded);
		int countB = getMaxCustomerId();
		Assert.assertEquals(countB, countAdded+countA);
		try {			
            session = sf.openSession();
            tx = session.beginTransaction();
            q = session.createQuery("DELETE CustomerAnno WHERE Id > ?");
            q.setParameter(0, countA);
            int actualcount = q.executeUpdate();            
            tx.commit();
            session.close();
            Assert.assertEquals(countAdded, actualcount);
        } catch (HibernateException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
	}





	private int getMaxCustomerId() {
		Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("SELECT MAX(Id) FROM Customer");
        Object ob = q.uniqueResult();
        session.close();
		return Integer.parseInt(ob.toString());
	}
	
//	private void clearCustomer(){
//		try {			
//            Session session = sf.openSession();
//            Transaction tx = session.beginTransaction();
//            Query q = session.createQuery("DELETE CustomerAnno");
//            q.executeUpdate();            
//            tx.commit();
//            session.close();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            Assert.assertTrue(false);
//        }
//	}
	
	
	@After
	public void tearDown(){
		if ( sf != null ) {
			sf.close();
		}
	}

}
