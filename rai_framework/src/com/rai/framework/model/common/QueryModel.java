package com.rai.framework.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.LinkedMap;

/**
 * 查询组件 用于组合查询条件<br/>
 * 
 * 基本用法 举例<br/>
 * 第一步：新建queryModel QueryModel queryModel=new QueryModel(Person.class);<br/>
 * 第二步：写入查询条件 queryModel.add(ConditionModel.eq("sex","男"); 第三步：增加排序
 * queryModel.addOrder("name","ASC");
 * 
 * @author zhaoxin
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class QueryModel implements Serializable {

	/**
	 * 连表查询方式(默认inner join)，还有left，right
	 * 
	 * @author zhaoxin
	 * 
	 */
	public enum JOIN {
		INNER, LEFT, RIGHT
	}
	
	public enum UNION {
		NOT,NORMAL,ALL
	}

	public QueryModel(Class<?>... clazz) {
		this.clazz = clazz;
	}

	private Class<?>[] clazz;

	public Class<?>[] getQueryClass() {
		return this.clazz;
	}

	/**
	 * 条件信息
	 */
	private List<ConditionModel> conditionModeList = new ArrayList<ConditionModel>();
	private List<QueryModel> queryModelList=new ArrayList<QueryModel>();
	private OrderedMap aliasNames = (OrderedMap) new LinkedMap();
	private boolean distinct = false;
	private JOIN join = JOIN.INNER;
	private UNION union=UNION.NOT;
	
	private String select=null; // add by zx 20110906
	private String groupby=null; // add by zx 20110906

	public List<ConditionModel> getConditionModeList() {
		return conditionModeList;
	}

	public QueryModel add(ConditionModel conditionModel) {
		conditionModeList.add(conditionModel);
		OrderedMap tempAliasMap = conditionModel.getAliasMap();
		Iterator<String> it = tempAliasMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (!aliasNames.containsKey(key)
					|| !aliasNames.get(key).equals(tempAliasMap.get(key)))
				aliasNames.put(key, tempAliasMap.get(key));
		}
		return this;
	}

	/**
	 * 排序信息
	 */
	private OrderedMap orderedMap = new LinkedMap();

	public OrderedMap getOrderedMap() {
		return orderedMap;
	}

	public void setOrderedMap(OrderedMap orderedMap) {
		this.orderedMap = orderedMap;
	}

	public void addOrder(String key, String value) {
		this.orderedMap.put(key, value);
	}

	public void addAll(List<ConditionModel> conditionModeList) {
		this.conditionModeList.addAll(conditionModeList);
	}

	public void setAliasNames(OrderedMap aliasNames) {
		this.aliasNames = aliasNames;
	}

	public OrderedMap getAliasNames() {
		return aliasNames;
	}

	/**
	 * 设置查询结果是否增加distinct过滤
	 * 
	 * @param distinct
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * 设置连表查询方式
	 * 
	 * @param join
	 */
	public void setJoin(JOIN join) {
		this.join = join;
	}

	public JOIN getJoin() {
		return join;
	}

	@Deprecated
	public void setQueryModelList(List<QueryModel> queryModelList) {
		this.queryModelList = queryModelList;
	}

	@Deprecated
	public List<QueryModel> getQueryModelList() {
		return queryModelList;
	}
	
	@Deprecated
	public void union(QueryModel queryModel)
	{
		this.queryModelList.add(queryModel);
		if(!UNION.NORMAL.equals(this.union))
			this.union=UNION.NORMAL;
	}
	
	@Deprecated
	public void union(QueryModel queryModel,UNION union)
	{
		this.queryModelList.add(queryModel);
		if(!this.union.equals(union))
			this.union=union;
	}

	@Deprecated
	public void setUnion(UNION union) {
		this.union = union;
	}

	@Deprecated
	public UNION getUnion() {
		return union;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getSelect() {
		return select;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	public String getGroupby() {
		return groupby;
	}
}
