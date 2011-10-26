package com.rai.framework.model;

import java.math.BigDecimal;


/**
 * com.rai.framework.model.Organization entity.
 * 
 * @author Rai Framework Utils
 * @since 2011-10-26 15:54:31
 */
public class com.rai.framework.model.Organization implements java.io.Serializable {

	// Fields
	private String id;
	private String name;
	private Organization parent;
	private BigDecimal idx;
	private BigDecimal rank;

	// Functions
	public void setId(String id) {
		this.id = id;
	}
	public String getId(String id) {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName(String name) {
		return name;
	}
	public void setParent(Organization parent) {
		this.parent = parent;
	}
	public Organization getParent(Organization parent) {
		return parent;
	}
	public void setIdx(BigDecimal idx) {
		this.idx = idx;
	}
	public BigDecimal getIdx(BigDecimal idx) {
		return idx;
	}
	public void setRank(BigDecimal rank) {
		this.rank = rank;
	}
	public BigDecimal getRank(BigDecimal rank) {
		return rank;
	}

}
