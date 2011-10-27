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
import com.rai.framework.test.model.PersonMoveLog;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class PersonMoveLogAction extends GeneralAction {

	@RequestMapping(value="/admin/personMoveLog")
	public String actionExecute(HttpServletRequest request,Model model) throws Exception {

		String id = request.getParameter("id");
		PersonMoveLog personMoveLog = null;
		if (StringUtils.isNotBlank(id))
			personMoveLog = this.generalManager.get(PersonMoveLog.class,
					Integer.valueOf(id));

		QueryModel queryModel = new QueryModel(Organization.class);
		queryModel.addOrder("parentOrg.id", "ASC");
		queryModel.addOrder("idx", "ASC");

		List<Organization> orgList = this.generalManager.find(queryModel);

		queryModel = new QueryModel(Person.class);
		queryModel.addOrder("name", "ASC");

		List<Person> personList = this.generalManager.find(queryModel);

		saveReferer(request);
		request.setAttribute("personMoveLog", personMoveLog);
		request.setAttribute("orgList", orgList);
		request.setAttribute("personList", personList);

		return "admin/personMovLog";
	}

}
