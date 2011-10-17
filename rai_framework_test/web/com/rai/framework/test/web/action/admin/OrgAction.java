package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.rai.framework.test.model.Organization;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class OrgAction extends GeneralAction {

	@Override
	protected String actionExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

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
		return SUCCESS;
	}

}
