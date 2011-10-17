package com.rai.framework.dao.common;

import java.io.Serializable;

import com.rai.framework.model.common.ConditionModel;
import com.rai.framework.model.common.QueryModel;

@SuppressWarnings("unchecked")
public class GeneralDAOHibernate extends
		CommonDAOHibernate<Object, Serializable, GeneralDAO> implements
		GeneralDAO {

	@Override
	public <T> T get(Class<?> clazz, Serializable id) throws Exception {
		return (T) this.getHibernateTemplate().get(clazz, id);
	}

	@Override
	public <T> T get(Class<?> clazz, String idname, Serializable id)
			throws Exception {
		QueryModel queryModel = new QueryModel(clazz);
		queryModel.add(ConditionModel.eq(idname, id));
		return (T) this.get(queryModel);
	}

	@Override
	public void delete(Class<?> clazz, Serializable id) throws Exception {
		Object obj = this.get(clazz, id);
		this.delete(obj);
	}

	@Override
	public void delete(Class<?> clazz, String idname, Serializable id)
			throws Exception {
		Object obj = this.get(clazz, idname, id);
		this.delete(obj);
	}

}
