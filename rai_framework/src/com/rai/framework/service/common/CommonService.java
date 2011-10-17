package com.rai.framework.service.common;

import java.io.Serializable;
import java.util.List;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.utils.PageControl;

public abstract interface CommonService<T, ID extends Serializable> {

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<O> O get(ID id) throws Exception;

	/**
	 * @param arg0
	 * @return
	 */
	<O> O save(T t) throws Exception;

	/**
	 * @param t
	 * @return
	 * @throws Exception
	 */
	<O> O update(T t) throws Exception;

	/**
	 * @param id
	 */
	void delete(ID id) throws Exception;	

	/**
	 * 根据QueryModel查询条件，分页查询结果，保存在pageControl中
	 * 
	 * @param queryModel
	 * @param pageControl
	 * @throws Exception
	 */
	void find(QueryModel queryModel, PageControl pageControl) throws Exception;

	/**
	 * 根据QueryModel，获取唯一返回对象<br/>
	 * 返回查询结果list中的第一个对象 list.get(0);
	 * 
	 * @param <O>
	 * @param queryModel
	 * @return
	 */
	<O> O get(QueryModel queryModel) throws Exception;

	/**
	 * 根据QueryModel，查询结果List
	 * 
	 * @param <O>
	 * @param queryModel
	 * @return
	 */
	<O> List<O> find(QueryModel queryModel) throws Exception;

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

	/**
	 * 条件删除
	 * 
	 * @param queryModel
	 * @throws Exception
	 */
	void deleteByQueryModel(QueryModel queryModel) throws Exception;
}
