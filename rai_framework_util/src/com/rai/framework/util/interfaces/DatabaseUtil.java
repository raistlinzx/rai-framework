package com.rai.framework.util.interfaces;

import java.util.List;

import com.rai.framework.util.model.ForeignMap;

import com.rai.framework.util.model.ColumnMap;
import com.rai.framework.util.model.TableMap;

/**
 * 读取数据库结构使用
 * 
 * @author zhaoxin
 * 
 */
public interface DatabaseUtil {
	
	List<TableMap> loadAllTables();
	
	List<ColumnMap> loadAllColumns(String tableName);

	List<ForeignMap> loadAllForeigns();
}
