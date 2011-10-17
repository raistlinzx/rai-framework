package com.rai.framework.test.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构
 * 
 * @author zhaoxin
 * 
 */
public class Organization implements java.io.Serializable {

	private String id;
	/** 名称 */
	private String name;
	/** 父机构 */
	private Organization parentOrg;
	/** 层次 */
	private Integer rank;
	/** 排序 */
	private Integer idx;
	/** 人员调动记录 */
	private List<PersonMoveLog> out_personMoveLogs = new ArrayList<PersonMoveLog>();
	private List<PersonMoveLog> in_personMoveLogs = new ArrayList<PersonMoveLog>();

	private List<Organization> children = new ArrayList<Organization>();
	private List<Person> persons = new ArrayList<Person>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Organization getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(Organization parentOrg) {
		this.parentOrg = parentOrg;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public List<PersonMoveLog> getOut_personMoveLogs() {
		return out_personMoveLogs;
	}

	public void setOut_personMoveLogs(List<PersonMoveLog> outPersonMoveLogs) {
		out_personMoveLogs = outPersonMoveLogs;
	}

	public List<PersonMoveLog> getIn_personMoveLogs() {
		return in_personMoveLogs;
	}

	public void setIn_personMoveLogs(List<PersonMoveLog> inPersonMoveLogs) {
		in_personMoveLogs = inPersonMoveLogs;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Person> getPersons() {
		return persons;
	}
}