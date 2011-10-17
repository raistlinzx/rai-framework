package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.rai.framework.test.model.Person;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class PersonSaveAction extends GeneralAction {

	@Override
	protected String actionExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		String cmd = request.getParameter("cmd");
		if ("delete".equals(cmd)) {
			// 删除人员
			this.generalManager.delete(Person.class, Integer.valueOf(id));
			saveReferer(request);
		} else {
			// 保存人员 （包括新建、更新）
			Person person = null;
			if (StringUtils.isNotBlank(id))
				person = this.generalManager.get(Person.class, Integer
						.valueOf(id));

			person = (Person) this.getPojoFromRequestMap("person", person,
					request);

			this.generalManager.save(person);
		}
		return REFERER;
	}

}
