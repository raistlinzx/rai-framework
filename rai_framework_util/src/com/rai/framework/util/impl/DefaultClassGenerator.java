package com.rai.framework.util.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

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
		classText = classText.replaceAll("#CLASS_NAME#", packageName + "."
				+ tableMap.getClassName());

		// nowdate
		classText = classText.replaceAll("#NOWDATE#", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

		StringBuffer importBuffer = new StringBuffer("");
		Set<String> importClass = new HashSet<String>();
		StringBuffer propertyBuffer = new StringBuffer("");
		StringBuffer propertyFuncBuffer = new StringBuffer("");

		for (ColumnMap column : tableMap.getColumns()) {
			String type = column.getType();
			String propertyClass = null;
			if (column.getForeign() != null) {
				propertyClass = column.getForeign().getClassName();
			} else {
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
					+ func_PropertyName + "(" + propertyClass + " "
					+ propertyName + ") {\n");
			propertyFuncBuffer.append("\t\treturn " + propertyName + ";\n");
			propertyFuncBuffer.append("\t}\n");
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
