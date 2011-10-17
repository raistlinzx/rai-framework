package com.rai.framework.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * 生成BeanUtils扩展方法<br/>
 * java.util.Date,java.sql.Date,java.sql.Timestamp类型对象复制
 * 
 * @author zhaoxin
 * 
 */
public class BeanUtilsEx extends BeanUtils {
	static {
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
		ConvertUtils.register(new DateConverter(), java.sql.Date.class);
		ConvertUtils.register(new DateConverter(), java.sql.Timestamp.class);
	}

	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
	}
}