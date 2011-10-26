package com.rai.framework.util.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.rai.framework.util.interfaces.DatabaseUtil;
import com.rai.framework.util.model.ColumnMap;
import com.rai.framework.util.model.ForeignMap;
import com.rai.framework.util.model.TableMap;
import com.rai.framework.util.model.ColumnMap.EXTRA;

public class MySQLDatabaseUtil extends AbstractDatabaseUtil implements
		DatabaseUtil {

	public MySQLDatabaseUtil(Properties properties) {
		super(properties);
	}

	@Override
	public List<ColumnMap> loadAllColumns(String tableName) {
		List<ColumnMap> columnNameList = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("show columns from "
					+ tableName);
			columnNameList = new ArrayList<ColumnMap>();
			while (rs.next()) {
				ColumnMap column = new ColumnMap();
				column.setName(rs.getString("field"));
				column.setType(rs.getString("type"));
				String type=rs.getString("type");
				int x = column.getType().indexOf("(");
				
				if (x > 0) {
					column.setType(type.substring(0, x).toLowerCase());
					String sizeStr = type.substring(x + 1);
					sizeStr = sizeStr.replace(")", "");

					String[] sizes = sizeStr.split(",");
					column.setDataLength(Integer.valueOf(sizes[0]));
					try {
						column.setDataScale(Integer.valueOf(sizes[1]));
					} catch (Exception e) {
					}
				} else
					column.setType(type.toLowerCase());
				column.setPrimary("PRI".equals(rs.getString("key")));

				EXTRA extra = EXTRA.DEFINED;
				if ("auto_increment".equals(rs.getString("extra")))
					extra = EXTRA.AUTO;
				column.setExtra(extra);
				System.out.println(column);
				columnNameList.add(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return columnNameList;
	}

	@Override
	public List<TableMap> loadAllTables() {
		List<TableMap> tableList = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("show tables");
			tableList = new ArrayList<TableMap>();
			while (rs.next()) {
				TableMap table = new TableMap();
				table.setName(rs.getString(1));
				tableList.add(table);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tableList;
	}

	@Override
	public List<ForeignMap> loadAllForeigns() {
		List<ForeignMap> foreignList = null;
		try {
			String sql = "select * from information_schema.KEY_COLUMN_USAGE where CONSTRAINT_SCHEMA='"
					+ database + "' and REFERENCED_COLUMN_NAME is not null";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			foreignList = new ArrayList<ForeignMap>();
			while (rs.next()) {
				ForeignMap foreign = new ForeignMap();
				foreign.setTableName(rs.getString("table_name"));
				foreign.setColumnName(rs.getString("column_name"));
				foreign.setReferencedTableName(rs
						.getString("referenced_table_name"));
				foreign.setReferencedColumnName(rs
						.getString("referenced_column_name"));

				foreignList.add(foreign);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foreignList;
	}

}
