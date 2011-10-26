package com.rai.framework.util.interfaces;

import java.io.OutputStream;
import java.util.Properties;

import com.rai.framework.util.model.TableMap;

/**
 * POJO类对象生成接口
 * 
 * @author zhaoxin
 * 
 */
public interface ClassGenerator {
	void generateClassFile(OutputStream os, TableMap tableMap,
			String templatePath, String packageName, String target,
			Properties datatype) throws Exception;
}
