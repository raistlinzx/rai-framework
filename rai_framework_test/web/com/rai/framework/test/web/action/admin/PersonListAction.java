package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.Person;
import com.rai.framework.utils.PageControl;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class PersonListAction extends GeneralAction {

	@RequestMapping(value="/admin/personList")
	public String actionExecute(HttpServletRequest request,Model model) throws Exception {

		QueryModel queryModel = new QueryModel(Person.class);
		queryModel.addOrder("org.id", "ASC");
		queryModel.addOrder("name", "ASC");

		PageControl pageControl = this.createPageControl(request);
		this.generalManager.find(queryModel, pageControl);

		request.setAttribute("pageControl", pageControl);

		return "admin/personList";
	}

}
