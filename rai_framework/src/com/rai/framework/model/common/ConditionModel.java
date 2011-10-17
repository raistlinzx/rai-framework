package com.rai.framework.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.LinkedMap;

/**
 * 用于QueryModel查询增加查询条件<br/>
 * 支持eq, ne, ge, le, gt, lt, in, notIn, or, like, columnLike, isNull, isNotNull,
 * isEmpty, isNotEmpty, conjunction, disjunction<br/>
 * 
 * @author zhaoxin
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class ConditionModel implements Serializable {

	public final static String ALIAS_NAME = "alias";
	public final static String NO_ALIAS_START = "[";
	public final static String NO_ALIAS_END = "]";

	@SuppressWarnings("unused")
	private ConditionModel() {
	}

	private ConditionModel(String columnName, Object value, Condition condition) {
		this.columnName = columnName;
		this.value = value;
		this.condition = condition;
		convertPropertyName(this.columnName);
	}

	private ConditionModel(ConditionModel[] conditionModels, Condition condition) {
		for (ConditionModel conditionModel : conditionModels) {
			this.list.add(conditionModel);
			this.aliasMap.putAll(conditionModel.getAliasMap());
		}
		this.condition = condition;
	}

	private ConditionModel(String columnName, Object[] values,
			Condition condition) {
		this.columnName = columnName;
		this.values = values;
		this.condition = condition;
		convertPropertyName(this.columnName);
	}

	public ConditionModel(String columnName, Object value, MatchMode matchMode,
			Condition condition) {
		this.columnName = columnName;
		this.value = value;
		this.matchMode = matchMode;
		this.condition = condition;
		convertPropertyName(this.columnName);
	}

	public ConditionModel(String columnName, Collection values,
			Condition condition) {
		this.columnName = columnName;
		this.values = values.toArray();
		this.condition = condition;
		convertPropertyName(this.columnName);
	}

	public ConditionModel(Condition condition) {
		this.condition = condition;
	}

	/**
	 * 分离关联对象的属性，保存关联对象到别名集合
	 * 
	 * @param columnName
	 * @param aliasMap
	 * @return
	 */
	private void convertPropertyName(String columnName) {
		int aliasIndex = -1;

		if (columnName.startsWith(NO_ALIAS_START)
				&& columnName.endsWith(NO_ALIAS_END)) {
			columnName = columnName.replace(NO_ALIAS_START, "").replace(
					NO_ALIAS_END, "");
		} else {
			aliasIndex = columnName.indexOf(".");
		}

		if (aliasIndex > -1) {
			// 需连表查询
			this.haveAliasName = true;
			this.propertyName = columnName.substring(0, aliasIndex);
			if (aliasName == null)
				this.aliasName = ALIAS_NAME;
			this.extendPropertyName = "." + propertyName;
			String key = aliasName + extendPropertyName;
			this.aliasName = this.aliasName + "_" + this.propertyName;
			aliasMap.put(key, this.aliasName);

			convertPropertyName(columnName.substring(aliasIndex + 1));
		} else {
			// 不连表，直接属性对应
			this.propertyName = columnName;
			if (this.aliasName == null)
				this.aliasName = ALIAS_NAME;
			this.extendPropertyName = "." + propertyName;
			this.columnName = aliasName + extendPropertyName;
		}
	}

	private Condition condition;

	private MatchMode matchMode;

	private String columnName;

	private String aliasName;

	private String propertyName;

	private String extendPropertyName;

	private boolean haveAliasName;

	private OrderedMap aliasMap = new LinkedMap();

	private Object value;

	private Object[] values;

	private List<ConditionModel> list = new ArrayList<ConditionModel>();

	public String getColumnName() {
		return columnName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getExtendPropertyName() {
		return extendPropertyName;
	}

	public boolean isHaveAliasName() {
		return haveAliasName;
	}

	public Object getValue() {
		return value;
	}

	public Object[] getValues() {
		return values;
	}

	public ConditionModel[] getConditionList() {
		return list.toArray(new ConditionModel[list.size()]);
	}

	public Condition getCondition() {
		return condition;
	}

	public MatchMode getMatchMode() {
		return matchMode;
	}

	public ConditionModel add(ConditionModel conditionModel) {
		this.list.add(conditionModel);
		this.aliasMap.putAll(conditionModel.getAliasMap());
		return this;
	}

	public static ConditionModel eq(String columnName, Object value) {
		return new ConditionModel(columnName, value, Condition.eq);
	}

	public static ConditionModel ne(String columnName, Object value) {
		return new ConditionModel(columnName, value, Condition.ne);
	}

	public static ConditionModel ge(String columnName, Object value) {
		return new ConditionModel(columnName, value, Condition.ge);
	}

	public static ConditionModel le(String columnName, Object value) {
		return new ConditionModel(columnName, value, Condition.le);
	}

	public static ConditionModel gt(String columnName, Object value) {
		return new ConditionModel(columnName, value, Condition.gt);
	}

	public static ConditionModel lt(String columnName, Object value) {
		return new ConditionModel(columnName, value, Condition.lt);
	}

	public static ConditionModel or(ConditionModel... conditionModel) {
		return new ConditionModel(conditionModel, Condition.or);
	}

	public static ConditionModel in(String columnName, Object... values) {
		return new ConditionModel(columnName, values, Condition.in);
	}

	public static ConditionModel in(String columnName, Collection values) {
		return new ConditionModel(columnName, values, Condition.in);
	}

	public static ConditionModel notIn(String columnName, Collection values) {
		return new ConditionModel(columnName, values, Condition.notIn);
	}

	public static ConditionModel notIn(String columnName, Object... values) {
		return new ConditionModel(columnName, values, Condition.notIn);
	}

	public static ConditionModel like(String columnName, Object value,
			MatchMode matchMode) {
		return new ConditionModel(columnName, value, matchMode, Condition.like);
	}

	public static ConditionModel columnLike(String columnName, Object value,
			MatchMode matchMode) {
		return new ConditionModel(columnName, value, matchMode,
				Condition.columnLike);
	}

	public static ConditionModel isNull(String columnName) {
		return new ConditionModel(columnName, columnName, null,
				Condition.isNull);
	}

	public static ConditionModel isNotNull(String columnName) {
		return new ConditionModel(columnName, columnName, null,
				Condition.isNotNull);
	}

	public static ConditionModel conjunction() {
		return new ConditionModel(Condition.conjunction);
	}

	public static ConditionModel disjunction() {
		return new ConditionModel(Condition.disjunction);
	}

	public enum Condition {
		eq, ne, ge, le, gt, lt, in, notIn, or, like, columnLike, isNull, isNotNull, isEmpty, isNotEmpty, conjunction, disjunction
	}

	public enum MatchMode {
		EXACT, START, END, ANYWHERE
	}

	public void setHaveAliasName(boolean haveAliasName) {
		this.haveAliasName = haveAliasName;
	}

	public OrderedMap getAliasMap() {
		return aliasMap;
	}

}
