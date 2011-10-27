package com.rai.framework.web.struts.action.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.rai.framework.utils.PageControl;

public abstract class BaseAction {

	public final static String SUCCESS = "SUCCESS";
	public final static String FAILURE = "FAILURE";
	public final static String REFERER = "REFERER";
	public final static String INDEX = "INDEX";
	public final static String LOGIN = "LOGIN";
	public final static String INPUT = "INPUT";

	public final static String PAGE_TABLE_ID = "row";

	protected final Log logger = LogFactory.getLog(super.getClass());

	//
	// public String execute() throws Exception {
	// String forwordName = FAILURE;
	//
	// String errorCode = "00000";
	//
	// String log = "[00000] Top Exception in BaseAction";
	// try {
	// forwordName = actionExecute();
	// } catch (FrameworkRuntimeException ex) {
	// ex.printStackTrace();
	// if (this.logger.isErrorEnabled()) {
	// this.logger.error("[" + ex.getErrorCode() + "] " + log + ": ",
	// ex);
	// }
	// request.setAttribute("ErrorMsg", ex.getErrorCode());
	// } catch (FrameworkException ex) {
	// ex.printStackTrace();
	// if (this.logger.isErrorEnabled()) {
	// this.logger.error("[" + ex.getErrorCode() + "] " + log + ": ",
	// ex);
	// }
	// request.setAttribute("ErrorMsg", ex.getErrorCode());
	// } catch (RuntimeException ex) {
	// ex.printStackTrace();
	// if (this.logger.isErrorEnabled()) {
	// this.logger.error("[" + errorCode + "] " + log + ": ", ex);
	// }
	// request.setAttribute("ErrorMsg", errorCode);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// if (this.logger.isErrorEnabled()) {
	// this.logger.error("[" + errorCode + "] " + log + ": ", ex);
	// }
	// request.setAttribute("ErrorMsg", errorCode);
	// }
	//
	// String referer = request.getParameter(REFERER);
	//
	// if (StringUtils.isNotBlank(referer))
	// if ("true".equals(referer))
	// saveReferer(request);
	// else
	// saveReferer(request, referer);
	//
	// return forwordName;
	// }

//	protected abstract String actionExecute(HttpServletRequest request,
//			HttpServletResponse response) throws Exception;

	protected void saveReferer(HttpServletRequest request) {
		// request.getSession().setAttribute(REFERER,
		// request.getHeader(REFERER) + "?" + request.getQueryString());
		request.getSession().setAttribute(REFERER, request.getHeader(REFERER));
	}

	protected void saveReferer(HttpServletRequest request, String referer) {
		request.getSession().setAttribute(REFERER, referer);
	}

	/**
	 * 创建PageControl对象
	 * 
	 * @param request
	 * @param tableId
	 * @return
	 */
	protected PageControl createPageControl(HttpServletRequest request,
			String tableId) {
		String pageIndexName = new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE);

		String pageIndexValue = request.getParameter(pageIndexName);

		Integer pageIndex = 1;
		if (StringUtils.isNumeric(pageIndexValue)) {
			pageIndex = Integer.valueOf(request.getParameter(pageIndexName));
		}
		return new PageControl(request, pageIndex, PageControl.PAGESIZE);
	}

	/**
	 * 创建PageControl对象
	 * 
	 * @param request
	 * @return
	 */
	protected PageControl createPageControl(HttpServletRequest request) {
		String pageIndexName = new ParamEncoder(PAGE_TABLE_ID)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE);

		String pageIndexValue = request.getParameter(pageIndexName);

		Integer pageIndex = 1;
		if (StringUtils.isNumeric(pageIndexValue)) {
			pageIndex = Integer.valueOf(request.getParameter(pageIndexName));
		}
		return new PageControl(request, pageIndex, PageControl.PAGESIZE);
	}

	/**
	 * 创建PageControl对象
	 * 
	 * @param request
	 * @param tableId
	 * @param pageSize
	 * @return
	 */
	protected PageControl createPageControl(HttpServletRequest request,
			String tableId, int pageSize) {
		String pageIndexName = new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE);

		String pageIndexValue = request.getParameter(pageIndexName);

		Integer pageIndex = 1;
		if (StringUtils.isNumeric(pageIndexValue)) {
			pageIndex = Integer.valueOf(request.getParameter(pageIndexName));
		}
		return new PageControl(request, pageIndex, pageSize);
	}

	/**
	 * 创建PageControl对象
	 * 
	 * @param request
	 * @param pageSize
	 * @return
	 */
	protected PageControl createPageControl(HttpServletRequest request,
			int pageSize) {
		String pageIndexName = new ParamEncoder(PAGE_TABLE_ID)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE);

		String pageIndexValue = request.getParameter(pageIndexName);

		Integer pageIndex = 1;
		if (StringUtils.isNumeric(pageIndexValue)) {
			pageIndex = Integer.valueOf(request.getParameter(pageIndexName));
		}
		return new PageControl(request, pageIndex, pageSize);
	}
	//
	// protected HttpServletRequest request;
	// protected HttpServletResponse response;

}
