package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.test.model.PersonMoveLog;
import com.rai.framework.web.struts.action.common.GeneralAction;

@Controller
public class PersonMoveLogSaveAction extends GeneralAction {

	@RequestMapping(value = "/admin/personMoveLogSave")
	public String actionExecute(HttpServletRequest request, Model model)
			throws Exception {

		String id = request.getParameter("id");
		String cmd = request.getParameter("cmd");
		if ("delete".equals(cmd)) {
			// 删除人员调动记录
			this.generalManager
					.delete(PersonMoveLog.class, Integer.valueOf(id));
			saveReferer(request);
		} else {
			// 保存人员 （包括新建、更新）
			PersonMoveLog personMoveLog = null;
			if (StringUtils.isNotBlank(id))
				personMoveLog = this.generalManager.get(PersonMoveLog.class,
						Integer.valueOf(id));

			personMoveLog = (PersonMoveLog) this.getPojoFromRequestMap(
					"personMoveLog", personMoveLog, request);

			this.generalManager.save(personMoveLog);
		}
		return "common/referer";
	}

}
