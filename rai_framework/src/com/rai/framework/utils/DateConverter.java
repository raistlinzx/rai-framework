package com.rai.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {

	public static final String[] PATTERN = { "yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd" };

	@SuppressWarnings("unchecked")
	public Object convert(Class arg0, Object arg1) {
		if (arg1 == null)
			return null;

		long time = 0L;
		if (java.util.Date.class.equals(arg1.getClass()))
			time = ((java.util.Date) arg1).getTime();
		else if (java.sql.Date.class.equals(arg1.getClass()))
			time = ((java.sql.Date) arg1).getTime();
		else if (java.sql.Timestamp.class.equals(arg1.getClass()))
			time = ((java.sql.Timestamp) arg1).getTime();
		else {
			String p = arg1.toString();
			if (p == null || p.trim().length() == 0) {
				return null;
			}

			Date value = null;

			for (String pattern : PATTERN) {
				try {
					SimpleDateFormat df = new SimpleDateFormat(pattern);
					value = df.parse(p.trim());
					break;
				} catch (Exception e) {
					continue;
				}
			}
			if (value == null)
				return null;
			time = value.getTime();

		}

		if (arg0.equals(java.util.Date.class))
			return new java.util.Date(time);
		else if (arg0.equals(java.sql.Date.class))
			return new java.sql.Date(time);
		else if (arg0.equals(java.sql.Timestamp.class))
			return new java.sql.Timestamp(time);
		return null;

	}

}