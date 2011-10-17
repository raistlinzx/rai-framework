package com.rai.framework.dao.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.utils.PageControl;

@SuppressWarnings("unchecked")
public class CommonDAOHibernate<T, ID extends Serializable, DAOImpl extends CommonDAO<T, ID>>
		extends HibernateDaoSupport implements CommonDAO<T, ID> {

	protected final Log logger = LogFactory.getLog(super.getClass());
	private Class persistentClass;

	public CommonDAOHibernate() {
		this.persistentClass = ((Class) ((java.lang.reflect.ParameterizedType) super
				.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	/**
	 * 根据条件查询记录数
	 * 
	 * @param queryModel
	 * @return
	 */
	public Integer count(QueryModel queryModel) throws Exception {
		return QueryFactory.countByQueryModel(getSession(), queryModel);
	}

	/**
	 * 根据查询条件返回集合
	 * 
	 * @param <O>
	 * 
	 * @param queryModel
	 * @return
	 */
	public List<T> find(QueryModel queryModel) throws Exception {
		return (List<T>) QueryFactory
				.findByQueryModel(getSession(), queryModel);
	}

	/**
	 * 根据查询条件返回对象
	 * 
	 * @param queryModel
	 * @return
	 */
	public T get(QueryModel queryModel) throws Exception {
		return (T) QueryFactory.getByQueryModel(getSession(), queryModel);
	}

	/**
	 * 根据查询条件返回分页的集合
	 * 
	 * @param projectQueryModel
	 * @param pageControl
	 * @return
	 */
	public void find(QueryModel queryModel, PageControl pageControl)
			throws Exception {
		QueryFactory
				.findPageByQueryModel(getSession(), pageControl, queryModel);
	}

	@Override
	public T get(ID id) throws Exception {
		return (T) getHibernateTemplate().get(getPersistentClass(), id);
	}

	@Override
	public T update(T t) throws Exception {
		return save(t);
	}

	/**
	 * 获取总记录数
	 * 
	 * @param sql
	 * @return
	 */
	protected String getCountSQL(String sql) {
		String tmpSQL = sql.toLowerCase();

		int fromIndex = tmpSQL.indexOf("from");
		int groupIndex = tmpSQL.lastIndexOf(" group by");
		if (groupIndex > 0)
			sql = sql.substring(0, groupIndex);

		if (fromIndex >= 0)
			return "select count(*) " + sql.substring(fromIndex);
		else
			throw new RuntimeException("sql not match SELECT ... FROM [" + sql
					+ "]");
	}

	/**
	 * 分页查询(只应用与纯SQL查询)
	 * 
	 * @param sql
	 * @param columns
	 * @param start
	 * @param end
	 * @return
	 */
	protected String getPageSQL(String sql, String[] columns, int start, int end) {
		if (columns == null || end < start)
			return null;
		StringBuffer sb = new StringBuffer("select");

		// 显示条件
		for (int i = 0; i < columns.length; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(" ");
			sb.append(columns[i]);
			sb.append(" ");
		}

		sb.append("from ( select a.*,rownum rn from (");

		sb.append(sql);

		sb.append(") a) where rn between ");
		sb.append(start);
		sb.append(" and ");
		sb.append(end);

		return sb.toString();
	}

	/**
	 * 分页查询(只应用与纯SQL查询)
	 * 
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	protected String getPageSQL(String sql, int start, int end) {
		if (end < start)
			return null;
		StringBuffer sb = new StringBuffer(
				"select * from ( select a.*,rownum rn from (");
		sb.append(sql);
		sb.append(") a) where rn between ");
		sb.append(start);
		sb.append(" and ");
		sb.append(end);

		return sb.toString();
	}

	@Override
	public List find(String sql, Object... args) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
		}
		return query.list();
	}

	@Override
	public void find(PageControl pageControl, String sql, Object... args) {

		String countSQL = this.getCountSQL(sql);

		SQLQuery countQuery = this.getSession().createSQLQuery(countSQL);

		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++)
				countQuery.setParameter(i, args[i]);
		}
		int count = ((BigDecimal) countQuery.uniqueResult()).intValue();
		pageControl.setCount(count);
		if (count > 0) {
			SQLQuery query = this.getSession().createSQLQuery(sql);

			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++)
					query.setParameter(i, args[i]);
			}

			query.setMaxResults(pageControl.getPageSize());
			query.setFirstResult(pageControl.getBegin());
			pageControl.setList(query.list());
		}
	}

	@Override
	public List findHQL(String hql, Object... args) {
		Query query = this.getSession().createQuery(hql);
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
		}
		return query.list();
	}

	@Override
	public void findHQL(PageControl pageControl, String hql, Object... args) {

		String countSQL = this.getCountSQL(hql);

		Query countQuery = this.getSession().createQuery(countSQL);

		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++)
				countQuery.setParameter(i, args[i]);
		}
		int count = ((Long) countQuery.uniqueResult()).intValue();
		pageControl.setCount(count);
		if (count > 0) {
			Query query = this.getSession().createQuery(hql);

			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++)
					query.setParameter(i, args[i]);
			}

			query.setMaxResults(pageControl.getPageSize());
			query.setFirstResult(pageControl.getBegin());
			pageControl.setList(query.list());
		}
	}

	@Override
	public void delete(QueryModel queryModel) throws Exception {
		QueryFactory.deleteByQueryModel(getSession(), queryModel);
	}

	public static void main(String[] args) throws Exception {
		String tmpSQL = "select *from C group by id";
		String a = tmpSQL.toLowerCase();
		int i = a.indexOf(" group by");
		if (i >= 0)
			System.out.println(tmpSQL.substring(0, i));

		// int i = a.indexOf("from");
		// if (i >= 0)
		// System.out.println("select count(*) " + tmpSQL.substring(i));
		// String[] a=tmpSQL.split("select(.)+from");
		// System.out.println();
	}

	@Override
	public void delete(T paramT) throws Exception {
		getHibernateTemplate().delete(paramT);
	}

	@Override
	public void delete(ID id) throws Exception {
		delete(get(id));
	}

	@Override
	public T save(T t) throws Exception {
		getHibernateTemplate().saveOrUpdate(t);
		return t;
	}

	public Class getPersistentClass() {
		return this.persistentClass;
	}

}
