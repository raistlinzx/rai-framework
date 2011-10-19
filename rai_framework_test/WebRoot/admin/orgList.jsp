<%@	page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<link href="${ctx }/css/displaytag.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table style="width:100%">
	<tr>
		<td>
			<c:if test="${not empty parentOrg}">
				上级单位:${parentOrg.name}
				&nbsp;
				<a href="${ctx}/admin/orgList.do?parentId=${parentOrg.parentOrg.id}">返回上级</a>
			</c:if>
		</td>
		<td style="text-align:right">
			<a href="${ctx}/admin/org.do?parentId=${parentOrg.id}">新增机构</a>
			&nbsp;&nbsp;
			<a href="${ctx}/">返回首页</a>
		</td>
	</tr>
</table>
<display:table id="row" class="distagtable" name="${pageControl.list}"
	partialList="true" pagesize="${pageControl.pageSize}" size="${pageControl.count}"
	requestURI="${ctx}/admin/orgList.do">
	<display:column title="单位名称">
		<a href="${ctx}/admin/orgList.do?parentId=${row.id}">${row.name}</a>
	</display:column> 
	<display:column property="parentOrg.name" title="上级单位" />
	<display:column title="总人数">${fn:length(row.persons)}</display:column>	
	<display:column title="操作">
		<a href="${ctx}/admin/org.do?id=${row.id}">修改</a>
		| <a href="${ctx}/admin/orgSave.do?cmd=delete&id=${row.id}">删除</a>
	</display:column>
</display:table>

</body>
</html>