<%@	page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>rai test</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    Welcome to Rai Framework Test. 
    <hr/>
    <ul>
    	<li><a href="${ctx}/admin/orgList.do">机构管理</a></li>
    	<li><a href="${ctx}/admin/personList.do">人员管理</a></li>
    	<li><a href="${ctx}/admin/personMoveLogList.do">人员调动管理</a></li>
    </ul>
  </body>
</html>
