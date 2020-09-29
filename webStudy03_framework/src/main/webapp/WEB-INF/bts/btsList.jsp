<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("select:first").on("change", (event)=>{
			let value = event.target.value;
			let select = event.target;
			console.log(value);
<%-- 			location.href="${pageContext.request.contextPath }/bts/getMemberPage.do?member="+value; --%>
			$.ajax({
				url : "${pageContext.request.contextPath }/bts/getMemberPage.do",
				data : {member:value},
				method : "get",
				dataType : "html",
				success : function(resp) {
					$(select).after(resp);
				},
				error : function(errResp) {
				}
			});
		});
	});
</script>
</head>
<body>
<select>
	<c:forEach items="${btsMap }" var="entry">
		<option value="${entry.key }">${entry.value[0] }</option>
	</c:forEach>
</select>
<!-- 하나의 멤버를 선택하면, change 이벤트 핸들러내에서 -->
<!-- "/bts/getMemberPage.do?member=B001" 요청 발생. -->
<!-- 해당 요청의 결과로 멤버의 개인 페이지 랜더링. -->
<!-- 단, bts 폴더 아래에 있는 개인 페이지의 주소를 클라이언트가 모르도록. -->
<!-- 이동하는 과정에서 요청에 대한 모든 정보를 리셋하고, 서버를 가볍게 이동. -->
</body>
</html>














