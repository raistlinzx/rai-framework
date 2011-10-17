package com.rai.framework.web.struts.action.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.rai.framework.model.common.QueryModel;
import com.rai.framework.service.common.GeneralManager;
import com.rai.framework.utils.POJOUtils;
import com.rai.framework.utils.PageControl;

/**
 * 实现基本CRUD方法的通用Action<br/>
 * 推荐继承此action。<br/>
 * 优点：<br/>
 * 1.已绑定generalManager方法<br/>
 * 2.支持从requestMap直接复制成类对象<br/>
 * <code>
 * a.复制时可自动创建新对象<br/>
 * b.可更新已有对象的属性。更新时，只更新requestMap中key与类属性名相同的对象，其余对象不变。<br/>
 * </code> 3.已定义常用action return string。<br/>
 * 
 * 
 * 
 * @author zhaoxin
 * 
 */
public class GeneralAction extends BaseAction {

	public static String CMD = "cmd";
	public static String REDIRECT_URL = "redirectURL";
	public static String RETURN_KEY = "returnKey";
	public static String CMD_REFERER = "referer";
	public static String TARGET_CLASS = "targetClass";
	public static String PK_NAME = "id";

	protected Map<?, ?> classNameMap;
	protected GeneralManager generalManager;

	private static final Log log = LogFactory.getLog(GeneralAction.class);

	@Override
	protected String actionExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String cmd = request.getParameter(CMD);
		String redirectURL = request.getParameter(REDIRECT_URL);
		String returnKey = request.getParameter(RETURN_KEY);
		String targetClass = request.getParameter(TARGET_CLASS);
		String referer = request.getParameter(CMD_REFERER);

