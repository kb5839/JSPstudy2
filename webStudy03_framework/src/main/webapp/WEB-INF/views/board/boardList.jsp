<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
	<table id="boardTable" class="table table-bordered">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:set value="${pagingVO.data }" var="dataList" />
			<c:choose>
				<c:when test="${not empty dataList }">
					<c:forEach var="board" items="${dataList }">
						<c:url value="/board/boardView.do" var="boardViewURL">
							<c:param name="what" value="${board.bo_no }" />
						</c:url>
						<tr>
							<td>${board.rnum }</td>
							<td><a href="${boardViewURL }">${board.bo_title }(글번호:${board.bo_no })</a></td>
							<td>${board.bo_writer }</td>
							<td>${board.bo_date }</td>
							<td>${board.bo_hit }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="6">검색 조건에 맞는 게시글이 없음.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5" id="pagingArea">
					${pagingVO.pagingHTML_BS }
				</td>
			</tr>
		</tfoot>
	</table>
<form id="searchForm" action="${pageContext.request.contextPath }/board/boardList.do" class="form-inline">
	<input type="hidden" name="page" />
	<select name='searchType' class="form-control mr-3">
		<option value="all">전체</option>
		<option value="title">제목</option>
		<option value="writer">작성자</option>
		<option value="content">내용</option>
	</select>
	<input type="text" class="form-control mr-3" name="searchWord" />
	<input type="submit" class="btn btn-primary" value="검색" />
</form>
<script type="text/javascript">
	let listTable = $("#boardTable");
	let pagingArea = $("#pagingArea");
	let pagingA = pagingArea.on('click', "a" ,function(){
		let page = $(this).data("page");
		searchForm.find("[name='page']").val(page);
		searchForm.submit();
		searchForm.find("[name='page']").val(1);
		return false;
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
				listTable.find("tbody").empty();
				pagingArea.empty();
				let list = resp.data;
				let trTags = [];
				if(list.length>0){
					$(list).each(function(idx, board){
						trTags.push(
							$("<tr>").append(
								$("<td>").text(board.rnum),
								$("<td>").html(
										$("<a>").text(board.bo_title+"(글번호:"+board.bo_no+")")
												.attr("href", "${pageContext.request.contextPath}/board/boardView.do?what="+board.bo_no)
												.data("what", board.bo_no)
								),
								$("<td>").text(board.bo_writer),
								$("<td>").text(board.bo_date),
								$("<td>").text(board.bo_hit)
							)
						);
						
					});
				}else{
					trTags.push($("<tr>").html($("<td colspan='5'>").text("조건에 맞는 게시글이 없음.")));
				}
				listTable.find("tbody").html(trTags);
				pagingArea.html( resp.pagingHTML_BS );
			},
			error : function(errResp) {
			}
		});
		return false;
	});
</script>	
</body>
</html>