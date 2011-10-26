package com.rai.framework.model;

import java.math.BigDecimal;


/**
 * com.rai.framework.model.Person entity.
 * 
 * @author Rai Framework Utils
 * @since 2011-10-26 15:54:31
 */
public class com.rai.framework.model.Person implements java.io.Serializable {

	// Fields
	private Integer id;
	private String name;
	private BigDecimal sex;
	private BigDecimal age;
	private Organization org;

	// Functions
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId(Integer id) {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName(String name) {
		return name;
	}
	public void setSex(BigDecimal sex) {
		this.sex = sex;
	}
	public BigDecimal getSex(BigDecimal sex) {
		return sex;
	}
	public void setAge(BigDecimal age) {
		this.age = age;
	}
	public BigDecimal getAge(BigDecimal age) {
		return age;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public Organization getOrg(Organization org) {
		return org;
	}

}
