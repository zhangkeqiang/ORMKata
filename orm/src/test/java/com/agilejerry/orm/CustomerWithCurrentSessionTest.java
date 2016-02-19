package com.agilejerry.orm;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agilejerry.orm.model.CustomerNoAnno;
import com.agilejerry.orm.model.CustomerAnno;
import com.agilejerry.orm.model.CustomerBean;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CustomerWithCurrentSessionTest {

    Session session;
    static int rowCount = 0;

    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
        try {
            Transaction tx;
            Query q;
            session = HibernateUtil.getCurrentSession();
            tx = session.beginTransaction();
            q = session.createQuery("DELETE CustomerAnno WHERE Id > :MaxID");
            q.setParameter("MaxID", 10);
            q.executeUpdate();
            tx.commit();
           
        } catch (HibernateException e) {
            e.printStackTrace();
         
        }
    }
    private void addSomeCustomers(int count) {
        session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        for (int i = 0; i < count; i++) {
            CustomerAnno customer = new CustomerAnno();
            customer.setUsername("AnnoCustomer" + i);
            customer.setPassword("Anno");
            session.save(customer);
        }

        tx.commit();
    }

    @Test
    public void testCurrentSessionSearch(){
        int countAdded = 12;
        addSomeCustomers(countAdded);
        String hql = "FROM CustomerBean";
        session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        List<CustomerBean> list = session.createQuery(hql).list();
        for(CustomerBean cus:list){
            System.out.println(cus.getUsername());
        }
        session.getTransaction().commit();
    }
    
    
    @Test
    public void deleteSomeCustomer() {
        int countAdded = 12;
        int countA = getCountOfCustomer();
        System.out.println(countA);
        addSomeCustomers(countAdded);
        int countB = getCountOfCustomer();
        Assert.assertEquals(countB, countAdded + countA);       
    }

    private int getCountOfCustomer(){
        session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("SELECT COUNT(*) FROM CustomerBean");
        Object ob = q.uniqueResult();
        tx.commit();
        return Integer.parseInt(ob.toString());
    }
    private int getMaxCustomerId() {
        session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("SELECT MAX(Id) FROM Customer");
        Object ob = q.uniqueResult();
        tx.commit();
        return Integer.parseInt(ob.toString());
    }

    


}