		try {
			if (cmd == null)
				throw new Exception("cmd is null");

			Map<String, String> requestMap = cleanRequestMap(request
					.getParameterMap(), CMD, REDIRECT_URL, RETURN_KEY,
					TARGET_CLASS, CMD_REFERER);
			// 清理requestMap中命令参数

			/** 开始执行操作 */
			String className = getClassName(targetClass);

			Object object = null;
			List<?> list = null;
			// cmd
			/** ----Create---- */
			if ("create".equals(cmd)) {
				object = cmd_create(className, requestMap);
				request.setAttribute(targetClass, object);
			}
			/** ----Update---- */
			else if ("update".equals(cmd)) {
				object = cmd_update(className, requestMap);
				request.setAttribute(targetClass, object);
			}
			/** ----Get---- */
			else if ("get".equals(cmd)) {
				object = cmd_get(className, requestMap);
				request.setAttribute(targetClass, object);
			}
			/** ----Delete---- */
			else if ("delete".equals(cmd)) {
				cmd_delete(className, requestMap);
			}
			/** ----List---- */
			else if ("list".equals(cmd)) {
				list = cmd_list(className, requestMap);
				request.setAttribute("list", list);
			}
			/** ----ListByPage---- */
			else if ("listByPage".equals(cmd)) {

				PageControl pageControl = this.createPageControl(request);

				pageControl = cmd_listByPage(className, requestMap, pageControl);
				request.setAttribute("pageControl", pageControl);
			}
			if ("true".equals(referer))
				saveReferer(request);

			// 返回
			if (redirectURL != null)
				response.sendRedirect(redirectURL);
			else if (returnKey != null)
				return returnKey;
			else if (targetClass != null)
				return targetClass + "." + cmd;

		} catch (Exception e) {
			log.debug(e);
			throw new RuntimeException(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 清理requestMap中多余的值，同时转换request.getParameterMap中String[]
	 * value为String。不支持同名多值
	 * 
	 * @param requestMap
	 * @param keys
	 *            要清理掉的值
	 * @return 清理后的map<String,String>
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, String> cleanRequestMap(Map requestMap,
			String... keys) throws Exception {
		if (keys == null)
			return requestMap;
		List<String> keyList = Arrays.asList(keys);
		Map<String, String> map = new HashMap<String, String>();

		Iterator<String> it = requestMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (!keyList.contains(key)) {
				String[] values = (String[]) requestMap.get(key);
				if (values != null && values.length > 0)
					map.put(key, values[0]);
			}

		}

		return map;

	}

	/**
	 * 根据类短名称，查询配置中的完整类名
	 * 
	 * @param targetClass
	 *            类短名称
	 * @return 完整类名称
	 */
	protected String getClassName(String targetClass) throws Exception {
		if (classNameMap == null)
			throw new Exception("ClassNameMap is null");
		return (String) classNameMap.get(targetClass);
	}

	/**
	 * 从request.getParameterMap中获取值，转换为pojo对象
	 * 
	 * @param targetName
	 *            目标类的短名称
	 * @param target
	 *            如已存在对象
	 * @param request
	 *            request
	 * @return 返回转换好的对象
	 */
	protected Object getPojoFromRequestMap(String targetName, Object target,
			HttpServletRequest request) throws Exception {
		Object object = null;

		// 转换条件map
		Map<String, String> requestMap = cleanRequestMap(request
				.getParameterMap(), CMD, REDIRECT_URL, RETURN_KEY,
				TARGET_CLASS, CMD_REFERER);

		// 如ID字段不为空，则移除idName值，同时根据ID获取对象
		object = POJOUtils.requestMap2Pojo(this.getClassName(targetName),
				target, requestMap);

		return object;
	}

	protected List<?> cmd_list(String className, Map<String, String> requestMap)
			throws Exception {
		// 2. 生成QueryModel
		QueryModel queryModel = POJOUtils.queryModelFromMap(className,
				requestMap);
		// 3. 查询返回
		return generalManager.find(queryModel);
	}

	protected PageControl cmd_listByPage(String className,
			Map<String, String> requestMap, PageControl pageControl)
			throws Exception {
		// 2. 生成QueryModel
		QueryModel queryModel = POJOUtils.queryModelFromMap(className,
				requestMap);
		// 3. 查询返回
		generalManager.find(queryModel, pageControl);
		return pageControl;
	}

	protected Object cmd_get(String className, Map<String, String> requestMap)
			throws Exception {
		// 2. 生成QueryModel
		QueryModel queryModel = POJOUtils.queryModelFromMap(className,
				requestMap);
		// 3. 查询返回
		return generalManager.get(queryModel);
	}

	protected Object cmd_create(String className, Map<String, String> requestMap)
			throws Exception {
		// 2. 生成对象
		Object object = POJOUtils.requestMap2Pojo(className, null, requestMap);
		// 3. 保存返回
		return generalManager.save(object);
	}

	protected Object cmd_update(String className, Map<String, String> requestMap)
			throws Exception {
		// 2. 生成对象
		Object object = POJOUtils.requestMap2Pojo(className, null, requestMap);
		// 3. 保存返回
		return generalManager.update(object);
	}

	protected void cmd_delete(String className, Map<String, String> requestMap)
			throws Exception {
		// requestMap中应仅有一组参数key为idname，value为值;
		if (requestMap.size() != 1)
			throw new RuntimeException("requestMap parameters outbounds:"
					+ requestMap);

		QueryModel queryModel = POJOUtils.queryModelFromMap(className,
				requestMap);
		generalManager.deleteObject(generalManager.get(queryModel));
	}

	public static void setCmd(String cMD) {
		CMD = cMD;
	}

	public static void setRedirectUrl(String rEDIRECT_URL) {
		REDIRECT_URL = rEDIRECT_URL;
	}

	public static void setReturnKey(String rETURN_KEY) {
		RETURN_KEY = rETURN_KEY;
	}

	public static void setTargetClass(String tARGET_CLASS) {
		TARGET_CLASS = tARGET_CLASS;
	}

	public static void setPkName(String pK_NAME) {
		PK_NAME = pK_NAME;
	}

	public void setClassNameMap(HashMap<?, ?> classNameMap) {
		// log.debug("classNameMap=" + classNameMap);
		this.classNameMap = classNameMap;
	}

	public void setGeneralManager(GeneralManager generalManager) {
		this.generalManager = generalManager;
	}

}
