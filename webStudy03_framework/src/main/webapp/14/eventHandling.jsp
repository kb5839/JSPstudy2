<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>14/eventHandling.jsp</title>
</head>
<body>
<h4> 서버사이드에서 처리할 수 있는 이벤트 종류 </h4>

	lifecycle, attribute
1. request : initialize/destroy, add/remove/replace
2. session : initialize/destroy, add/remove/replace
3. application(ServletContext) : initialize/destroy, add/remove/replace

	이벤트 처리 방법
1. 이벤트 타겟 결정(버튼)
2. 처리할 이벤트 결정(클릭)
3. 이벤트 핸들러 구현(함수) : Listener 구현
4. 타겟에 핸들러를 부착(onclick) : web.xml -> listener
</body>
</html>
















