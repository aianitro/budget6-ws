<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import='com.anpilog.budget.ws.service.UsersService'%>
<%@page import='com.anpilog.budget.ws.service.impl.UsersServiceImpl'%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email Verification Page</title>
</head>
<body>

	<%
		String token = request.getParameter("token");
		UsersService usersService = new UsersServiceImpl();
		boolean isEmailVerified = usersService.verifyEmail(token);

		if (isEmailVerified) {
	%>

	<p>Thank you! Your email has been verified!</p>

	<%
		} else {
	%>

	<p>Sorry, your email address has not been verified. Try again.</p>

	<%
		}
	%>

</body>
</html>