<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script type="text/javascript">
	<c:if test="${not empty message }">
		alert("${message }");
	</c:if>
	$(function(){
		let prod_lguTag = $("[name='prod_lgu']").on("click", function(){
			let lgu = $(this).val();
			prod_buyerTag.find("option").hide();
			prod_buyerTag.find("option:first").show();
			if(lgu){
				prod_buyerTag.find("option."+lgu).show();
			}else{
				prod_buyerTag.find("option").show();
			}
		}).val("${prod.prod_lgu}");
		let prod_buyerTag = $("[name='prod_buyer']").val("${prod.prod_buyer}");
	});
</script>
</head>
<body>
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request"/>
	<form method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th>상품코드</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" readonly name="prod_id" 
							value="${prod.prod_id }" maxLength="10" />
						<span class="error">${errors["prod_id"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>상품명</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" required name="prod_name"
							value="${prod.prod_name }" maxLength="40" />
						<span class="error">${errors["prod_name"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>상품분류</th>
				<td>
					<div class="form-group form-inline">
						<select class="form-control mr-3" name="prod_lgu">
							<option value>상품분류</option>
							<c:forEach items="${lprodList }" var="lprod">
								<option value="${lprod.lprod_gu }">${lprod["lprod_nm"] }</option>
							</c:forEach>
						</select>
						<span class="error">${errors["prod_lgu"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>거래처코드</th>
				<td>
					<div class="form-group form-inline">
						<select class="form-control mr-3" name="prod_buyer">
							<option value>거래처</option>
							<c:forEach items="${buyerList }" var="buyer">
								<option value="${buyer.buyer_id }" class="${buyer.buyer_lgu }">${buyer["buyer_name"] }</option>
							</c:forEach>
						</select>
						<span class="error">${errors["prod_buyer"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>구매가</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" required
							name="prod_cost" value="${prod.prod_cost }" maxLength="22" />
						<span class="error">${errors["prod_cost"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>판매가</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" required
							name="prod_price" value="${prod.prod_price }" maxLength="22" />
						<span class="error">${errors["prod_price"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>세일가</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" required
							name="prod_sale" value="${prod.prod_sale }" maxLength="22" />
						<span class="error">${errors["prod_sale"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>상품개요</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" required
							name="prod_outline" value="${prod.prod_outline }" maxLength="100" />
						<span class="error">${errors["prod_outline"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>상세정보</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" name="prod_detail"
							value="${prod.prod_detail }" maxLength="4000" />
						<span class="error">${errors["prod_detail"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>상품이미지</th>
				<td>
					<div style="width: 50px;height: 50px;">
						<img style="width: 100%;height: 100%;" src="<%=request.getContextPath() %>/prodImages/${prod.prod_img }" />
					</div>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="file" name="prod_image" />
						<span class="error">${errors["prod_img"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>총재고</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" required
							name="prod_totalstock" value="${prod.prod_totalstock }"
							maxLength="22" />
						<span class="error">${errors["prod_totalstock"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>입고일</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="date" name="prod_insdate" readonly
							value="${prod.prod_insdate }" maxLength="7" />
						<span class="error">${errors["prod_insdate"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>적정재고</th>
				<td>	
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" required
							name="prod_properstock" value="${prod.prod_properstock }"
							maxLength="22" />
						<span class="error">${errors["prod_properstock"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>크기</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" name="prod_size"
							value="${prod.prod_size }" maxLength="20" />
						<span class="error">${errors["prod_size"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>색상</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" name="prod_color"
							value="${prod.prod_color }" maxLength="20" />
						<span class="error">${errors["prod_color"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>배송방법</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" name="prod_delivery"
							value="${prod.prod_delivery }" maxLength="255" />
						<span class="error">${errors["prod_delivery"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>단위</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="text" name="prod_unit"
							value="${prod.prod_unit }" maxLength="6" />
						<span class="error">${errors["prod_unit"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>입고량</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" name="prod_qtyin"
							value="${prod.prod_qtyin }" maxLength="22" />
						<span class="error">${errors["prod_qtyin"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>판매량</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" name="prod_qtysale"
							value="${prod.prod_qtysale }" maxLength="22" />
						<span class="error">${errors["prod_qtysale"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td>
					<div class="form-group form-inline">
						<input class="form-control mr-2" type="number" name="prod_mileage"
							value="${prod.prod_mileage }" maxLength="22" />
						<span class="error">${errors["prod_mileage"] }</span>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="submit" class="btn btn-primary">전송</button>
					<button type="reset" class="btn btn-warning">취소</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>









