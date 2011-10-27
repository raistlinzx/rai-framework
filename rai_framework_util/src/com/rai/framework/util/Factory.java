package com.rai.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.rai.framework.util.impl.DefaultClassGenerator;
import com.rai.framework.util.impl.MySQLDatabaseUtil;
import com.rai.framework.util.impl.DefaultHBMGenerator;
import com.rai.framework.util.impl.OracleDatabaseUtil;
import com.rai.framework.util.interfaces.ClassGenerator;
import com.rai.framework.util.interfaces.DatabaseUtil;
import com.rai.framework.util.interfaces.HBMGenerator;
import com.rai.framework.util.model.ColumnMap;
import com.rai.framework.util.model.ForeignMap;
import com.rai.framework.util.model.TableMap;

public class Factory {
	private static Logger log = Logger.getLogger(Factory.class.getName());
	private static Properties config;
	private static Properties datatype;
	private static String CONFIG_FILE_PATH = "config.properties";
	private static String DATATYPE_FILE_PATH = "datatype.properties";

	public static void execute() throws Exception {
		// load properties file
		config = loadPropertiesFile(CONFIG_FILE_PATH);
		datatype = loadPropertiesFile(DATATYPE_FILE_PATH);

		String target = config.getProperty("target");
		String hbmBuild = config.getProperty("hbmfile.build");

		DatabaseUtil databaseUtil = null;
		ClassGenerator classGenerator = new DefaultClassGenerator();
		HBMGenerator hbmGenerator = new DefaultHBMGenerator();
		if ("mysql".equals(target.toLowerCase())) {
			// mysql
			databaseUtil = new MySQLDatabaseUtil(config);
		}
		else if ("oracle".equals(target.toLowerCase())){
			//oracle
			databaseUtil=new OracleDatabaseUtil(config);
		}

		List<TableMap> tableMaps = databaseUtil.loadAllTables();
		log.info("Loading All Tables.");

		for (TableMap table : tableMaps) {
			log.info("Loading Table Columns.[" + table.getName() + "]");
			table.setColumns(databaseUtil.loadAllColumns(table.getName()));
		}

		List<ForeignMap> foreigns = databaseUtil.loadAllForeigns();
		log.info("Loading All Foreigns.");
		for (ForeignMap foreign : foreigns) {
			setForeignColumn(tableMaps, foreign);
		}

		for (TableMap table : tableMaps) {
			String filepath = config.getProperty("output.filepath");
			File dir = new File(filepath);
			if (!dir.exists())
				dir.mkdirs();
			// class file
			File classFile = new File(filepath + "/" + table.getClassName()
					+ ".java");
			OutputStream classFileOS = new FileOutputStream(classFile);
			classGenerator.generateClassFile(classFileOS, table, config
					.getProperty("template.class.path"), config
					.getProperty("class.package_name"), target, datatype);
			classFileOS.close();

			if ("true".equals(hbmBuild)) {
				// hbm file
				File hbmFile = new File(filepath + "/" + table.getClassName()
						+ ".hbm.xml");
				OutputStream hbmFileOS = new FileOutputStream(hbmFile);
				hbmGenerator.generateFile(hbmFileOS, table, config
						.getProperty("template.hbm.path"), config
						.getProperty("class.package_name"));

				hbmFileOS.close();
			}
		}
	}

	protected static Properties loadPropertiesFile(String file) {
		try {
			InputStream inputStream = ClassLoader
					.getSystemResourceAsStream(file);

			Properties properties = new Properties();
			properties.load(inputStream);
			log.info("loading properties file:" + file);
			return properties;
		} catch (IOException e) {
			log.info("NOT EXISTS properties file:" + file);
			e.printStackTrace();
		}
		return null;
	}

	private static void setForeignColumn(List<TableMap> tableMaps,
			ForeignMap foreign) {
		TableMap refTable = findTableMap(tableMaps, foreign.getTableName());
		ColumnMap refColumn = findColumnMap(refTable.getColumns(), foreign
				.getColumnName());

		TableMap targetTable = findTableMap(tableMaps, foreign
				.getReferencedTableName());

		refColumn.setForeign(targetTable);
		targetTable.getOneToMany().put(refColumn, refTable);

		System.out.println("FOREIGN[" + refTable.getClassName() + "("
				+ refColumn.getPropertyName() + ")->"
				+ targetTable.getClassName() + "]");
	}

	public static TableMap findTableMap(List<TableMap> tableMaps,
			String tableName) {
		for (TableMap table : tableMaps) {
			if (table.getName().equals(tableName)) {
				return table;
			}
		}
		return null;
	}

	public static ColumnMap findColumnMap(List<ColumnMap> columnMap,
			String columnName) {
		for (ColumnMap column : columnMap) {
			if (column.getName().equals(columnName)) {
				return column;
			}
		}
		return null;
	}
}
