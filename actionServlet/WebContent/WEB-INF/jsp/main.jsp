<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
  	<title>主页</title>
  </head>
  <body>
  	<h3>主页空空如也。。。 可以下载一张美图</h3>
  	<a href="<%=request.getContextPath() %>/downloadFile.action?fileName=download">&nbsp;下载</a>
  	<a href="<%=request.getContextPath() %>/loginOut.action">&nbsp;退出</a>
  </body>
</html>