package com.rai.framework.test.web.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.Organization;
import com.rai.framework.test.model.Person;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class PersonAction extends GeneralAction {

	@RequestMapping(value="/admin/person")
	public String actionExecute(HttpServletRequest request,Model model) throws Exception {

		String id = request.getParameter("id");
		Person person = null;
		if (StringUtils.isNotBlank(id))
			person = this.generalManager.get(Person.class, Integer.valueOf(id));

		QueryModel queryModel = new QueryModel(Organization.class);
		queryModel.addOrder("id", "ASC");
		queryModel.addOrder("idx", "ASC");

		List<Organization> orgList = this.generalManager.find(queryModel);

		saveReferer(request);
		request.setAttribute("person", person);
		request.setAttribute("orgList", orgList);

		return "admin/person";
	}

}
