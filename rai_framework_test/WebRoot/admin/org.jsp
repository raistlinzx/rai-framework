<%@	page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<link href="${ctx }/css/displaytag.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form action="${ctx}/admin/orgSave.do" method="post">
	<input type="hidden" name="id" value="${org.id}" />
	<input type="text" name="parentOrg.id" value="${parentOrg.id}" />
	<table>
		<tr>
			<td>上级机构名称</td>
			<td>${parentOrg.name}</td>
		</tr>
		<tr>
			<td>机构名称</td>
			<td><input type="text" name="name" value="${org.name}" /></td>
		</tr>		
		<tr>
			<td>排序</td>
			<td><input type="text" name="idx" value="${org.idx}" /></td>
		</tr>		
		<tr>
			<td colspan="2">
				<input type="submit" value="保存"/>
				&nbsp;
				<input type="button" value="返回" onclick="window.location.href='${ctx}/common/referer.jsp'" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>