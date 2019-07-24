<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
	table{
		margin-left: auto;
		margin-right: auto;
		height: 120px;
		width: 320px;
		background-color: bisque;
		margin-top: 10%;
	}
	
</style>
</head>
<body>
	<form class="userForm" action="<%=request.getContextPath() %>/login.action" method="post">
		<table class="userTable">
			<tr>
				<td>用户名：</td>
				<td><input id="username" type="text" name="username" placeholder="请输入用户名"/></td>
				
			</tr>
			<tr>
				<td>密码：</td>
				<td><input id="pw" type="password" name="PW" placeholder="请输入密码"/></td>
			</tr>
			<tr>
				<td>
					<%
						String User_Not_Found = (String)session.getAttribute("user_not_found");
						User_Not_Found = User_Not_Found == null ? "":User_Not_Found;
						out.print(User_Not_Found);
					%>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="登录"/></td>
			</tr>
			
		</table>
	</form>
</body>
</html>