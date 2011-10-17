package com.rai.framework.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.type.StringType;

/**
 * Hiberante 映射String转码封装
 * 
 * @author rai
 * 
 */
public class CStringType extends StringType implements java.io.Serializable {

	private static final long serialVersionUID = -9215360287351254459L;
	private static final boolean ENCODING_CHANGE = false;

	private final static String sourceEncoding = "gbk";
	private final static String destEncoding = "iso_8859_1";

	public CStringType() {
		super();
	}

	public Object get(ResultSet rs, String name) throws SQLException {
		// System.out.println("--=: Encoding(" + name + ") :=--");
		try {
			if (rs != null && name != null && rs.getString(name) != null) {
				if (ENCODING_CHANGE)
					return new String(rs.getString(name).getBytes(destEncoding), sourceEncoding);
				else
					return escape(rs.getString(name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void set(PreparedStatement st, Object value, int index)
			throws SQLException {
		// System.out.println("--=: Encoding(" + value + ") :=--");
		try {
			if (st != null && value != null && index > 0) {
				if (ENCODING_CHANGE)
					st.setString(index, new String(((String) value)
							.getBytes(sourceEncoding), destEncoding));
				else
					st.setString(index, (String) value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encode(String source) {
		try {
			if (source != null && ENCODING_CHANGE)
				return new String(source.getBytes(sourceEncoding), destEncoding);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return source;
	}

	public static String decode(String source) {
		try {
			if (source != null && ENCODING_CHANGE)
				return new String(source.getBytes(destEncoding), sourceEncoding);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return source;
	}

	private static String escape(String value) {
		String ret = value;
		ret = ret.replaceAll("<", "&lt;");
		ret = ret.replaceAll(">", "&gt;");
		// ret=ret.replaceAll("&", "&amp;");
		ret = ret.replaceAll("\\\"", "&quot;");

		return ret;
	}

	public static void main(String[] arg0) {
		System.out.println(escape("<我们>"));

	}
}