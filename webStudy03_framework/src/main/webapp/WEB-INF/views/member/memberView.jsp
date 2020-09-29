<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
	<!-- table 태그를 이용하여, 현재 로그인된 유저의 모든 정보를 출력. -->
<table class="table table-bordered">
	<tr>
		<th>아이디</th>
		<td>${member.mem_id }</td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td>${member.mem_pass }</td>
	</tr>
	<tr>
		<th>회원명</th>
		<td>${member.mem_name }</td>
	</tr>
	<tr>
		<th>이미지</th>
		<td>
			<c:if test="${not empty authMember.mem_img }">
				<img src="data:image/*;base64,${authMember.mem_imgBase64 }" />
			</c:if>
		</td>
	</tr>
	<tr>
		<th>주민번호1</th>
		<td>${member.mem_regno1 }</td>
	</tr>
	<tr>
		<th>주민번호2</th>
		<td>${member.mem_regno2 }</td>
	</tr>
	<tr>
		<th>생일</th>
		<td>${member.mem_bir }</td>
	</tr>
	<tr>
		<th>우편번호</th>
		<td>${member.mem_zip }</td>
	</tr>
	<tr>
		<th>주소1</th>
		<td>${member.mem_add1 }</td>
	</tr>
	<tr>
		<th>주소2</th>
		<td>${member.mem_add2 }</td>
	</tr>
	<tr>
		<th>집전번</th>
		<td>${member.mem_hometel }</td>
	</tr>
	<tr>
		<th>회사전번</th>
		<td>${member.mem_comtel }</td>
	</tr>
	<tr>
		<th>휴대폰</th>
		<td>${member.mem_hp }</td>
	</tr>
	<tr>
		<th>이메일</th>
		<td>${member.mem_mail }</td>
	</tr>
	<tr>
		<th>직업</th>
		<td>${member.mem_job }</td>
	</tr>
	<tr>
		<th>취미</th>
		<td>${member.mem_like }</td>
	</tr>
	<tr>
		<th>기념일</th>
		<td>${member.mem_memorial }</td>
	</tr>
	<tr>
		<th>기념일자</th>
		<td>${member.mem_memorialday }</td>
	</tr>
	<tr>
		<th>마일리지</th>
		<td>${member.mem_mileage }</td>
	</tr>
	<tr>
		<th>탈퇴여부</th>
		<td>${member.mem_delete }</td>
	</tr>
	<tr>
		<th>구매목록</th>
		<td>
			<table class="table table-bordered">
				<thead class="thead-dark">
					<tr>
						<th>상품코드</th>
						<th>상품명</th>
						<th>구매가</th>
						<th>판매가</th>
						<th>마일리지</th>
					</tr>
				</thead>
				<tbody>
					<c:set value="${member.prodList }" var="prodList" />
					<c:if test="${not empty prodList }">
						<c:forEach items="${prodList }" var="prod">
							<c:url value="/prod/prodView.do" var="prodViewURL">
								<c:param name="what" value="${prod.prod_id }" />
							</c:url>
							<tr>
								<td>${prod.prod_id }</td>
								<td><a href="${prodViewURL }">${prod.prod_name }</a></td>
								<td>${prod.prod_cost }</td>
								<td>${prod.prod_price }</td>
								<td>${prod.prod_mileage }</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty prodList }">
						<tr>
							<td colspan="5">
								구매 목록이 없음.
							</td>
						</tr>
					</c:if>
				</tbody>			
			</table>
		</td>
	</tr>
</table>
	
	
	
	
	
	
	
	
	
	
	
	
