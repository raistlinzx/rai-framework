<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>

<%@ page import="com.rai.framework.web.struts.action.common.BaseAction" %>

<%
String CTX_PATH = "/index.jsp";

String referer = null;

String refererParam = request.getParameter(BaseAction.REFERER);
String refererRequest = (String)request.getAttribute(BaseAction.REFERER);
String refererSession = (String)session.getAttribute(BaseAction.REFERER);

if ((refererParam != null) && (!refererParam.equals("null")) && (!refererParam.equals(""))&&(!refererParam.equals("true"))) {
  referer = refererParam;
}
else if ((refererRequest != null) && (!refererRequest.equals("null")) && (!refererRequest.equals(""))) {
  referer = refererRequest;
}
else if ((refererSession != null) && (!refererSession.equals("null")) && (!refererSession.equals(""))) {
  referer = refererSession;
}
else {
  referer = request.getHeader("referer");

  if (referer == null) {
    referer = CTX_PATH;
  }
}

System.out.println("REFERER="+referer);
%>
<script language="JavaScript">
  	self.location = '<%= referer %>';  	
</script>