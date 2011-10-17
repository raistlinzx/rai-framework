<%@	page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>Failure</title>
		<link href="${ctx}/css/style1.css" type=text/css rel=stylesheet>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript">
			function forback()
			{
				if(window.history.length==0)
				{
					window.close();
				}
				else
				{
					window.history.back();
				}
			}
	</script>
		<style type="text/css">
#Error {
	COLOR: #B22222;
	FONT-SIZE: 14px;
}
</style>
	</head>
	<body leftmargin="0" topmargin="0" class="body1">
		<center>
			<TABLE width="100%" height="600" border="0" cellPadding="0"
				cellSpacing="0">
				<tr>
					<td align="center">
						<table width="70%" border="0" cellpadding="1" cellspacing="0"
							bordercolor="#BDC7D6" bgcolor="#BDC7D6">
							<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										bordercolor="#BDC7D6" bgcolor="#FFFFFF" class="lankuang1">
										<tr>
											<td height="25" align="left" valign="middle"
												bgcolor="#EFF3F7" class="lanbottom">
												<strong style="color: #5F9EA0">报错提醒：</strong>
											</td>
										</tr>
										<tr>
											<td height="120" align="center">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td width="27%" align="center">
															<img src="${ctx}/images/ico.jpg" width="93" height="77">
														</td>
														<td width="73%" align="left" valign="middle">
															<span id="Error">${app_ErrorMessage[ErrorMsg] }!</span>
														</td>
													</tr>
													<tr>
														<td colspan="2" align="center">
															<input type="button" name="forward" value="返回  "
																class="but2" onClick="forback();">
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</TABLE>
		</center>
	</body>
</html>
