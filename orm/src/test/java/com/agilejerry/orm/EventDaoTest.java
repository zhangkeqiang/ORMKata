package com.agilejerry.orm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agilejerry.orm.model.Event;
import com.agilejerry.orm.model.EventDao;

public class EventDaoTest {
	EventDao eventDao;
	Session session;
	@Before
	public void setUp() {
		session = HibernateUtil.getSessionFactory().openSession();
		eventDao = new EventDao(session);
	}

	@Test
	public void testCRUDEvent() {
		Event event = new Event("ddd", new Date());
		Assert.assertEquals(1, eventDao.addEvent(event));
		Assert.assertEquals(event.getId() > 1, true);
		event.setTitle("HelloHibernate!");
		Assert.assertEquals(1, eventDao.updateEvent(event));
		Assert.assertEquals(1, eventDao.deleteEvent(event));
	}

	@Test
	public void testListEvents() {
		List<Event> list = eventDao.listEvents();
		for (Event event : list) {
			System.out.println(event.getTitle());
		}
	}

	@Test
	public void testDeleteEvent() {
		System.out.println("====testDeleteEvent====");
		Event event = new Event("ddd", new Date());
		Assert.assertEquals(1, eventDao.addEvent(event));
		Assert.assertEquals(1, eventDao.deleteEvent(event));
	}

//	public void deleteAllEvents() {
//		System.out.println("====testDeleteEvent====");
//		eManager.deleteAllEvents();
//	}

	@Test
	public void testSearchEvents() {
		// prepare data
		List<Event> list = new ArrayList<Event>();
		for (int i = 0; i < 20; i++) {
			Event event = new Event("Title" + i, new Date());
			eventDao.addEvent(event);
			list.add(event);
		}
		

		List<Event> eventlist = eventDao.searchEventsByTitle("Title1");
		Assert.assertNotNull(eventlist);
		Assert.assertEquals(true, eventlist.size() > 0);
		Iterator<Event> iterator = eventlist.iterator();
		while (iterator.hasNext()) {
			Event event = (Event) iterator.next();
			System.out.print("ID: " + event.getId());
			System.out.print(", Title: " + event.getTitle());
			System.out.println(", Date: " + event.getDate());
		}
		for(Event event: list){
			eventDao.deleteEvent(event.getId());
		} 

	}
	
	@After
	public void clear(){
		eventDao.clearIDAbove(3);
        session.close();
	}
}
