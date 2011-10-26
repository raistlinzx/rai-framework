package com.rai.framework.util.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;

import com.rai.framework.util.interfaces.HBMGenerator;
import com.rai.framework.util.model.ColumnMap;
import com.rai.framework.util.model.TableMap;

public class MySQLHBMGenerator implements HBMGenerator {

	@Override
	public void generateFile(OutputStream os, TableMap tableMap,
			String templatePath, String packageName) throws Exception {
		String hbmText = FileReaderUtil.readFile(templatePath);

		// nowdate
		hbmText = hbmText.replaceAll("#NOWDATE#", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

		// class_name
		hbmText = hbmText.replaceAll("#CLASS_NAME#", packageName + "."
				+ tableMap.getClassName());

		// table_name
		hbmText = hbmText.replaceAll("#TABLE_NAME#", tableMap.getName());

		// table_name
		hbmText = hbmText.replaceAll("#COMMENT#",
				tableMap.getComment() == null ? "" : tableMap.getComment());

		StringBuffer idBuffer = new StringBuffer("");
		StringBuffer propertyBuffer = new StringBuffer("");
		StringBuffer bagBuffer = new StringBuffer("");

		for (ColumnMap column : tableMap.getColumns()) {

			if (column.getPrimary()) {
				// id
				String idextra = "assigned";
				if (ColumnMap.EXTRA.AUTO.equals(column.getExtra()))
					idextra = "native";
				idBuffer.append("\t\t<id name=\"" + column.getPropertyName()
						+ "\" column=\"" + column.getName() + "\">\n");
				idBuffer.append("\t\t\t<generator class=\"" + idextra
						+ "\" />\n");
				idBuffer.append("\t\t</id>\n");
			} else {
				if (column.getForeign() == null) {
					// normal property
					propertyBuffer.append("\t\t<property name=\""
							+ column.getPropertyName() + "\" column=\""
							+ column.getName() + "\" />\n");
				} else {
					// many-to-one
					propertyBuffer.append("\t\t<many-to-one name=\""
							+ column.getPropertyName() + "\" class=\""+packageName+"."+column.getForeign().getClassName()+"\" column=\""
							+ column.getName() + "\" />\n");
				}
			}
		}

		// propertyClass = datatype.getProperty(target + "." + type);
		//
		// // default
		// if (propertyClass == null)
		// propertyClass = "String";
		//
		// if (propertyClass.contains(".")) {
		// if (!importClass.contains(propertyClass))
		// importClass.add(propertyClass);
		// propertyClass = propertyClass.substring(propertyClass
		// .lastIndexOf(".") + 1);
		// }
		// }
		//
		// String propertyName = column.getPropertyName();
		// String func_PropertyName = propertyName.substring(0, 1)
		// .toUpperCase()
		// + propertyName.substring(1);
		// propertyBuffer.append("\tprivate " + propertyClass + " "
		// + column.getPropertyName() + ";\n");
		//
		// // function set
		// propertyFuncBuffer.append("\tpublic void set" + func_PropertyName
		// + "(" + propertyClass + " " + propertyName + ") {\n");
		// propertyFuncBuffer.append("\t\tthis." + propertyName + " = "
		// + propertyName + ";\n");
		// propertyFuncBuffer.append("\t}\n");
		//			
		// // function get
		// propertyFuncBuffer.append("\tpublic "+propertyClass+" get" +
		// func_PropertyName
		// + "(" + propertyClass + " " + propertyName + ") {\n");
		// propertyFuncBuffer.append("\t\treturn " + propertyName + ";\n");
		// propertyFuncBuffer.append("\t}\n");
		// }
		//
		// // properties
		// hbmText = hbmText.replaceAll("#PROPERTY_LIST#", propertyBuffer
		// .toString());
		//
		// // imports
		// for (String importStr : importClass) {
		// importBuffer.append("import " + importStr + ";\n");
		// }
		// hbmText = hbmText.replaceAll("#IMPORTS#", importBuffer.toString());

		// id
		hbmText = hbmText.replaceAll("#ID#", idBuffer.toString());
		// properties
		hbmText = hbmText.replaceAll("#PROPERTY_LIST#", propertyBuffer
				.toString());
		// bags
		hbmText = hbmText.replaceAll("#BAG_LIST#", bagBuffer.toString());

		os.write(hbmText.getBytes());
		os.flush();
	}

}
