package com.rai.framework.model;



/**
 * com.rai.framework.model.Coder entity.
 * 
 * @author Rai Framework Utils
 * @since 2011-10-26 15:54:31
 */
public class com.rai.framework.model.Coder implements java.io.Serializable {

	// Fields
	private String id;
	private Integer version;
	private Integer nextindex;
	private String tableName;

	// Functions
	public void setId(String id) {
		this.id = id;
	}
	public String getId(String id) {
		return id;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getVersion(Integer version) {
		return version;
	}
	public void setNextindex(Integer nextindex) {
		this.nextindex = nextindex;
	}
	public Integer getNextindex(Integer nextindex) {
		return nextindex;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName(String tableName) {
		return tableName;
	}

}
