package com.rai.framework.util.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Map.Entry;

import com.rai.framework.util.interfaces.HBMGenerator;
import com.rai.framework.util.model.ColumnMap;
import com.rai.framework.util.model.TableMap;

public class DefaultHBMGenerator implements HBMGenerator {

	@Override
	public void generateFile(OutputStream os, TableMap tableMap,
			String templatePath, String packageName) throws Exception {
		String hbmText = FileReaderUtil.readFile(templatePath);
		// nowdate
		hbmText = hbmText.replaceAll("#NOWDATE#", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

		// class_name
		hbmText = hbmText.replaceAll("#TABLE_CLASS_NAME#", packageName + "."
				+ tableMap.getClassName());

		// table_name
		hbmText = hbmText.replaceAll("#TABLE_NAME#", tableMap.getName());

		// table_name
		hbmText = hbmText.replaceAll("#COMMENT#",
				tableMap.getComment() == null ? "" : tableMap.getComment());

		StringBuffer idBuffer = new StringBuffer("");
		StringBuffer propertyBuffer = new StringBuffer("");
		StringBuffer bagBuffer = new StringBuffer("");

		int[] idPosition = findTemplatePosition(hbmText, "ID");
		int[] propertyPosition = findTemplatePosition(hbmText, "PROPERTY");
		int[] normalPosition = findTemplatePosition(hbmText, "NORMAL");
		int[] manyPosition = findTemplatePosition(hbmText, "MANY");
		int[] bagPosition = findTemplatePosition(hbmText, "BAG");

		String template_id = hbmText.substring(idPosition[2], idPosition[3]);
		String template_normal = hbmText.substring(normalPosition[2],
				normalPosition[3]);
		String template_many = hbmText.substring(manyPosition[2],
				manyPosition[3]);
		String template_bag = hbmText.substring(bagPosition[2], bagPosition[3]);

		for (ColumnMap column : tableMap.getColumns()) {

			if (column.getPrimary()) {
				String idStr = template_id;
				// id
				String idextra = "assigned";
				if (ColumnMap.EXTRA.AUTO.equals(column.getExtra()))
					idextra = "native";
				idStr = idStr.replaceAll("#PROPERTY_NAME#", column
						.getPropertyName());
				idStr = idStr.replaceAll("#COLUMN_NAME#", column.getName());
				idStr = idStr.replaceAll("#EXTRA#", idextra);
				idBuffer.append(idStr);
			} else {
				if (column.getForeign() == null) {
					// normal property
					String normalStr = template_normal;
					normalStr = normalStr.replaceAll("#PROPERTY_NAME#", column
							.getPropertyName());
					normalStr = normalStr.replaceAll("#COLUMN_NAME#", column
							.getName());
					propertyBuffer.append(normalStr);
				} else {
					// many-to-one
					String manyStr = template_many;
					manyStr = manyStr.replaceAll("#PROPERTY_NAME#", column
							.getPropertyName());
					manyStr = manyStr.replaceAll("#CLASS_NAME#", packageName
							+ "." + column.getForeign().getClassName());
					manyStr = manyStr.replaceAll("#COLUMN_NAME#", column
							.getName());
					propertyBuffer.append(manyStr);
				}
			}
		}

		for (Entry<ColumnMap, TableMap> foreign : tableMap.getOneToMany()
				.entrySet()) {
			ColumnMap column = foreign.getKey();
			TableMap table = foreign.getValue();

			String bagStr = template_bag;
			bagStr = bagStr.replaceAll("#PROPERTY_NAME#", column
					.getPropertyName()
					+ table.getClassName());
			bagStr = bagStr.replaceAll("#COLUMN_NAME#", column.getName());
			bagStr = bagStr.replaceAll("#CLASS_NAME#", packageName + "."
					+ table.getClassName());
			bagBuffer.append(bagStr);
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

		// bags
		hbmText = hbmText.replaceAll(hbmText.substring(bagPosition[0],
				bagPosition[1]), bagBuffer.toString());
		// properties
		hbmText = hbmText.replaceAll(hbmText.substring(propertyPosition[0],
				propertyPosition[1]), propertyBuffer.toString());
		// id
		hbmText = hbmText.replaceAll(hbmText.substring(idPosition[0],
				idPosition[1]), idBuffer.toString());

		os.write(hbmText.getBytes());
		os.flush();
	}

	protected int[] findTemplatePosition(String template, String tag) {

		int start = template.indexOf("#" + tag + "_START#");
		int end = template.indexOf("#" + tag + "_END#") + tag.length() + 6;
		int template_start = start + tag.length() + 8;
		int template_end = template.indexOf("#" + tag + "_END#") - 1;

		int[] position = { start, end, template_start, template_end };
		return position;
	}

}
