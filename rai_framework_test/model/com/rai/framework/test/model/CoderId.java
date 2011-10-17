package com.rai.framework.test.model;

/**
 * CoderId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CoderId implements java.io.Serializable {

	// Fields

	private String id="0";
	private String tableName;

	// Constructors

	/** default constructor */
	public CoderId() {
	}

	/** full constructor */
	public CoderId(String id, String tableName) {
		this.id = id;
		this.tableName = tableName;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CoderId))
			return false;
		CoderId castOther = (CoderId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getTableName() == castOther.getTableName()) || (this
						.getTableName() != null
						&& castOther.getTableName() != null && this
						.getTableName().equals(castOther.getTableName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		return result;
	}

}