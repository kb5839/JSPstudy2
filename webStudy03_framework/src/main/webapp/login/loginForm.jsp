<%@page import="kr.or.ddit.utils.CookieUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.error{
		color: red;
	}
</style>
</head>
<body>
<%
	String savedId = (new CookieUtils(request)).getCookieValue("idCookie");
	String message = (String)session.getAttribute("message");
	if(StringUtils.isNotBlank(message)){
		%>
		<div class="error"><%=message %></div>
		<%
		session.removeAttribute("message"); // flash attribute
	}
%>
<form action="<%=request.getContextPath() %>/login/loginProcess.do" method="post">
	<ul>
		<li>
			아이디 : <input type="text" name="mem_id" value="<%=Objects.toString(savedId, "") %>"/>
			<label><input type="checkbox" name="saveId" value="save"  <%=savedId!=null?"checked":"" %>  />아이디기억하기</label>
		</li>
		<li>
			비밀번호 : <input type="password" name="mem_pass" />
			<input type="submit" value="로그인" />
		</li>
	</ul>
</form>
</body>
</html>









