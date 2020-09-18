<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.ddit.vo.DataBasePropertyVO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jdbcDesc.jsp</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("[name='property_name']").val("${param.property_name}");
		$("formaaa:first").on("submit", function(event){
			event.preventDefault();
			let url = this.action?this.action:"";
			let method = $(this).attr("method")?this.method:"get";
			let data = $(this).serialize(); // query string 생성
			console.log(data);
			$.ajax({
				url : url,
				method : method,
				data : data,
				dataType : "json", // Accept, Content-type
				success : function(resp) {
					let trTags = [];
					if(resp.length>0){
						$(resp).each(function(index, dbProp){
							let tr = $("<tr>");
							tr.append(
								$("<td>").text(dbProp.property_name),		
								$("<td>").text(dbProp.property_value),		
								$("<td>").text(dbProp.description)		
							);
							trTags.push(tr);
						});
					}else{
						let tr = $("<tr>").attr({
							colspan:"3"
						}).append($("<td>").text("검색 결과가 없음."));
						trTags.push(tr);
					}
					$("#listArea").html(trTags);
				},
				error : function(errResp) {
				}
			});
			return false;
		});
	});
</script>
</head>
<body>
<h4>JDBC(Java DataBase Connectivity)</h4>

<pre>
	Facade Pattern : Facade 객체를 통해 실객체에 명령을 전달하는 구조.
	JDBC 단계
	
	1. 드라이버를 빌드패스에 추가.(pom.xml)
	2. 드라이버 로딩(드라이버 클래스를 로딩)
	3. Connection 생성
	4. 쿼리 객체 생성
		1) Statement
		2) PreparedStatement
		3) CallableStatement
	5. 쿼리 실행
		1) ResultSet executeQuery(sql)
		2) int executeUpdate(sql)
	6. 결과 집합 핸들링
	7. 자원을 release
	<%
		List<DataBasePropertyVO> list = (List) request.getAttribute("propList");
		DataBasePropertyVO paramVO = (DataBasePropertyVO)request.getAttribute("param");
		List<String> propNames = (List) request.getAttribute("propNames");
	%>
</pre>
<form>
	프로퍼티명:
	<select name="property_name">
		<option value>프로퍼티명선택</option>
		<%
			for(String name : propNames){
				%>
				<option><%=name %></option>
				<%
			}
		%>
	</select>
	프로퍼티값:<input type="text" name="property_value" value="${param.property_value }"/>
	<input type="submit" value="검색" />
</form>
<table>
	<thead>
		<tr>
			<th>PROPERTY_NAME?</th>
			<th>PROPERTY_VALUE?</th>
			<th>DESCRITPION?</th>
		</tr>
	</thead>
	<tbody id="listArea">
		<%
			for(DataBasePropertyVO dbProp :list){
				%>
				<tr>
					<td><%=dbProp.getProperty_name() %></td>
					<td><%=dbProp.getProperty_value() %></td>
					<td><%=dbProp.getDecription() %></td>
				</tr>
				<%
			}
		%>
	</tbody>
</table>
</body>
</html>















