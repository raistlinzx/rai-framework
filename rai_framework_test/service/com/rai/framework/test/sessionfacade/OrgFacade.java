package com.rai.framework.test.sessionfacade;

import com.rai.framework.service.common.BaseSessionFacade;
import com.rai.framework.test.model.Organization;

public interface OrgFacade extends BaseSessionFacade {

	/**
	 * 保存机构使用(包括新建、更新)
	 * 
	 * @param org
	 * @throws Exception
	 */
	void saveOrg(Organization org) throws Exception;
}
