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
<form id="searchForm" action="${pageContext.request.contextPath }/prod/prodList.do" class="form-inline">
	<input type="hidden" name="page" />
	<select class="form-control mr-3" name="prod_lgu">
		<option value>상품분류</option>
		<c:forEach items="${lprodList }" var="lprod">
			<option value="${lprod.lprod_gu }">${lprod["lprod_nm"] }</option>
		</c:forEach>
	</select>
	<select class="form-control mr-3" name="prod_buyer">
		<option value>거래처</option>
		<c:forEach items="${buyerList }" var="buyer">
			<option value="${buyer.buyer_id }" class="${buyer.buyer_lgu }">${buyer["buyer_name"] }</option>
		</c:forEach>
	</select>
	<input type="text" name="prod_name" class="form-control mr-3" />
	<input type="submit" class="btn btn-primary mr-3" value="검색" />
	<input type="button" class="btn btn-secondary" value="상품등록" 
		onclick="location.href='${pageContext.request.contextPath }/prod/prodInsert.do';"
	/>
</form>
	<table id="prodTable" class="table table-bordered">
		<thead>
			<tr>
				<th>상품명</th>
				<th>상품분류</th>
				<th>거래처</th>
				<th>구매가</th>
				<th>판매가</th>
				<th>세일가</th>
			</tr>
		</thead>
		<tbody>
		<c:set var="dataList" value="${pagingVO.data }" />
		<c:choose>
			<c:when test="${not empty dataList }">
				<c:forEach items="${dataList }" var="prod">
					<tr>
						<c:url value="/prod/prodView.do" var="prodViewURL">
							<c:param name="what" value="${prod.prod_id }" />
						</c:url>
						<td><a href="${prodViewURL }">${prod.prod_name }</a></td>
						<td>${prod.lprod_nm }</td>
						<td>${prod.buyer.buyer_name}</td>
						<td>${prod.prod_cost }</td>
						<td>${prod.prod_price }</td>
						<td>${prod.prod_sale }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="6">검색 조건에 맞는 상품 없음.</td>
				</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="6" id="pagingArea">
					${pagingVO.pagingHTML_BS }
				</td>
			</tr>
		</tfoot>
	</table>

<script type="text/javascript">
	let listTable = $("#prodTable");
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
					$(list).each(function(idx, prod){
						trTags.push(
							$("<tr>").append(
								$("<td>").html(
										$("<a>").text(prod.prod_name)
												.attr("href", "${pageContext.request.contextPath }/prod/prodView.do?what="+prod.prod_id)
								),
								$("<td>").text(prod.lprod_nm),
								$("<td>").text(prod.buyer.buyer_name),
								$("<td>").text(prod.prod_cost),
								$("<td>").text(prod.prod_price),
								$("<td>").text(prod.prod_sale)
							)
						);
						
					});
				}else{
					trTags.push($("<tr>").html($("<td colspan='6'>").text("조건에 맞는 상품이 없음.")));
				}
				listTable.find("tbody").html(trTags);
				pagingArea.html( resp.pagingHTML_BS );
			},
			error : function(errResp) {
			}
		});
		return false;
	});
	let prod_buyerTag = $("[name='prod_buyer']");
	searchForm.find(":input").on("change", function(){
		let name = this.name;
		let lgu = $(this).val();
		if(name=='prod_lgu'){
			prod_buyerTag.find("option").hide();
			prod_buyerTag.find("option:first").show();
			if(lgu){
				prod_buyerTag.find("option."+lgu).show();
			}else{
				prod_buyerTag.find("option").show();
			}
		}
		$(searchForm).submit();
	});
</script>	
</body>
</html>
