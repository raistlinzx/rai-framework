package com.rai.framework.test.model;

/**
 * Coder entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Coder implements java.io.Serializable {

	// Fields

	private CoderId id;
	private int version =0;
	private int nextIndex = 1;

	// Constructors

	/** default constructor */
	public Coder() {
	}

	/** full constructor */
	public Coder(CoderId id, int version, int nextIndex) {
		this.id = id;
		this.version = version;
		this.nextIndex = nextIndex;
	}
	
	public Coder(String key,String tableName) {
		this.id = new CoderId();
		this.id.setId(key);
		this.id.setTableName(tableName);
	}

	// Property accessors

	public CoderId getId() {
		return this.id;
	}

	public void setId(CoderId id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	
}