package com.rai.framework.test.web.action.admin;

import org.apache.commons.lang.StringUtils;

import com.rai.framework.test.model.PersonMoveLog;
import com.rai.framework.web.struts.action.common.GeneralAction;

public class PersonMoveLogSaveAction extends GeneralAction {

	@Override
	protected String actionExecute() throws Exception {

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
		return REFERER;
	}

}
