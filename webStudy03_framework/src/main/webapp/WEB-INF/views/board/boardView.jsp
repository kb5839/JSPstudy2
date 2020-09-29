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
		<tr>
			<td>제목 : ${board.bo_title }(${board.bo_no })</td>
			<td>작성자 : ${board.bo_writer }</td>
			<td>IP : ${board.bo_ip }</td>
		</tr>
		<tr>
			<td>
				<c:if test="${not empty board.attatchList }">
					<c:forEach items="${board.attatchList }" var="attatch" varStatus="vs">
						${attatch.att_filename } ${vs.last?'':','} 
					</c:forEach>
				</c:if>
				<c:if test="${empty board.attatchList }">
					첨부파일 없음.
				</c:if>
			</td>
			<td>작성일 : ${board.bo_date }</td>
			<td>조회수 : ${board.bo_hit }</td>
		</tr>
		<tr class="content">
			<td colspan="3">${board.bo_content }</td>
		</tr>
	</table>
	<form method="post" class="form-inline" id="replyInsertForm"
		action="${pageContext.request.contextPath }/reply/replyInsert.do">
		<input type="hidden" name="rep_no" />
		<input type="hidden" name="bo_no" value="${board.bo_no }"/>
		<table class="col-md-10">
			<tr>
				<td>
					<input type="text" class="form-control mb-2" name="rep_writer" placeholder="작성자" maxlength="15"/>
				</td>
				<td>
					<input type="password" class="form-control mb-2" name="rep_pass" placeholder="비밀번호"/>
				</td>
				<td>
					<input type="text" class="form-control mb-2" readonly name="rep_ip" value="${pageContext.request.remoteAddr }" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="input-group">
					<textarea class="form-control mb-2 mr-2" rows="2" placeholder="내용 200자 이내"maxlength="200" name="rep_content"></textarea>
					</div>
				</td>
				<td colspan="3">
					<input type="submit" value="전송" class="btn btn-primary" />
					<input type="reset" value="취소" class="btn btn-warning" />
				</td>
			</tr>
		</table>
	</form>
	<h4>댓글리스트 (비동기)</h4>
	<table id="replyTable" class="table table-bordered">
	</table>
	<div id="pagingArea"></div>
	<form id="searchForm" action="${pageContext.request.contextPath }/reply/replyList.do">
		<input type="hidden" name="what" value="${board.bo_no }" />
		<input type="hidden" name="page"  />
	</form>
	<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  	 <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">댓글 수정</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <form action="${pageContext.request.contextPath }/reply/replyUpdate.do" method="post">
	      	<input type="hidden" name="rep_no" required/>
	      	<input type="hidden" name="bo_no"  required/>
	      <div class="modal-body">
	      	<table class="table form-inline">
	      		<tr>
	      			<td>
	      				<input type="text" required name="rep_writer" class="form-control" placeholder="작성자" />
	      			</td>
	      			<td>
	      				<input type="password" required name="rep_pass" class="form-control" placeholder="비밀번호"/>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td colspan="2">
						<div class="input-group">
						<textarea class="form-control mb-2 mr-2" rows="2" placeholder="내용 200자 이내"maxlength="200" name="rep_content"></textarea>
						</div>
					</td>
	      		</tr>
	      	</table>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-primary">수정</button>
	        <button type="reset" class="btn btn-warning" data-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	<form id="replyDeleteForm" action="${pageContext.request.contextPath }/reply/replyDelete.do" method="post">
		<input type="hidden" name="rep_no" required/>
      	<input type="hidden" name="bo_no"  required/>
      	<input type="hidden" name="rep_pass"  required/>
	</form>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/boardView.js"></script>
</body>
</html>
