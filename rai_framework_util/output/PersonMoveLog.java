package com.rai.framework.model;

import java.util.Date;


/**
 * com.rai.framework.model.PersonMoveLog entity.
 * 
 * @author Rai Framework Utils
 * @since 2011-10-26 18:08:39
 */
public class com.rai.framework.model.PersonMoveLog implements java.io.Serializable {

	// Fields
	private Integer id;
	private Person person;
	private String oldOrgId;
	private Organization newOrg;
	private Date moveTime;

	// Functions
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId(Integer id) {
		return id;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Person getPerson(Person person) {
		return person;
	}
	public void setOldOrgId(String oldOrgId) {
		this.oldOrgId = oldOrgId;
	}
	public String getOldOrgId(String oldOrgId) {
		return oldOrgId;
	}
	public void setNewOrg(Organization newOrg) {
		this.newOrg = newOrg;
	}
	public Organization getNewOrg(Organization newOrg) {
		return newOrg;
	}
	public void setMoveTime(Date moveTime) {
		this.moveTime = moveTime;
	}
	public Date getMoveTime(Date moveTime) {
		return moveTime;
	}

}
