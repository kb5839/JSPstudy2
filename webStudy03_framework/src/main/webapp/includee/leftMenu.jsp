<%@page import="java.util.Map.Entry"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.MenuVO"%>
<%@page import="kr.or.ddit.servlet05.Model2PageModuleServlet.ServiceType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<form id="menuForm" action="">
	<input type="hidden" name="service" value=""/>
</form>    
<ul>
	<%
		ServiceType[] types = ServiceType.values();
		for(ServiceType tmp : types){
			MenuVO menu = tmp.getMenuVO();
			%>
			<li><a href="#" class="menuA" data-action="<%=menu.getMenuURI() %>" data-service="<%=menu.getMenuId() %>"><%=menu.getMenuText() %></a></li>
			<%
		}
	%>
</ul>
<ul>
	<c:forEach items="${applicationScope.userList }" var="entry">
		<li>${entry.value.mem_name }
	</c:forEach>	
</ul>
<script type="text/javascript">
	var menuForm = $("#menuForm");
	$(".menuA").on("click", function(event){
		console.log($(this));
		event.preventDefault();
		menuForm.attr("action", "<%=request.getContextPath() %>" + $(this).data("action"));
		menuForm.find("[name='service']").val($(this).data("service"));
		menuForm.submit();
		return false;
	});
</script>















