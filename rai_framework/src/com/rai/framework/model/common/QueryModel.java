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

	public QueryModel addOrder(String key, String value) {
		this.orderedMap.put(key, value);
		return this;
	}

	public QueryModel addAll(List<ConditionModel> conditionModeList) {
		this.conditionModeList.addAll(conditionModeList);
		return this;
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
	public QueryModel setDistinct(boolean distinct) {
		this.distinct = distinct;
		return this;
	}

	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * 设置连表查询方式
	 * 
	 * @param join
	 */
	public QueryModel setJoin(JOIN join) {
		this.join = join;
		return this;
	}

	public JOIN getJoin() {
		return join;
	}

	@Deprecated
	public QueryModel setQueryModelList(List<QueryModel> queryModelList) {
		this.queryModelList = queryModelList;
		return this;
	}

	@Deprecated
	public List<QueryModel> getQueryModelList() {
		return queryModelList;
	}
	
	@Deprecated
	public QueryModel union(QueryModel queryModel)
	{
		this.queryModelList.add(queryModel);
		if(!UNION.NORMAL.equals(this.union))
			this.union=UNION.NORMAL;
		return this;
	}
	
	@Deprecated
	public QueryModel union(QueryModel queryModel,UNION union)
	{
		this.queryModelList.add(queryModel);
		if(!this.union.equals(union))
			this.union=union;
		return this;
	}

	@Deprecated
	public QueryModel setUnion(UNION union) {
		this.union = union;
		return this;
	}

	@Deprecated
	public UNION getUnion() {
		return union;
	}

	public QueryModel setSelect(String select) {
		this.select = select;
		return this;
	}

	public String getSelect() {
		return select;
	}

	public QueryModel setGroupby(String groupby) {
		this.groupby = groupby;
		return this;
	}

	public String getGroupby() {
		return groupby;
	}
}
