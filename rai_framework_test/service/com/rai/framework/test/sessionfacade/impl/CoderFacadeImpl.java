package com.rai.framework.test.sessionfacade.impl;

import com.rai.framework.model.common.ConditionModel;
import com.rai.framework.model.common.QueryModel;
import com.rai.framework.service.common.BaseSessionFacadeImpl;
import com.rai.framework.test.model.Coder;
import com.rai.framework.test.sessionfacade.CoderFacade;

public class CoderFacadeImpl extends BaseSessionFacadeImpl implements
		CoderFacade {

	public int nextIndex(String key, String tableName) throws Exception {
		Coder coder = null;
		int returnValue = 1;

		for (int i = 0; i < 10; i++) {
			coder = this.getCoder(key, tableName);
			try {
				if (coder == null)
					throw new Exception("Coder is null");
				returnValue = coder.getNextIndex();
				coder.setNextIndex(returnValue + 1);
				// generalManager.update(coder);
				generalManager.save(coder);
			} catch (Throwable e) {
				e.printStackTrace();
				continue;
			}
			return returnValue;
		}
		throw new Exception("Coder ERROR!!!");
	}

	private Coder getCoder(String key, String tableName) {
		Coder coder = null;
		try {
			QueryModel condition = new QueryModel(Coder.class);

			condition.add(ConditionModel.eq("[id.id]", key));
			condition.add(ConditionModel.eq("[id.tableName]", tableName));

			coder = generalManager.get(condition);
		} catch (Exception e) {
			e.printStackTrace();
			coder = new Coder(key, tableName);
		}
		if (coder == null)
			coder = new Coder(key, tableName);
		return coder;
	}

	public String savePkCoder(String parentId, String tableName, int length)
			throws Exception {

		int maxId = this.nextIndex(parentId, tableName);

		String id = parentId + String.format("%0" + length + "d", maxId);
		if (parentId == null || parentId.equals("0")) {
			id = String.format("%0" + length + "d", maxId);
		}
		// id += projectManager.getProjectNewCode(4);
		return id;
	}

	public static void main(String[] args) {
		int maxId = 1;

		String id = String.format("%04d", maxId);

		System.out.println("id==" + id);
	}

}
