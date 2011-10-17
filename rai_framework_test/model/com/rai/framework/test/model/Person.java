package com.rai.framework.test.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员
 * 
 * @author zhaoxin
 * 
 */
public class Person implements java.io.Serializable {

	public static final Integer SEX_MALE = 1;
	public static final Integer SEX_FEMALE = 2;
	// Fields

	private Integer id;
	/** 姓名 */
	private String name;
	/** 年龄 */
	private Integer age;
	/** 性别 */
	private Integer sex = SEX_MALE;
	/** 所在机构 */
	private Organization org;
	/** 人员调动记录 */
	private List<PersonMoveLog> personMoveLogs=new ArrayList<PersonMoveLog>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public void setPersonMoveLogs(List<PersonMoveLog> personMoveLogs) {
		this.personMoveLogs = personMoveLogs;
	}

	public List<PersonMoveLog> getPersonMoveLogs() {
		return personMoveLogs;
	}

}