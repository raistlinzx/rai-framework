package com.rai.framework.model.common;

/**
 * Base Entity
 * 
 * @author rai
 */
@SuppressWarnings("serial")
public class Persistent implements java.io.Serializable {

	/*
	 * 对象唯一标识
	 */
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getClass().getName() + ":" + getId();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
