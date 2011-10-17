package com.rai.framework.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// POJOUtils.pojo2Map(new Member());
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "testname");
		map.put("parent.parent.name", "0101");
		map.put("name", "222.34");

		A a = new A();
		a=(A) POJOUtils.requestMap2Pojo(A.class.getName(), a, map);

		// Order member = new Order();
		// member = (Order) POJOUtils.requestMap2Pojo(Order.class.getName(),
		// member, map);
		//
		// POJOUtils.pojo2Map(member);
		//
		// Map<String, String> tempMap = new HashMap<String, String>();
		// // 默认相等
		// tempMap.put("id", "123");
		// tempMap.put("createTime-gt", "2010-04-01");
		// tempMap.put("paidCoins-le", "100");
		// tempMap.put("product.price-ge", "12.3");
		// tempMap.put("product.name-like-any", "vvvv");
		//
		// QueryModel queryModel = queryModelFromMap(Order.class, tempMap);
		// queryModel.toString();

	}

}


@SuppressWarnings("serial")
class A implements Serializable {

	private String id;
	private String name;
	private A parent;

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

	public A getParent() {
		return parent;
	}

	public void setParent(A parent) {
		this.parent = parent;
	}

	public A(){};
	public A(String id, String name, A parent) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
	}

}

@SuppressWarnings("serial")
class B implements Serializable {

	private String id;
	private String price;
	private A owner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public A getOwner() {
		return owner;
	}

	public void setOwner(A owner) {
		this.owner = owner;
	}

	public B(){}
	public B(String id, String price, A owner) {
		super();
		this.id = id;
		this.price = price;
		this.owner = owner;
	}

}
