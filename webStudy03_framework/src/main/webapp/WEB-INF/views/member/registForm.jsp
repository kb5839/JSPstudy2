<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/additional-methods.min.js"></script>
<link rel="stylesheet" href="//cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<script type="text/javascript" src="//cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<style type="text/css">
	.error{
		color: red;
	}
</style>
<script type="text/javascript">
	$(function() {
		var registForm = $("#registForm");
		registForm.find(":input[type!='button']").addClass("form-control");
		<c:if test="${'update' ne command}">
			registForm.validate({
				submitHandler:function(form){
					let valid = $(form).data("validId");
					if(!valid){
						alert("아이디 중복 체크 하세요!");
						valid=false;
					}else{
						form.submit();
					}
				}
			});
			var inInputTag = $("[name='mem_id']");
			inInputTag.on("change", function(){
				registForm.data("validId", false);
				$(this).next('.idMsg').remove();
			});
			
			$("#checkBtn").on("click", function(){
				let inputId = inInputTag.val();
				$.ajax({
					url : "${pageContext.request.contextPath }/idCheck.do",
					// inputId 파라미터로 중복 확인
					data : {
						inputId:inputId
					},
					method : "post",
					dataType : "json", // Accept(request), Content-Type(response)
					success : function(resp) {
						if(resp.valid){
							let msgTag = inInputTag.next(".idMsg");
							if(msgTag.length==0){
								inInputTag.after("<span class='idMsg'>아이디 사용가능</span>");
							}
							registForm.data("validId", true);
						}else{
							alert("아이디 중복, 바꾸셈.");
							$("[name='mem_id']").focus();
						}
					},
					error : function(errResp) {
						console.log(errResp);
					}
				});
			});			
		</c:if>
		<c:if test="${'update' eq command}">
			registForm.find("input[name]").prop("readonly", true);
			registForm.find("input.editable").prop("readonly", false);
		</c:if>
		<c:if test="${not empty message}">
			alert("${message}");
		</c:if>
	});
</script>
</head>
<body>
	<form id="registForm" method="post" class="form-inline" enctype="multipart/form-data">
		<table class="table table-bordered">
			<tr>
				<th>아이디</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_id" value="${member.mem_id }" 
							maxLength="15" data-msg="아이디 필수"/>
					<button type="button" id="checkBtn" class="ml-3 btn btn-info">아이디중복체크</button>	
					<span class="error">${errors["mem_id"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_pass" value="${member.mem_pass }" 
						maxLength="15" data-msg="비밀번호 필수"  class="editable"/>
					<span class='error'>${errors["mem_pass"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>회원명</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_name" value="${member.mem_name }" 
							maxLength="20" data-msg="이름 필수"/>
					<span class='error'>${errors["mem_name"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>이미지</th>
				<td>
				<div class="form-group">
					<input type="file" name="mem_image" />
					<span class='error'>${errors["mem_image"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>주민번호1</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_regno1" value="${member.mem_regno1 }" 
						maxLength="6" pattern="[0-9]{6}" data-msg-required="주민번호 필수" data-msg-pattern="형식확인"/>
					<span class='error'>${errors["mem_regno1"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>주민번호2</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_regno2" value="${member.mem_regno2 }" 
							maxLength="7"  pattern="[0-9]{7}" data-msg-required="주민번호 필수" data-msg-pattern="형식확인"/>
					<span class='error'>${errors["mem_regno2"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>생일</th>
				<td>
				<div class="form-group">
					<input type="date" name="mem_bir" value="${member.mem_bir }" pattern="\d{4}-\d{2}-\d{2}"/>
					<span class='error'>${errors["mem_bir"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_zip" value="${member.mem_zip }" 
							maxLength="7" pattern="[0-9]{3}-[0-9]{3}" readonly
							data-msg-required="우편번호 필수" data-msg-pattern="형식확인"/>
					<span class='error'>${errors["mem_zip"] }</span>
					<input type="button" id="zipSearchBtn" class="btn btn-info ml-3" value="우편번호 검색" />
				</div>
				</td>
			</tr>
			<tr>
				<th>주소1</th>
				<td>
				<div class="form-group">
					<input type="text" class="col" required name="mem_add1" value="${member.mem_add1 }" 
							maxLength="100" readonly data-msg="주소 필수" />
					<span class='error'>${errors["mem_add1"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>주소2</th>
				<td>
				<div class="form-group">
					<input type="text" class="col" required name="mem_add2" value="${member.mem_add2 }" 
							maxLength="80" readonly  data-msg="주소 필수" />
					<span class='error'>${errors["mem_add2"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>집전번</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_hometel" value="${member.mem_hometel }" 
							maxLength="14" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
							placeholder="000-000-0000"   class="editable"
							data-msg-required="집전번 필수" data-msg-pattern="전화번호 형식 확인"/>
					<span class='error'>${errors["mem_hometel"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>회사전번</th>
				<td>
				<div class="form-group">
					<input type="text" required name="mem_comtel" value="${member.mem_comtel }" 
							maxLength="14"  pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" 
							placeholder="000-000-0000"   class="editable"
							data-msg-required="회사전번 필수" data-msg-pattern="전화번호 형식 확인"/>
					<span class='error'>${errors["mem_comtel"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>
				<div class="form-group">
					<input type="text" name="mem_hp" value="${member.mem_hp }" 
							maxLength="15" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
							placeholder="000-000-0000"   class="editable"
							data-msg-pattern="전화번호 형식 확인"/>
					<span class='error'>${errors["mem_hp"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
				<div class="form-group">
					<input type="email" required name="mem_mail" value="${member.mem_mail }" 
						maxLength="40" data-msg="이메일 필수"  class="editable"/>
					<span class='error'>${errors["mem_mail"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>직업</th>
				<td>
				<div class="form-group">
					<input type="text" name="mem_job" value="${member.mem_job }"
					 maxLength="40"  class="editable"/>
					<span class='error'>${errors["mem_job"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>취미</th>
				<td>
				<div class="form-group">
					<input type="text" name="mem_like" value="${member.mem_like }" 
						maxLength="40"  class="editable"/>
					<span class='error'>${errors["mem_like"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>기념일</th>
				<td>
				<div class="form-group">
					<input type="text" name="mem_memorial" value="${member.mem_memorial }" 
						maxLength="40"  class="editable"/>
					<span class='error'>${errors["mem_memorial"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>기념일자</th>
				<td>
				<div class="form-group">
					<input type="date" name="mem_memorialday" value="${member.mem_memorialday }" 
							pattern="\d{4}-\d{2}-\d{2}"  class="editable"/>
					<span class='error'>${errors["mem_memorialday"] }</span>
				</div>
				</td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td>기본마일리지</td>
			</tr>
			<tr>
				<th>탈퇴여부</th>
				<td>신규가입</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" class="btn btn-primary" value="전송" /> 
					<input type="reset" class="btn btn-warning" value="취소" />
				</td>
			</tr>
		</table>
	</form>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/searchZip.js?${System.currentTimeMillis() }"></script>
<script type="text/javascript">
	$("#registForm").searchZip({
		searchBtn:"#zipSearchBtn",
		url:"${pageContext.request.contextPath }/searchZip_DT.do",
		zipCodeTag : "[name='mem_zip']",
		address1Tag : "[name='mem_add1']",
		address2Tag : "[name='mem_add2']"
	});
</script>
</body>
</html>	









