package kr.or.ddit.commons.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.commons.dao.IZipCodeSearchDAO;
import kr.or.ddit.commons.dao.ZipCodeSearchDAOImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.ZipCodeVO;

@CommandHandler
public class ZipTBController{
	
	IZipCodeSearchDAO dao = ZipCodeSearchDAOImpl.getInstance();
	
	@URIMapping("/searchZip.do")
	public String doGet(
			@RequestParameter(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParameter(name="searchWord", required=false) String searchWord,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PagingVO<ZipCodeVO> pagingVO = new PagingVO<>();
		pagingVO.setSearchVO(new SearchVO(null, searchWord));
		int totalRecord = dao.selectTotalCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		pagingVO.setCurrentPage(currentPage);
		List<ZipCodeVO> zipList = dao.selectZipcodeList(pagingVO);
		pagingVO.setData(zipList);
		
		resp.setContentType("application/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();	
		){
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, pagingVO);
		}
		return null;
	}
}


















