package com.rai.framework.util.model;

public class ForeignMap {

	private String tableName;
	private String columnName;
	private String referencedTableName;
	private String referencedColumnName;

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}

	public String getReferencedTableName() {
		return referencedTableName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}

	public String getReferencedColumnName() {
		return referencedColumnName;
	}

	@Override
	public String toString() {
		return this.getTableName() + "(" + this.getColumnName() + ") -> "
				+ this.getReferencedTableName() + "("
				+ this.getReferencedColumnName() + ")";
	}

}
