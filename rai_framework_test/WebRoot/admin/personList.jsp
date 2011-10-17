<%@	page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<link href="${ctx }/css/displaytag.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table style="width:100%">
	<tr>
		<td style="text-align:right">
			<a href="${ctx}/admin/person.do">新增人员</a>
			&nbsp;&nbsp;
			<a href="${ctx}/">返回首页</a>
		</td>
	</tr>
</table>
<display:table id="row" class="distagtable" name="${pageControl.list}"
	partialList="true" pagesize="${pageControl.pageSize}" size="${pageControl.count}"
	requestURI="${ctx}/admin/personList.do">
	<display:column property="name" title="姓名" />
	<display:column property="sex" title="性别" />
	<display:column property="age" title="年龄" />
	<display:column property="org.name" title="单位" />
	<display:column title="操作">
		<a href="${ctx}/admin/person.do?id=${row.id}">修改</a>
		| <a href="${ctx}/admin/personSave.do?cmd=delete&id=${row.id}">删除</a>
	</display:column>
</display:table>

</body>
</html>