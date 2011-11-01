package com.rai.framework.util.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import com.rai.framework.util.interfaces.ClassGenerator;
import com.rai.framework.util.model.ColumnMap;
import com.rai.framework.util.model.TableMap;

public class DefaultClassGenerator implements ClassGenerator {

	@Override
	public void generateClassFile(OutputStream os, TableMap tableMap,
			String templatePath, String packageName, String target,
			Properties datatype) throws Exception {
		String classText = FileReaderUtil.readFile(templatePath);

		// package_name
		classText = classText.replaceAll("#PACKAGE_NAME#", packageName);

		// class_name
		classText = classText.replaceAll("#CLASS_NAME#", tableMap
				.getClassName());

		// nowdate
		classText = classText.replaceAll("#NOWDATE#", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

		StringBuffer importBuffer = new StringBuffer("");
		Set<String> importClass = new HashSet<String>();
		StringBuffer propertyBuffer = new StringBuffer("");
		StringBuffer propertyFuncBuffer = new StringBuffer("");

		// Columns
		for (ColumnMap column : tableMap.getColumns()) {
			String type = column.getType().toLowerCase();
			String propertyClass = null;
			if (column.getForeign() != null) {
				propertyClass = column.getForeign().getClassName();
			} else {
				if (column.getDataScale() != null && column.getDataScale() != 0
						&& datatype.containsKey(target + "." + type + ".scale"))
					propertyClass = datatype.getProperty(target + "." + type
							+ ".scale");
				else
					propertyClass = datatype.getProperty(target + "." + type);
				// default
				if (propertyClass == null)
					propertyClass = "String";

				if (propertyClass.contains(".")) {
					if (!importClass.contains(propertyClass))
						importClass.add(propertyClass);
					propertyClass = propertyClass.substring(propertyClass
							.lastIndexOf(".") + 1);
				}
			}

			String propertyName = column.getPropertyName();
			String func_PropertyName = propertyName.substring(0, 1)
					.toUpperCase()
					+ propertyName.substring(1);
			propertyBuffer.append("\tprivate " + propertyClass + " "
					+ column.getPropertyName() + ";\n");

			// function set
			propertyFuncBuffer.append("\tpublic void set" + func_PropertyName
					+ "(" + propertyClass + " " + propertyName + ") {\n");
			propertyFuncBuffer.append("\t\tthis." + propertyName + " = "
					+ propertyName + ";\n");
			propertyFuncBuffer.append("\t}\n");

			// function get
			propertyFuncBuffer.append("\tpublic " + propertyClass + " get"
					+ func_PropertyName + "() {\n");
			propertyFuncBuffer.append("\t\treturn " + propertyName + ";\n");
			propertyFuncBuffer.append("\t}\n");
		}

		// OneToMany
		if (!tableMap.getOneToMany().isEmpty()) {
			importBuffer.append("import java.util.List;\n");
			importBuffer.append("import java.util.ArrayList;\n");

			for (Entry<ColumnMap, TableMap> foreign : tableMap.getOneToMany()
					.entrySet()) {
				ColumnMap column = foreign.getKey();
				TableMap targetTable = foreign.getValue();
				String propertyName = column.getPropertyName()
						+ targetTable.getClassName();

				propertyBuffer.append("\tprivate List<"
						+ targetTable.getClassName() + "> " + propertyName
						+ " = new ArrayList<" + targetTable.getClassName()
						+ ">();\n");

				// function set
				String func_PropertyName = propertyName.substring(0, 1)
						.toUpperCase()
						+ propertyName.substring(1);
				propertyFuncBuffer.append("\tpublic void set"
						+ func_PropertyName + "(List<"
						+ targetTable.getClassName() + "> " + propertyName
						+ ") {\n");
				propertyFuncBuffer.append("\t\tthis." + propertyName + " = "
						+ propertyName + ";\n");
				propertyFuncBuffer.append("\t}\n");

				// function get
				propertyFuncBuffer.append("\tpublic List<"
						+ targetTable.getClassName() + "> get"
						+ func_PropertyName + "() {\n");
				propertyFuncBuffer.append("\t\treturn " + propertyName + ";\n");
				propertyFuncBuffer.append("\t}\n");
			}

		}

		// properties
		classText = classText.replaceAll("#PROPERTY_LIST#", propertyBuffer
				.toString());

		// imports
		for (String importStr : importClass) {
			importBuffer.append("import " + importStr + ";\n");
		}
		classText = classText.replaceAll("#IMPORTS#", importBuffer.toString());
		// functions
		classText = classText.replaceAll("#PROPERTY_FUNC_LIST#",
				propertyFuncBuffer.toString());

		os.write(classText.getBytes());
		os.flush();
	}
}
