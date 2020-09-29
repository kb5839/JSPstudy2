package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelData;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

@CommandHandler
public class ReplyController {
	IReplyService service = new ReplyServiceImpl();
	
	@URIMapping(value="/reply/replyInsert.do", method=HttpMethod.POST)
	public String insert(@ModelData(name="reply") ReplyVO reply,
		HttpServletResponse resp) throws ServletException, IOException{
		ServiceResult result = service.createReply(reply);
		Map<String, Object> resultMap = Collections.singletonMap("result", result);
		resp.setContentType("application/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();	
		){
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, resultMap);
		}
		return null;
	}
	@URIMapping(value="/reply/replyUpdate.do", method=HttpMethod.POST)
	public String update(@ModelData(name="reply") ReplyVO reply,
			HttpServletResponse resp) throws ServletException, IOException {
		ServiceResult result = service.modifyReply(reply);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		if(ServiceResult.INVALIDPASSWORD.equals(result)) {
			resultMap.put("message", "비밀번호 오류");
		}
		resp.setContentType("application/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();	
		){
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, resultMap);
		}
		return null;
	}
	@URIMapping(value="/reply/replyDelete.do", method=HttpMethod.POST)
	public String delete(@ModelData(name="reply") ReplyVO reply,
			HttpServletResponse resp) throws ServletException, IOException {
		ServiceResult result = service.removeReply(reply);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		if(ServiceResult.INVALIDPASSWORD.equals(result)) {
			resultMap.put("message", "비밀번호 오류");
		}
		resp.setContentType("application/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();	
		){
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, resultMap);
		}
		return null;
	}
	
	@URIMapping("/reply/replyList.do")
	public String list(
			@RequestParameter(name="what", required=true) int bo_no, 
			@RequestParameter(name="page", required=false, defaultValue="1") int currentPage
			, HttpServletResponse resp) throws ServletException, IOException{
//		====검색, 특정글의 댓글만 조회
		ReplyVO searchDetail = new ReplyVO();
		searchDetail.setBo_no(bo_no);
		
		PagingVO<ReplyVO> pagingVO = new PagingVO<>(5, 5);
		pagingVO.setSearchDetail(searchDetail);
//		========
		int totalRecord = service.readReplyCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord); // totalPage
		pagingVO.setCurrentPage(currentPage); // startRow, endRow, startPage, endPage
		
		List<ReplyVO> ReplyList = service.readReplyList(pagingVO);
		pagingVO.setData(ReplyList);
		
		resp.setContentType("application/json;charset=UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		try(
			PrintWriter out = resp.getWriter();	
		){
			mapper.writeValue(out, pagingVO);
		}
		return null;
	}
}













