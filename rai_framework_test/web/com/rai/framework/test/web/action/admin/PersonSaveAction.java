package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.test.model.Person;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class PersonSaveAction extends GeneralAction {

	@RequestMapping(value = "/admin/personSave")
	public String actionExecute(HttpServletRequest request, Model model)
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
		return "common/referer";
	}

}
