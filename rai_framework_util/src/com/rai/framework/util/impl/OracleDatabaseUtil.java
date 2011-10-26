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

public class OracleDatabaseUtil extends AbstractDatabaseUtil implements
		DatabaseUtil {

	public OracleDatabaseUtil(Properties properties) {
		super(properties);
	}

	@Override
	public List<ColumnMap> loadAllColumns(String tableName) {
		List<ColumnMap> columnNameList = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement
					.executeQuery("select * from user_tab_columns where table_name='"
							+ tableName + "'");
			columnNameList = new ArrayList<ColumnMap>();
			while (rs.next()) {
				ColumnMap column = new ColumnMap();
				column.setName(rs.getString("column_name"));
				column.setType(rs.getString("data_type"));
				String precision = rs.getString("data_precision");
				if (precision == null)
					column.setDataLength(rs.getInt("data_precision"));
				else {
					column.setDataLength(rs.getInt("data_precision"));
					column.setDataScale(rs.getInt("data_scale"));
				}

				String checkPRISql = "select count(1) from user_constraints where table_name='"
						+ tableName + "' and constraint_type='P'";
				ResultSet checkRS = statement.executeQuery(checkPRISql);
				if (checkRS.next())
					column.setPrimary(true);

				column.setExtra(EXTRA.DEFINED);
				System.out.println(column);
				columnNameList.add(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return columnNameList;
	}

	@Override
	public List<ForeignMap> loadAllForeigns() {
		List<ForeignMap> foreignList = null;
		try {
			String sql = "select cc.table_name tb,cc.column_name tc,cr.table_name r_tb,cr.column_name r_tc"
					+ " from user_cons_columns cc,user_constraints c,user_cons_columns cr"
					+ " where c.constraint_name=cc.constraint_name and c.constraint_type='R'"
					+ " and cr.constraint_name=c.r_constraint_name";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			foreignList = new ArrayList<ForeignMap>();
			while (rs.next()) {
				ForeignMap foreign = new ForeignMap();
				foreign.setTableName(rs.getString("tb"));
				foreign.setColumnName(rs.getString("tc"));
				foreign.setReferencedTableName(rs.getString("r_tb"));
				foreign.setReferencedColumnName(rs.getString("r_tc"));

				foreignList.add(foreign);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foreignList;
	}

	@Override
	public List<TableMap> loadAllTables() {
		List<TableMap> tableList = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select * from user_tables");
			tableList = new ArrayList<TableMap>();
			while (rs.next()) {
				TableMap table = new TableMap();
				table.setName(rs.getString("table_name"));
				tableList.add(table);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tableList;
	}

}
