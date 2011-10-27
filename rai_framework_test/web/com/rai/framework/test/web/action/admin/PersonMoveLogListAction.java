package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.PersonMoveLog;
import com.rai.framework.utils.PageControl;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class PersonMoveLogListAction extends GeneralAction {

	@RequestMapping(value="/admin/personMoveLogList")
	public String actionExecute(HttpServletRequest request,Model model) throws Exception {

		QueryModel queryModel = new QueryModel(PersonMoveLog.class);
		queryModel.addOrder("person.id", "ASC");
		queryModel.addOrder("oldOrg.id", "ASC");

		PageControl pageControl = this.createPageControl(request);
		this.generalManager.find(queryModel, pageControl);

		request.setAttribute("pageControl", pageControl);

		return "admin/personMoveLogList";
	}

}
