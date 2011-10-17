package com.rai.framework.dao.common;

import java.io.Serializable;
import java.util.List;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.utils.PageControl;

/**
 * Generic DAO Interface
 * 
 * @author Xin Zhao
 * 
 * @param <T>
 *            Class Name
 * @param <ID>
 *            PK
 */
public abstract interface CommonDAO<T, ID extends Serializable> {

	/**
	 * get Object by PK_ID<br>
	 * Just used in the class extends CommonDAO defined by user.<br>
	 * 只在泛型类中试用，generalDAO中请使用get(class,id)方法
	 * 
	 * @param id
	 * @return
	 */
	T get(ID id) throws Exception;

	/**
	 * 保存对象,并返回保存后的最新对象
	 * 
	 * @param t
	 * @return
	 */
	T save(T t) throws Exception;

	/**
	 * 更新对象，并返回更新后的最新对象
	 * 
	 * @param t
	 * @return
	 */
	T update(T t) throws Exception;

	/**
	 * 根据ID删除对象<br/>
	 * 只在泛型类中使用，generalDAO中请使用delete(class,id)
	 * 
	 * @param id
	 */
	void delete(ID id) throws Exception;

	void delete(T t) throws Exception;

	/**
	 * 根据条件删除对象
	 * 
	 * @param queryModel
	 */
	void delete(QueryModel queryModel) throws Exception;

	/**
	 * 根据QueryModel查询条件，分页查询结果，保存在pageControl中
	 * 
	 * @param queryModel
	 * @param pageControl
	 */
	void find(QueryModel queryModel, PageControl pageControl) throws Exception;

	/**
	 * 根据QueryModel，获取唯一返回对象<br/>
	 * 返回查询结果list中的第一个对象 list.get(0);
	 * 
	 * @param queryModel
	 * @return
	 */
	T get(QueryModel queryModel) throws Exception;

	/**
	 * 根据QueryModel，查询结果List
	 * 
	 * @param queryModel
	 * @return
	 */
	List<T> find(QueryModel queryModel) throws Exception;

	/**
	 * 根据QueryModel，获取查询结果总数Count
	 * 
	 * @param queryModel
	 * @return
	 */
	Integer count(QueryModel queryModel) throws Exception;

	/**
	 * 根据原生SQL，查询对象结果List。参数通过args传入，要求顺序要一致
	 * 
	 * @param sql
	 *            原生SQL
	 * @param args
	 *            条件参数数组
	 * @return 返回结果List
	 */
	@SuppressWarnings("unchecked")
	List find(String sql, Object... args) throws Exception;

	/**
	 * 根据原生SQL，查询分页结果。参数通过args传入，要求顺序要一致
	 * 
	 * @param pageControl
	 *            分页控件
	 * @param sql
	 *            原生SQL
	 * @param args
	 *            条件参数数组
	 */
	void find(PageControl pageControl, String sql, Object... args)
			throws Exception;

	/**
	 * 根据Hibernate HQL，查询对象结果List。参数通过args传入，要求顺序要一致
	 * 
	 * @param hql
	 *            HQL
	 * @param args
	 *            条件参数数组
	 * @return 返回结果List
	 */
	@SuppressWarnings("unchecked")
	List findHQL(String hql, Object... args) throws Exception;

	/**
	 * 根据Hibernate HQL，查询分页结果。参数通过args传入，要求顺序要一致
	 * 
	 * @param pageControl
	 *            分页控件
	 * @param hql
	 *            HQL
	 * @param args
	 *            条件参数数组
	 */
	void findHQL(PageControl pageControl, String hql, Object... args)
			throws Exception;

}
