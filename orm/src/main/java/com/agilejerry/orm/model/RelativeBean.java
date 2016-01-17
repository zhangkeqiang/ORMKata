package com.agilejerry.orm.model;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "RELATIVE")
public class RelativeBean implements java.io.Serializable  {
	private static final long serialVersionUID = -7903495555694399175L;

	private Integer Id;
	private CustomerBean customer;
	private String name;
	private String phone;
	
	public RelativeBean(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}
	
	
	public RelativeBean() {
		super();
	}


	public RelativeBean(CustomerBean customer, String name, String phone) {
		super();
		this.customer = customer;
		this.name = name;
		this.phone = phone;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	public CustomerBean getCustomerBean() {
		return customer;
	}
	public void setCustomerBean(CustomerBean customer) {
		this.customer = customer;
	}
	
	@Column(name = "RELATIVE_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "RALATIVE_PHONE")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
