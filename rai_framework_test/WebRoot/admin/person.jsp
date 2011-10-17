<%@	page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<link href="${ctx }/css/displaytag.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form action="${ctx}/admin/personSave.do" method="post">
	<input type="hidden" name="id" value="${person.id}" />
	<table>
		<tr>
			<td>姓名</td>
			<td><input type="text" name="name" value="${person.name}" /></td>
		</tr>
		<tr>
			<td>性别</td>
			<td>
				<select name="sex">
					<option value="1" ${person.sex eq 1 ? 'selected="selected"' : ''} >男</option>
					<option value="2" ${person.sex eq 2 ? 'selected="selected"' : ''} >女</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>年龄</td>
			<td><input type="text" name="age" value="${person.age}" /></td>
		</tr>
		<tr>
			<td>工作单位</td>
			<td>
				<select name="org.id">
					<c:forEach var="org" items="${orgList}">
						<option value="${org.id}" ${org.id eq person.org.id ? 'selected="selected"' : ''} >${org.name}</option>
					</c:forEach>
				</select>
			</td>
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