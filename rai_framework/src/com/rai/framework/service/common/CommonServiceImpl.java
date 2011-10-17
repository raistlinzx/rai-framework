package com.rai.framework.service.common;

import java.io.Serializable;
import java.util.List;

import com.rai.framework.dao.common.CommonDAO;
import com.rai.framework.model.common.QueryModel;
import com.rai.framework.utils.PageControl;

@SuppressWarnings("unchecked")
public class CommonServiceImpl<T, ID extends Serializable> implements
		CommonService<T, ID> {

	protected CommonDAO<T, ID> dao;

	public void setDao(CommonDAO<T, ID> dao) {
		this.dao = dao;
	}

	@Override
	public <O> O get(ID id) throws Exception {
		return (O) dao.get(id);
	}

	@Override
	public <O> O save(T t) throws Exception {
		return (O) dao.save(t);
	}

	@Override
	public <O> O update(T t) throws Exception {
		return (O) dao.update(t);
	}

	@Override
	public void delete(ID id) throws Exception {
		dao.delete(id);
	}

	@Override
	public void find(QueryModel queryModel, PageControl pageControl)
			throws Exception {
		dao.find(queryModel, pageControl);
	}

	@Override
	public <O> O get(QueryModel queryModel) throws Exception {
		return (O) dao.get(queryModel);
	}

	@Override
	public <O> List<O> find(QueryModel queryModel) throws Exception {
		return (List<O>) dao.find(queryModel);
	}

	@Override
	public Integer count(QueryModel queryModel) throws Exception {
		return dao.count(queryModel);
	}

	@Override
	public void find(PageControl pageControl, String sql, Object... args)
			throws Exception {
		dao.find(pageControl, sql, args);
	}

	@Override
	public List find(String sql, Object... args) throws Exception {
		return dao.find(sql, args);
	}

	@Override
	public void findHQL(PageControl pageControl, String hql, Object... args)
			throws Exception {
		dao.find(pageControl, hql, args);
	}

	@Override
	public List findHQL(String hql, Object... args) throws Exception {
		return dao.find(hql, args);
	}

	@Override
	public void deleteByQueryModel(QueryModel queryModel) throws Exception {
		dao.delete(queryModel);
	}

}
