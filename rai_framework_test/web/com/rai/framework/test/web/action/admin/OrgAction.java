package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.test.model.Organization;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class OrgAction extends GeneralAction {

	@RequestMapping(value="/admin/org")
	public String actionExecute(HttpServletRequest request,Model model) throws Exception {

		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");

		Organization org = null, parentOrg = null;
		if (StringUtils.isNotBlank(id)) {
			// 获取Organization对象，id的类型要准确
			org = this.generalManager.get(Organization.class, id);
			parentOrg = org.getParentOrg();
		}

		if (parentOrg == null && StringUtils.isNotBlank(parentId))
			parentOrg = this.generalManager.get(Organization.class, parentId);

		request.setAttribute("org", org);
		request.setAttribute("parentOrg", parentOrg);

		// 保存前一访问连接
		saveReferer(request);
		return "admin/org";
	}

}
