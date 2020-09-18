<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/datatable.jsp</title>
<link rel="stylesheet" href="//cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<jsp:include page="/includee/preScript.jsp" />
<script type="text/javascript" src="//cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
// 	non datatable
	$(function(){
		let zipTable = $("#zipTable");
		let pagingArea = $("#pagingArea");
		let pagingA = pagingArea.on('click', "a" ,function(){
			let page = $(this).data("page");
			searchForm.find("[name='page']").val(page);
			searchForm.submit();
			searchForm.find("[name='page']").val(1);
		});
		let searchForm = $("#searchForm").on("submit", function(event){
			event.preventDefault();
			let url = this.action?this.action:location.href;
			let method = this.method?this.method:"get";
			let data = $(this).serialize(); // query string 
			$.ajax({
				url : url,
				method : method,
				data : data,
				dataType : "json",
				success : function(resp) {
					zipTable.find("tbody").empty();
					pagingArea.empty();
					let list = resp.data;
					let trTags = [];
					if(list.length>0){
						$(list).each(function(idx, zipVO){
							trTags.push(
								$("<tr>").append(
									$("<td>").text(zipVO.zipcode),
									$("<td>").text(zipVO.address)
								)
							);
							
						});
					}else{
						trTags.push($("<tr>").html($("<td colspan='2'>").text("우편번호 없음.")));
					}
					zipTable.find("tbody").html(trTags);
					pagingArea.html( resp.pagingHTML );
				},
				error : function(errResp) {
				}
			});
			return false;
		}).submit();
	});
</script>
</head>
<body>
<table id="zipTable">
	<thead>
		<tr>
			<th>우편번호</th>
			<th>주소</th>
		</tr>
	</thead>
	<tbody>
		
	</tbody>
</table>
<form id="searchForm" action="<%=request.getContextPath()%>/searchZip.do" method="get">
	동이름 : <input type="text" name="searchWord" />
	<input type="hidden" name="page" />
	<input type="submit" value="우편번호 검색"/>
</form>
<div id="pagingArea">

</div>
<script type="text/javascript">
// 	$("#zipTable").DataTable({
<%-- 		ajax:"<%=request.getContextPath() %>/searchZip.do", --%>
// 		columns: [
//             { "data": "zipcode" },
//             { "data": "address" },
//         ]
// 	});
</script>
</body>
</html>

















