package com.rai.framework.test.web.action.admin;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.Person;
import com.rai.framework.utils.PageControl;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class PersonListAction extends GeneralAction {

	@Override
	protected String actionExecute() throws Exception {

		QueryModel queryModel = new QueryModel(Person.class);
		queryModel.addOrder("org.id", "ASC");
		queryModel.addOrder("name", "ASC");

		PageControl pageControl = this.createPageControl(request);
		this.generalManager.find(queryModel, pageControl);

		request.setAttribute("pageControl", pageControl);

		return SUCCESS;
	}

}
