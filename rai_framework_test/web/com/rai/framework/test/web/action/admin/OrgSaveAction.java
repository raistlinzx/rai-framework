package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.rai.framework.test.model.Organization;
import com.rai.framework.test.sessionfacade.OrgFacade;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class OrgSaveAction extends GeneralAction {

	private OrgFacade orgFacade;
	
	@Override
	protected String actionExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		String cmd = request.getParameter("cmd");
		if ("delete".equals(cmd)) {
			// 删除机构
			this.generalManager.delete(Organization.class, id);
			saveReferer(request);
		} else {
			// 保存机构 （包括新建、更新）
			Organization org = null;
			if (StringUtils.isNotBlank(id))
				org = this.generalManager.get(Organization.class, id);

			// 自动保存org对象
			org = (Organization) this
					.getPojoFromRequestMap("org", org, request);

			orgFacade.saveOrg(org);
		}
		// 返回之前页
		return REFERER;
	}

	public void setOrgFacade(OrgFacade orgFacade) {
		this.orgFacade = orgFacade;
	}

}
