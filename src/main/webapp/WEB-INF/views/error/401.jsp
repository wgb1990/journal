<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>

<%pageContext.setAttribute("ctx", request.getContextPath());%>

<html>
<head>
	<title>401 - 用户未被授权</title>
</head>

<body>
	<h2>401 - 用户权限不足.</h2>
	<p><a href="<c:url value="/"/>">返回首页</a></p>
</body>
</html>
