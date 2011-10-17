package com.rai.framework.web.struts.action.common;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.rai.framework.exception.FrameworkException;
import com.rai.framework.exception.FrameworkRuntimeException;
import com.rai.framework.utils.PageControl;

public abstract class BaseAction extends Action {

	public final static String SUCCESS = "SUCCESS";
	public final static String FAILURE = "FAILURE";
	public final static String REFERER = "REFERER";
	public final static String INDEX = "INDEX";
	public final static String LOGIN = "LOGIN";
	public final static String INPUT = "INPUT";

	public final static String PAGE_TABLE_ID = "row";

	protected final Log logger = LogFactory.getLog(super.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		ActionForward forward = new ActionForward();
		String forwordName = "failure";

		String errorCode = "00001";

		String log = "[00001] Top Exception in BaseAction";
		try {
			if (form != null) {
				trimFormFields(form);
			}
			forwordName = actionExecute(mapping, form, request, response);
		} catch (FrameworkRuntimeException ex) {
			ex.printStackTrace();
			if (this.logger.isErrorEnabled()) {
				this.logger.error("[" + ex.getErrorCode() + "] " + log + ": ",
						ex);
			}
			request.setAttribute("ErrorMsg", ex.getErrorCode());
		} catch (FrameworkException ex) {
			ex.printStackTrace();
			if (this.logger.isErrorEnabled()) {
				this.logger.error("[" + ex.getErrorCode() + "] " + log + ": ",
						ex);
			}
			request.setAttribute("ErrorMsg", ex.getErrorCode());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if (this.logger.isErrorEnabled()) {
				this.logger.error("[" + errorCode + "] " + log + ": ", ex);
			}
			request.setAttribute("ErrorMsg", errorCode);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (this.logger.isErrorEnabled()) {
				this.logger.error("[" + errorCode + "] " + log + ": ", ex);
			}
			request.setAttribute("ErrorMsg", errorCode);
		}
		if (!(messages.isEmpty())) {
			saveMessages(request, messages);

			forward = mapping.findForward(forwordName);
		} else {
			forward = mapping.findForward(forwordName);
		}

		String referer = request.getParameter(REFERER);

		if (StringUtils.isNotBlank(referer))
			if ("true".equals(referer))
				saveReferer(request);
			else
				saveReferer(request, referer);

		return forward;
	}

	protected abstract String actionExecute(ActionMapping paramActionMapping,
			ActionForm paramActionForm,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception;

	protected void trimFormFields(ActionForm form) throws Exception {
		Map properties = PropertyUtils.describe(form);
		Iterator it = properties.keySet().iterator();

		while (it.hasNext()) {
			String property = (String) it.next();
			Object value = PropertyUtils.getSimpleProperty(form, property);
			if ((value == null) || (!(value instanceof String)))
				continue;
			PropertyUtils.setSimpleProperty(form, property, ((String) value)
					.trim());
		}
	}

	//	
	//	
	// @Override
	// protected String actionExecute(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// try {
	// System.out.println("----Referer:" + request.getHeader(REFERER)
	// + "?" + request.getQueryString());
	// String referer = request.getParameter(REFERER);
	//
	// if (StringUtils.isNotBlank(referer))
	// if ("true".equals(referer))
	// saveReferer(request);
	// else
	// saveReferer(request, referer);
	//
	// } catch (RuntimeException e) {
	// e.printStackTrace();
	// }
	// return SUCCESS;
	// }

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
}
