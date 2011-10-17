package com.rai.framework.test.model;

import java.util.Date;

/**
 * 人员
 * 
 * @author zhaoxin
 * 
 */
public class PersonMoveLog implements java.io.Serializable {

	private Integer id;
	/** 人员 */
	private Person person;
	/** 原机构 */
	private Organization oldOrg;
	/** 新机构 */
	private Organization newOrg;
	/** 时间 */
	private Date moveTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Organization getOldOrg() {
		return oldOrg;
	}

	public void setOldOrg(Organization oldOrg) {
		this.oldOrg = oldOrg;
	}

	public Organization getNewOrg() {
		return newOrg;
	}

	public void setNewOrg(Organization newOrg) {
		this.newOrg = newOrg;
	}

	public Date getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(Date moveTime) {
		this.moveTime = moveTime;
	}

}