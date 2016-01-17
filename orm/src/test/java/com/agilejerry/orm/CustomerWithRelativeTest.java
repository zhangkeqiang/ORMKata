package com.agilejerry.orm;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agilejerry.orm.model.CustomerBean;
import com.agilejerry.orm.model.RelativeBean;

public class CustomerWithRelativeTest {

	SessionFactory sf;
	Session session;
	static int rowCount = 0;

	@Before
	public void setUp() {
		sf = new Configuration().configure().buildSessionFactory();
		session = sf.openSession();

	}

	@Test
	public void test_read_relative() {
		System.out.println("test_read_relative");
		session.beginTransaction();
		CustomerBean customA = (CustomerBean) session.byId(CustomerBean.class).getReference(1);
		Set<RelativeBean> relativeBeans = customA.getRelativeBeans();
		Assert.assertEquals(2, relativeBeans.size());
		for (RelativeBean relativeBean : relativeBeans) {
			System.out.println(relativeBean.getName() + relativeBean.getPhone());
		}
		session.getTransaction().commit();
	}

	@Test
	public void test_add_new_customer_and_relative() {
		System.out.println("Hibernate one to many (Annotation)");
		CustomerBean customA = new CustomerBean("李莲英", "122643337");
		RelativeBean relativeA = new RelativeBean("李海燕2", "13823330545");
		try {
			session.beginTransaction();
			session.save(customA);
			relativeA.setCustomerBean(customA);
			RelativeBean relativeB = new RelativeBean(customA, "张思德2", "13823333333");
			session.save(relativeA);
			session.save(relativeB);
			Set<RelativeBean> relativeBeans = new HashSet<RelativeBean>();
			relativeBeans.add(relativeA);
			relativeBeans.add(relativeB);
			customA.setRelativeBeans(relativeBeans);
			session.getTransaction().commit();
			Assert.assertEquals(true, relativeA.getId().intValue() > 0);
		} catch (HibernateException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}

		// session.beginTransaction();
		CustomerBean customB = (CustomerBean) session.get(CustomerBean.class, customA.getId());
		Assert.assertEquals(2, customB.getRelativeBeans().size());

		// session.getTransaction().commit();

		session.beginTransaction();
		session.delete(customA);
		session.getTransaction().commit();
	}

	@Test
	public void test_add_existed_customer_relatives() {
		CustomerBean customA = (CustomerBean) session.get(CustomerBean.class, 1);
		Set<RelativeBean> relativeBeans = customA.getRelativeBeans();
		Assert.assertEquals(2, relativeBeans.size());
		RelativeBean relativeA = new RelativeBean("李海燕2", "13823330545");
		relativeA.setCustomerBean(customA);
		RelativeBean relativeB = new RelativeBean(customA, "张思德2", "13823333333");
		relativeBeans.add(relativeA);
		relativeBeans.add(relativeB);
		//customA.setRelativeBeans(relativeBeans);
		session.beginTransaction();
		// CustomerBean cascadetype is all, so the below 2 lines is no need
		// session.save(relativeA);
		// session.save(relativeB);
		session.update(customA);
		session.getTransaction().commit();
		Assert.assertEquals(4, countAllRelatives());
		relativeBeans.remove(relativeA);
		relativeBeans.remove(relativeB);
		session.beginTransaction();
		session.delete(relativeA);
		session.delete(relativeB);
		session.update(customA);
		session.getTransaction().commit();
		Assert.assertEquals(2, countAllRelatives());
	}

	@Test
	public void test_search_by_sql() {
		int countA, countB;

		String queryString = "SELECT COUNT(ID) FROM RELATIVE";
		Query q1 = session.createSQLQuery(queryString);
		countA = Integer.parseInt(q1.uniqueResult().toString());
		// session.getTransaction().commit();
		Query q2 = session.createSQLQuery(queryString);
		countB = Integer.parseInt(q2.uniqueResult().toString());

		Assert.assertEquals(2, countA);
		Assert.assertEquals(countB, countA);
	}

	private int countAllRelatives(){
		String queryString = "SELECT COUNT(ID) FROM RELATIVE";
		Query q1 = session.createSQLQuery(queryString);
		return Integer.parseInt(q1.uniqueResult().toString());
	}
	@After
	public void tearDown() {
		session.close();
		sf.close();
	}
}
