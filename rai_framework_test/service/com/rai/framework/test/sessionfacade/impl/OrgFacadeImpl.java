package com.rai.framework.test.sessionfacade.impl;

import org.apache.commons.lang.StringUtils;

import com.rai.framework.service.common.BaseSessionFacadeImpl;
import com.rai.framework.test.model.Organization;
import com.rai.framework.test.sessionfacade.CoderFacade;
import com.rai.framework.test.sessionfacade.OrgFacade;

public class OrgFacadeImpl extends BaseSessionFacadeImpl implements OrgFacade {

	private CoderFacade coderFacade;

	@Override
	public void saveOrg(Organization org) throws Exception {
		/**
		 * 传进来的org对象，是由OrgSaveAction中通过getPojoFromRequestMap方法，获得的对象<br>
		 * 如果是新建ORG,则自动获得的对象中parentOrg也会自动生成。<br>
		 * parentOrg.id属性，是从页面上同名复制过来的。可能为空<br>
		 * 如果parentOrg.id为空，就表示父单位为空，需要设置parentOrg=null，否则无法保存。<br>
		 * 一般来说，如果不能保证类型对象不为空，建议在保存前手动处理。
		 * 
		 */
		// 获取parentId
		String parentId = "0";
		if (org.getParentOrg() != null
				&& StringUtils.isNotBlank(org.getParentOrg().getId()))
			parentId = org.getParentOrg().getId();
		else
			org.setParentOrg(null);

		if (StringUtils.isBlank(org.getId())) {
			// org的id为空，则重新生成id。
			String id = coderFacade.savePkCoder(parentId, "ORG", 3);
			org.setId(id);
		}
		// save方法可以同时用于新建和更新保存
		this.generalManager.save(org);
	}

	public void setCoderFacade(CoderFacade coderFacade) {
		this.coderFacade = coderFacade;
	}

}
