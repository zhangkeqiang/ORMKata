package com.agilejerry.orm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "Customer" )
public class CustomerBean implements java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8728656013044878089L;
	
	private int id;
	private String username;
	private String password;
	private Set<RelativeBean> relativeBeans;
	private Set<Event> events = new HashSet<Event>(0);

	public CustomerBean(){
		
	}
	
	public CustomerBean(String name, String pwd){
		this.username = name;
		this.password = pwd;
	}

	public CustomerBean(String name, String pwd, Set<RelativeBean> relativeBeans){
		this.username = name;
		this.password = pwd;
		this.relativeBeans = relativeBeans;
	}
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}


	public String getUsername() {
		return username;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerBean",cascade=(CascadeType.ALL))
	public Set<RelativeBean> getRelativeBeans() {
		return relativeBeans;
	}

	public void setRelativeBeans(Set<RelativeBean> relativeBeans) {
		this.relativeBeans = relativeBeans;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "customerBeans")
	public Set<Event> getEvents() {
		return events;
	}

	
	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}
