package com.rai.framework.service.common;

import java.io.Serializable;

import com.rai.framework.dao.common.GeneralDAO;

public class GeneralManagerImpl extends CommonServiceImpl<Object, Serializable>
		implements GeneralManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> clazz, Serializable id) throws Exception {
		return (T) ((GeneralDAO) dao).get(clazz, id);
	}

	@Override
	public void delete(Class<?> clazz, Serializable id) throws Exception {
		((GeneralDAO) dao).delete(clazz, id);
	}

	public void deleteObject(Object obj) throws Exception {
		((GeneralDAO) dao).delete(obj);
	}

	@Override
	public void delete(Class<?> clazz, String idname, Serializable id)
			throws Exception {
		((GeneralDAO) dao).delete(clazz, idname, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<?> clazz, String idname, Serializable id)
			throws Exception {
		return (T) ((GeneralDAO) dao).get(clazz, idname, id);
	}

	@Override
	@Deprecated
	public void delete(Serializable id) {

	}

	@Override
	@Deprecated
	public <O> O get(Serializable id) {
		return null;
	}
}
