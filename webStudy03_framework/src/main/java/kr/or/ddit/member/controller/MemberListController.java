package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.ZipCodeVO;

@WebServlet("/member/memberList.do")
public class MemberListController extends HttpServlet {
	
	IMemberService service = MemberServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String pageParam = req.getParameter("page");
//		====검색
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		SearchVO searchVO = new SearchVO(searchType, searchWord);
		PagingVO<MemberVO> pagingVO = new PagingVO<>(3, 2);
		pagingVO.setSearchVO(searchVO);
//		========
		int totalRecord = service.retrieveMemberCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord); // totalPage
		int currentPage = 1;
		if(StringUtils.isNotBlank(pageParam) && StringUtils.isNumeric(pageParam)) {
			currentPage = Integer.parseInt(pageParam);
		}
		pagingVO.setCurrentPage(currentPage); // startRow, endRow, startPage, endPage
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		pagingVO.setData(memberList);
		
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType("application/json;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			try(
					PrintWriter out = resp.getWriter();	
					){
				mapper.writeValue(out, pagingVO);
			}
		}else {
			req.setAttribute("pagingVO", pagingVO);
			String goPage = "/WEB-INF/views/member/memberList.jsp";
			req.getRequestDispatcher(goPage).forward(req, resp);
		}
	}
}








