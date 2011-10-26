package com.rai.framework.util.model;

public class ColumnMap {

	// 主键生成方式
	public enum EXTRA {
		AUTO, DEFINED
	}

	/** 列名 */
	private String name;
	/** 类型 */
	private String type;
	/** 数据长度 */
	private Integer dataLength;
	/** 数据精度 */
	private Integer dataScala;
	/** 默认值 */
	private String defaultValue;
	/** 是否主键 */
	private boolean primary = false;
	/** 是否外键关联 */
	private TableMap foreign;
	/** 主键生成方式 */
	private EXTRA extra;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		int x = type.indexOf("(");
		if (x > 0) {
			this.type = type.substring(0, x).toLowerCase();
			String sizeStr = type.substring(x + 1);
			sizeStr = sizeStr.replace(")", "");

			String[] sizes = sizeStr.split(",");
			this.setDataLength(Integer.valueOf(sizes[0]));
			try {
				this.setDataScala(Integer.valueOf(sizes[1]));
			} catch (Exception e) {
			}
		} else
			this.type = type.toLowerCase();
	}

	public Integer getDataLength() {
		return dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public Integer getDataScala() {
		return dataScala;
	}

	public void setDataScala(Integer dataScala) {
		this.dataScala = dataScala;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public boolean getPrimary() {
		return primary;
	}

	public void setForeign(TableMap foreign) {
		this.foreign = foreign;
	}

	public TableMap getForeign() {
		return foreign;
	}

	public void setExtra(EXTRA extra) {
		this.extra = extra;
	}

	public EXTRA getExtra() {
		return extra;
	}

	/**
	 * 根据表名，以“_”分隔单词首字母大写
	 * 
	 * @return
	 */
	public String getPropertyName() {
		String propertyName = "";
		String[] names = name.split("_");

		for (String na : names) {
			if ("ID".equals((na.toUpperCase())) && this.getForeign() != null)
				na = "";
			if (na != null && !"".equals(na)) {
				if ("".equals(propertyName))
					propertyName += na.toLowerCase();
				else {
					String endWord = na.substring(1).toLowerCase();
					String startWord = String.valueOf(na.charAt(0));
					propertyName += startWord.toUpperCase()
							+ endWord.toLowerCase();
				}
			}
		}

		return propertyName;
	}

	@Override
	public String toString() {
		return "ColumnMap [dataLength=" + dataLength + ", dataScala="
				+ dataScala + ", defaultValue=" + defaultValue + ", extra="
				+ extra + ", foreign=" + foreign + ", name=" + name
				+ ", primary=" + primary + ", type=" + type + "]";
	}

}
