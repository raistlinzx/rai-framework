package com.rai.framework.util.interfaces;

import java.io.OutputStream;

import com.rai.framework.util.model.TableMap;

/**
 * 属性及配置文件生成
 * 
 * @author zhaoxin
 * 
 */
public interface HBMGenerator {
	void generateFile(OutputStream os, TableMap tableMap, String templatePath,
			String packageName) throws Exception;
}
