<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#top{
		width: 100%;
		height: 70px;
		background-color: gold;
	}
	#top ul{
		list-style: none;
		padding-top: 30px;
	}
	#top li{
		float: left;
		margin-left: 30px;
	}
	#left{
		width: 20%;
		height: 600px;
		background-color: lightblue;
		float: left;
	}
	#content{
		width: 79%;
		height: 600px;
		background-color: white;
		float: right;
	}
	#footer{
		width: 100%;
		height: 70px;
		background-color: silver;
		float: left;
	}
</style>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
<div id="top">
	<jsp:include page="/includee/topMenu.jsp" />
</div>
<div id="left">
<jsp:include page="/includee/leftMenu.jsp" />
</div>
<div id="content">
	<c:choose>
		<c:when test="${not empty includePage }">
			<jsp:include page="${includePage }" />
		</c:when>
		<c:otherwise>
			그냥 기본 페이지
		</c:otherwise>
	</c:choose>
</div>
<div id="footer">
<jsp:include page="/includee/footer.jsp" />
</div>
</body>
</html>



