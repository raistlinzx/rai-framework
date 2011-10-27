package com.rai.framework.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rai.framework.model.common.ConditionModel;
import com.rai.framework.model.common.QueryModel;
import com.rai.framework.model.common.ConditionModel.Condition;
import com.rai.framework.model.common.ConditionModel.MatchMode;

/**
 * POJO工具<br>
 * 主要功能:<br>
 * 1.普通POJO对象与Map<String,String>之间转换<br>
 * 2.QueryModel与Map<String,String>之间转换
 * 
 * @author zhaoxin
 * @version 2010-08-24
 */
public class POJOUtils {
	private static final Log log = LogFactory.getLog(POJOUtils.class);

	public static final String SPLIT = "-";

	/**
	 * 将POJO对象转换为Map<String,Object><br>
	 * Map的Key就是POJO对象的属性对应get方法名字符串，Map的Value就是POJO对象的值对象<br>
	 * 
	 * <pre>
	 * 如:getCreateTime对应key值就是createTime,value就是Timestamp对象
	 * </pre>
	 * 
	 * @param obj
	 *            POJO对象
	 * @return Map<String,Object>
	 */
	public final static Map<String, Object> pojo2Map(Object obj) {

		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
			Class<? extends Object> c = obj.getClass();
			Method m[] = c.getMethods();
			// 循环所有get方法，取属性名并执行方法取值
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().indexOf("get") == 0) {
					String name = m[i].getName().toLowerCase().substring(3, 4)
							+ m[i].getName().substring(4);
					log.debug("--[pojo2Map]-- 属性名:" + name);
					log.debug("--[pojo2Map]-- 值:"
							+ m[i].invoke(obj, new Object[0]));
					// System.out.println("属性名：" + name);
					// System.out.println("值：" + m[i].invoke(obj, new
					// Object[0]));

					// 执行方法取值
					hashMap.put(name, m[i].invoke(obj, new Object[0]));
				}
			}
		} catch (Throwable e) {
			log.error("pojo2Map error", e);
		}
		return hashMap;
	}

	/**
	 * Map映射保存为对应POJO对象。只复制Map中提及的属性，其他属性不动
	 * 
	 * @param className
	 *            POJO对象类名全称
	 * @param pojo
	 *            对象。如果为空则创建新对象
	 * @param map
	 *            Map映射
	 * @return 保存后的POJO对象
	 */
	public final static Object map2Pojo(String className, Object pojo,
			Map<String, Object> map) {

		try {
			// 如果参数中的对象为空，则创建新对象
			if (pojo == null)
				pojo = Class.forName(className).newInstance();

			Iterator<String> it = map.keySet().iterator();

			while (it.hasNext()) {
				// 赋值，只复制map中提及的属性，其他属性不动
				String key = it.next();
				PropertyUtils.setProperty(pojo, key, map.get(key));
				log.debug("--[map2Pojo]-- key=" + key + ",value="
						+ map.get(key));
			}
		} catch (Throwable e) {
			// 有异常则返回null
			log.error("map2Pojo error", e);
			return null;
		}

		return pojo;
	}

	/**
	 * request中参数Map复制属性到pojo对象 <br>
	 * 
	 * <pre>
	 * 1.此方法对应map中key和value都是String时，复制pojo用
	 * 2.可以支持对象嵌套，目前只能嵌套一层
	 *     如:
	 *     Order中有一个对象Product
	 *     Product有一个属性name
	 *     key=product.name,value="test"
	 *     映射会返回一个Product对象，并且其name值为test
	 * </pre>
	 * 
	 * @param className
	 *            类名全称
	 * @param pojo
	 *            pojo对象不能为空
	 * @param requestMap
	 *            request中获取到的参数map<K,V>。K=pojo中的对象名,V=值
	 * @return POJO对象
	 */
	public final static Object requestMap2Pojo(String className, Object pojo,
			Map<String, String> requestMap) {
		Map<String, Object> map = null;
		try {
			// 先对map进行reValue操作，将对应value转换为目前类型
			// 并进行嵌套生成嵌套类对象。
			log.debug("--[requestMap2Pojo]-- className=" + className);
			log.debug("--[requestMap2Pojo]-- requestMap=" + requestMap);
			map = requestMapRevalue(Class.forName(className), requestMap, true);
			log.debug("--[requestMap2Pojo]-- map=" + map);

		} catch (Exception e) {
			log.error("requestMap2Pojo error", e);
			//
			// System.err.println(e);
			// System.err.println("map="+map);
			// System.err.println("requestMap="+requestMap);
		}

		// 返回映射Pojo对象
		return map2Pojo(className, pojo, map);
	}

	/**
	 * 先对map进行reValue操作，将对应value转换为目前类型
	 * 
	 * @param c
	 *            对象Class
	 * @param requestMap
	 *            map映射表
	 * @param map2Object
	 *            是否映射嵌套属性
	 * @return
	 * @throws ParseException
	 */
	public final static Map<String, Object> requestMapRevalue(Class<?> c,
			Map<String, String> requestMap, boolean map2Object) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Iterator<String> it = requestMap.keySet().iterator();
			Object instance = c.newInstance();
			log.debug("--[requetMapRevalue]-- newInstance=" + instance);
			while (it.hasNext()) {
				String key = it.next();
				String strV = requestMap.get(key);

				map = getPropertyMap(map, instance, key, strV, map2Object);
			}

		} catch (Throwable e) {
			// System.err.println("requestMapRevalue error");
			// System.err.println(e);
			log.error("requestMapRevalue error", e);
		}

		return map;
	}

	/**
	 * 先对map进行reValue操作，将对应value转换为目前类型<br/>
	 * (此方法和requestMapRevalue一样，区别在于此方法传递的queryMap中，key值含有查询条件，需分离)
	 * 
	 * @param c
	 *            对象Class
	 * @param queryMap
	 *            map映射表
	 * @param map2Object
	 *            是否映射嵌套属性
	 * @return
	 * @throws ParseException
	 */
	public final static Map<String, Object> queryMapRevalue(Class<?> c,
			Map<String, String> queryMap, boolean map2Object) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Iterator<String> it = queryMap.keySet().iterator();
			Object instance = c.newInstance();
			log.debug("--[queryMapRevalue]-- newInstance=" + instance);
			while (it.hasNext()) {
				String key = it.next();
				String strV = queryMap.get(key);

				log
						.debug("--[queryMapRevalue]-- key=" + key + ",value="
								+ strV);
				String[] keys = key.split(SPLIT);

				Map<String, Object> subMap = new HashMap<String, Object>();
				subMap = getPropertyMap(subMap, instance, keys[0], strV,
						map2Object);
				map.put(key, subMap.get(keys[0]));
			}

		} catch (Throwable e) {
			// System.err.println("requestMapRevalue error");
			// System.err.println(e);
			log.error("queryMapRevalue error", e);
		}

		return map;
	}

	/**
	 * 根据key值从instance对象中获取对应属性，初始化新实例并赋值<br/>
	 * 返回对应key值的value object对象，map;
	 * 
	 * @param instance
	 * @param key
	 * @param strV
	 * @param map2Object
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static final Map<String, Object> getPropertyMap(
			Map<String, Object> map, Object instance, String key, String strV,
			boolean map2Object) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		log.debug("--[getPropertyMap]-- key=" + key + ",value=" + strV);
		if (key.contains(".")) {
			// 例如:key=user.job.company.id value=1

			// 1.1
			String[] classs = key.split("\\.");

			Object parentObj = instance;
			Object currentObj = null;
			int currentIndex = 0;
			Object[] objs = new Object[classs.length];

			String mapKey = null;
			Object mapObj = null;

			for (String currentKey : classs) {

				Class<?> destClass = PropertyUtils.getPropertyType(parentObj,
						currentKey);

				if (currentIndex == classs.length - 1) {
					Object dest = copyValueFromString(destClass, strV);
					objs[currentIndex] = dest;
				} else {
					currentObj = destClass.newInstance();
					objs[currentIndex] = currentObj;
					parentObj = currentObj;
				}
				log.debug("--[getPropertyMap]-- key contains.(clz="
						+ currentKey + ",p=" + objs[currentIndex] + ")");
				currentIndex++;
			}

			log.debug("--[getPropertyMap]-- map2Object=" + map2Object);
			if (map2Object) {
				mapKey = classs[0];
				mapObj = objs[0];
				Object po = null;
				Object co = null;

				if (map.containsKey(mapKey)) {
					po = map.get(mapKey);
					mapObj = po;
				} else
					po = mapObj;

				for (int index = 1; index < classs.length; index++, po = co, co = null) {
					co = PropertyUtils.getProperty(po, classs[index]);

					if (co == null) {
						co = objs[index];
						PropertyUtils.setProperty(po, classs[index], co);
					}
				}
			} else {
				mapKey = key;
				mapObj = objs[objs.length - 1];
			}
			log
					.debug("--[getPropertyMap]-- KV=[" + mapKey + "," + mapObj
							+ "]");
			map.put(mapKey, mapObj);
			
		} else {
			// key 中不含"."，也就是不涉及对象属性
			// 以当前key值查找对象class，并生成对象
			// (用于普通类型对象，如String,Integer,BigDecimal等等)
			Class<?> destClass = PropertyUtils.getPropertyType(instance, key);
			log.debug("--[getPropertyMap]-- dest.class=" + destClass);
			if (destClass != null) {
				Object dest = copyValueFromString(destClass, strV);
				log.debug("--[getPropertyMap]-- dest=" + dest);
				map.put(key, dest);
			}
		}

		return map;
	}

	/**
	 * 获取某Class的PK对象
	 * 
	 * @param clazz
	 *            类名
	 * @param key
	 *            PK名称
	 * @param value
	 *            值
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public static final Object getPKObject(Class<?> clazz, String key,
			String value) throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, ClassNotFoundException,
			NoSuchMethodException {
		Class<?> destClass = PropertyUtils.getPropertyType(clazz.newInstance(),
				key);
		Object pkObject = copyValueFromString(destClass, value);
		return pkObject;
	}

	/**
	 * 将String值转换成对应类
	 * 
	 * @param destClass
	 * @param strV
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static final Object copyValueFromString(Class<?> destClass,
			String strV) throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Object dest = null;
		if (!StringUtils.isBlank(strV)) {
			// strV值不能为空
			if (java.lang.String.class.equals(destClass)) {
				log
						.debug("--[copyValueFromString]-- destClass=java.lang.String");
				dest = strV;
			} else if (java.sql.Timestamp.class.equals(destClass)
					|| java.util.Date.class.equals(destClass)) {
				log.debug("--[copyValueFromString]-- destClass=" + destClass);
				dest = new DateConverter().convert(destClass, strV);
			} else {
				try {
					log
							.debug("--[copyValueFromString]-- try destClass.newInstance and BeanUtilsEx.copyProperties(dest,strV)");
					dest = destClass.newInstance();
					BeanUtilsEx.copyProperties(dest, strV);
				} catch (Exception e) {
					try {
						log
								.debug("--[copyValueFromString]-- try destClass.getConstructor(String.class)");
						// 利用构造函数
						dest = destClass.getConstructor(String.class)
								.newInstance(strV);
					} catch (NoSuchMethodException ex) {

						try {
							log
									.debug("--[copyValueFromString]-- try destClass.getMethod('valueOf', String.class).invoke(null, strV)");
							// 利用valueOf方法
							dest = destClass.getMethod("valueOf", String.class)
									.invoke(null, strV);
						} catch (Exception e2) {
							log.error("--[copyValueFromString]-- " + destClass
									+ " All copy Method FAILED.", e);
						}

					}
				}
			}
		}
		return dest;
	}

	/**
	 * 根据Map映射表，生成QueryModel查询条件
	 * 
	 * @param queryModel
	 *            现有QueryModel
	 * @param requestMap
	 *            Map映射表
	 * @return 返回生成好的QueryModel
	 */
	public static final QueryModel queryModelFromMap(QueryModel queryModel,
			Map<String, String> requestMap) {

		log.debug("--[queryModelFromMap]-- requestMap=" + requestMap);
		
		// reValue生成queryMap,
		Map<String, Object> queryMap = queryMapRevalue(queryModel.getQueryClass()[0], requestMap,
				false);

		Iterator<String> itr = queryMap.keySet().iterator();

		while (itr.hasNext()) {
			ConditionModel conditionModel = null;
			// 将queryMap的值对应到实际查询key值上，并生成新的ConditionModel查询到QueryModel中
			String key = itr.next();
			Object value = queryMap.get(key);
			String[] keys = key.split(SPLIT);
			switch (keys.length) {
			case 1:
				conditionModel = getNewConditionModel(keys[0], value, null,
						null);
				break;
			case 2:
				conditionModel = getNewConditionModel(keys[0], value, keys[1],
						null);
				break;
			default:
				conditionModel = getNewConditionModel(keys[0], value, keys[1],
						keys[2]);
				break;
			}
			if (conditionModel != null)
				queryModel.add(conditionModel);
		}

		return queryModel;
	}

	public static final QueryModel queryModelFromMap(String className,
			Map<String, String> requestMap) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		return queryModelFromMap(clazz, requestMap);
	}

	public static final QueryModel queryModelFromMap(Class<?> clazz,
			Map<String, String> requestMap) {
		QueryModel queryModel = new QueryModel(clazz);

		return queryModelFromMap(queryModel, requestMap);
	}

	/**
	 * 转换查询条件
	 * 
	 * @param key
	 *            查询关键字
	 * @param value
	 *            查询值
	 * @param conditionStr
	 *            查询条件
	 * @param matchModeStr
	 *            like匹配条件
	 * @return 返回一个ConditionModel
	 */
	private static final ConditionModel getNewConditionModel(String key,
			Object value, String conditionStr, String matchModeStr) {
		Condition condition = null;
		MatchMode matchMode = null;

		if ("gt".equals(conditionStr))
			condition = Condition.gt;
		else if ("ge".equals(conditionStr))
			condition = Condition.ge;
		else if ("lt".equals(conditionStr))
			condition = Condition.lt;
		else if ("le".equals(conditionStr))
			condition = Condition.le;
		else if ("ne".equals(conditionStr))
			condition = Condition.ne;
		else if ("isNull".equals(conditionStr))
			condition = Condition.isNull;
		else if ("isNotNull".equals(conditionStr))
			condition = Condition.isNotNull;
		else if ("like".equals(conditionStr)) {
			// like查询默认按anywhere
			condition = Condition.like;
			if ("start".equals(matchModeStr))
				matchMode = MatchMode.START;
			else if ("end".equals(matchModeStr))
				matchMode = MatchMode.END;
			else
				matchMode = MatchMode.ANYWHERE;
		} else
			condition = Condition.eq;
		// 如果value值是空，并且不是isNull isNotNull查询条件，则返回空
		if (value == null && !Condition.isNull.equals(condition)
				&& !Condition.isNotNull.equals(condition))
			return null;
		return new ConditionModel(key, value, matchMode, condition);
	}

}
