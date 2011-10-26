package com.rai.framework.util.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableMap {

	/** 表名 */
	private String name;
	/** 备注 */
	private String comment;

	private List<ColumnMap> columns = new ArrayList<ColumnMap>();
	private Map<ColumnMap,TableMap> oneToMany = new HashMap<ColumnMap,TableMap>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ColumnMap> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnMap> columns) {
		this.columns = columns;
	}

	public void setOneToMany(Map<ColumnMap,TableMap> oneToMany) {
		this.oneToMany = oneToMany;
	}

	public Map<ColumnMap,TableMap> getOneToMany() {
		return oneToMany;
	}

	/**
	 * 根据表名，以“_”分隔单词首字母大写
	 * 
	 * @return
	 */
	public String getClassName() {
		String className = "";
		String[] names = name.split("_");

		for (String na : names) {
			if (na != null && !"".equals(na)) {
				String endWord = na.substring(1).toLowerCase();
				String startWord = String.valueOf(na.charAt(0));
				className += startWord.toUpperCase() + endWord.toLowerCase();
			}
		}

		return className;
	}

	@Override
	public String toString() {
		return "TableMap [comment=" + comment + ", name=" + name + "]";
	}

}
