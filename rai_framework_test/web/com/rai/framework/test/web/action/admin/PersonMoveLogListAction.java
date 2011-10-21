package com.rai.framework.test.web.action.admin;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.PersonMoveLog;
import com.rai.framework.utils.PageControl;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class PersonMoveLogListAction extends GeneralAction {

	@Override
	protected String actionExecute() throws Exception {

		QueryModel queryModel = new QueryModel(PersonMoveLog.class);
		queryModel.addOrder("person.id", "ASC");
		queryModel.addOrder("oldOrg.id", "ASC");

		PageControl pageControl = this.createPageControl(request);
		this.generalManager.find(queryModel, pageControl);

		request.setAttribute("pageControl", pageControl);

		return SUCCESS;
	}

}
