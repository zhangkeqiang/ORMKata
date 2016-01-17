package com.agilejerry.orm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agilejerry.orm.model.CustomerBean;
import com.agilejerry.orm.model.CustomerDao;
import com.agilejerry.orm.model.Event;

public class CustomerDaoTest {
	
	CustomerDao customerDao = new CustomerDao();
	@Before
	public void setUp() {
		

	}

	@Test
	public void testCRUDEvent() {		
//		CustomerBean customer = new CustomerBean("李琴箫2", "password2");
//		Assert.assertEquals(1, customerDao.add(customer));
//		Assert.assertEquals(customer.getId() > 1, true);
////		event.setTitle("HelloHibernate!");
////		Assert.assertEquals(1, customerDao.update(event));
//		Assert.assertEquals(1, customerDao.delete(customer));
	}


	@After
	public void clear(){
		customerDao.clearIDAbove(2);
	}

}
