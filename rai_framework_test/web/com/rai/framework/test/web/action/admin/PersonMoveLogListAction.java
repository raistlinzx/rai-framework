package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.PersonMoveLog;
import com.rai.framework.utils.PageControl;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class PersonMoveLogListAction extends GeneralAction {

	@Override
	protected String actionExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		QueryModel queryModel = new QueryModel(PersonMoveLog.class);
		queryModel.addOrder("person.id", "ASC");
		queryModel.addOrder("oldOrg.id", "ASC");

		PageControl pageControl = this.createPageControl(request);
		this.generalManager.find(queryModel, pageControl);

		request.setAttribute("pageControl", pageControl);

		return SUCCESS;
	}

}
