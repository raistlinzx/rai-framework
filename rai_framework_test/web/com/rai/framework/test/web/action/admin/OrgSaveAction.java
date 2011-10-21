package com.rai.framework.test.web.action.admin;

import org.apache.commons.lang.StringUtils;

import com.rai.framework.test.model.Organization;
import com.rai.framework.test.sessionfacade.OrgFacade;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class OrgSaveAction extends GeneralAction {

	private OrgFacade orgFacade;

	@Override
	protected String actionExecute() throws Exception {

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
