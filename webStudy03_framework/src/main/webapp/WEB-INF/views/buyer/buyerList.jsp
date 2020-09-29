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
<table class="table table-bordered">
	<thead>
		<tr>
			<th>행번호</th>
			<th>거래처명</th>
			<th>분류명</th>
			<th>소재지</th>
			<th>당담자</th>
			<th>연락처</th>
			<th>이메일</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="buyerList" value="${pagingVO.data }" />
		<c:choose>
			<c:when test="${not empty buyerList }">
				<c:forEach items="${buyerList }" var="buyer">
					<tr>
						<c:url value="/buyer/buyerView.do" var="viewURL">
							<c:param name="what" value="${buyer.buyer_id }"/>
						</c:url>
						<td>${buyer.rnum }</td>
						<td><a class='byAjax' href="${viewURL }">${buyer.buyer_name }</a></td>
						<td>${buyer.lprod_nm }</td>
						<td>${buyer.buyer_add1 }</td>
						<td>${buyer.buyer_charger }</td>
						<td>${buyer.buyer_comtel }</td>
						<td>${buyer.buyer_mail }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="col">거래처 목록이 없음.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7">
				<div class="card-footer d-block d-md-flex align-items-center d-print-none">
					<div class="form-inline mb-3 justify-content-center">
						<select id="searchType" class="form-control mr-2">
							<option value="">전체</option>
							<option value="name">거래처명</option>
							<option value="address">소재지</option>
							<option value="lgu">분류명</option>
						</select>
						<input type="text" id="searchWord"  class="form-control mr-2"
							value="${param.searchWord }"
						/>
						<input type="button" value="검색" id="searchBtn" class="btn btn-info mr-2"/>
						<a class="btn btn-primary byAjax" href="<c:url value='/buyer/buyerInsert.do'/>">신규등록</a>
					</div>
					<nav class="d-flex ml-md-auto d-print-none" aria-label="Pagination" id="pagingArea">
						${pagingVO.pagingHTML_BS }
					</nav>
				</div>	
			</td>
		</tr>
	</tfoot>
</table>
<form id="searchForm" action="<c:url value='/buyer/buyerList.do'/>">
	<input type="hidden" name="page" />
	<input type="hidden" name="searchType" value="${param.searchType }"/>
	<input type="hidden" name="searchWord" value="${param.searchWord }"/>
</form>

<script type="text/javascript">
	$("#insertBtn").on("click", function(){
		location.href="<c:url value='/buyer/buyerInsert.do' />";
	});
	var searchTypeTag = $("#searchType");
	searchTypeTag.val("${param.searchType }");
	var searchWordTag = $("#searchWord");
	var searchForm = $("#searchForm");
	var searchBtn = $("#searchBtn").on("click", function(){
		let searchType = searchTypeTag.val();
		let searchWord = searchWordTag.val();
		searchForm.find("[name='searchType']").val(searchType); 
		searchForm.find("[name='searchWord']").val(searchWord); 
		searchForm.find("[name='page']").val(1);
		searchForm.submit();
	});
	$("#pagingArea").on("click", "a", function(event){
		event.preventDefault();
		let page = $(this).data("page");
		searchForm.find("[name='page']").val(page);
		searchForm.submit();
		return false;
	});
</script>
</body>
</html>


















