package com.agilejerry.orm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agilejerry.orm.model.CustomerNoAnno;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CustomerTest {
	SessionFactory sf;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp(){
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	
	@Test
	public void test() {
		try {			
            Session session = sf.openSession();
            Transaction tx = session.beginTransaction();

            for (int i = 0; i < 200; i++) {
                CustomerNoAnno customer = new CustomerNoAnno();
                customer.setUsername("customer" + i);
                customer.setPassword("customer");
                session.save(customer);
            }

            tx.commit();
            session.close();
            Assert.assertTrue(true);
        } catch (HibernateException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
	}
	
	@After
	public void tearDown(){
		if ( sf != null ) {
			sf.close();
		}
	}

}
