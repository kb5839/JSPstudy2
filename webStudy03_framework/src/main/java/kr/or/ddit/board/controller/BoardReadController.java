package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

@CommandHandler
public class BoardReadController {
	IBoardService service = new BoardServiceImpl();
	
	@URIMapping("/board/boardView.do")
	public String view(@RequestParameter(name="what", required=true) int bo_no, HttpServletRequest req){
		BoardVO board = service.readBoard(bo_no);
		req.setAttribute("board", board);
		String goPage = "board/boardView";
		return goPage;
	}
	
	@URIMapping("/board/boardList.do")
	public String list(
			@RequestParameter(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParameter(name="searchType", required=false) String searchType,
			@RequestParameter(name="searchWord", required=false) String searchWord,
			HttpServletRequest req, HttpServletResponse resp) throws IOException {
		SearchVO searchVO = new SearchVO(searchType, searchWord);
		PagingVO<BoardVO> pagingVO = new PagingVO<>();
		pagingVO.setSearchVO(searchVO);
		int totalRecord = service.readBoardCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord); // totalPage
		pagingVO.setCurrentPage(currentPage); // startRow, endRow, startPage, endPage
		
		List<BoardVO> boardList = service.readBoardList(pagingVO);
		pagingVO.setData(boardList);
		
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType("application/json;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			try(
				PrintWriter out = resp.getWriter();	
			){
				mapper.writeValue(out, pagingVO);
			}
			return null;
		}else {
			req.setAttribute("pagingVO", pagingVO);
			String goPage = "board/boardList";
			return goPage;
		}
	}
}
