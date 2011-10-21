package com.rai.framework.test.web.action.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.Organization;
import com.rai.framework.test.model.Person;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class PersonAction extends GeneralAction {

	@Override
	protected String actionExecute() throws Exception {

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

		return SUCCESS;
	}

}
