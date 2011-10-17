package com.rai.framework.test.sessionfacade;

import com.rai.framework.service.common.BaseSessionFacade;

public interface CoderFacade extends BaseSessionFacade {
	/**
	 * 根据Key和tableName，获取next值<br>
	 * 可用于生成主键ID或其他编号
	 * 
	 * @param key
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	int nextIndex(String key, String tableName) throws Exception;

	/**
	 * 申请主键编码<br>
	 * 比如parentId=001,length=3<br>
	 * 第一次生成001001，第二次生成001002,顺序增长<br>
	 * <br>
	 * tableName主要用于区分主键用途<br>
	 * 此方法不只限于用来生成主键，也可以用来生成其他编号
	 * 
	 * 
	 * @param parentId
	 * @param tableName
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public String savePkCoder(String parentId, String tableName, int length)
			throws Exception;
}
