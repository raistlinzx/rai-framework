package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.test.model.Organization;
import com.rai.framework.test.sessionfacade.OrgFacade;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class OrgSaveAction extends GeneralAction {
	@Autowired
	private OrgFacade orgFacade;

	@RequestMapping(value = "/admin/orgSave")
	public String actionExecute(HttpServletRequest request) throws Exception {

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
		return "common/referer";
	}

	public void setOrgFacade(OrgFacade orgFacade) {
		this.orgFacade = orgFacade;
	}

}
