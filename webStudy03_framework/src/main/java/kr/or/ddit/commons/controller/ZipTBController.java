package kr.or.ddit.commons.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.commons.dao.IZipCodeSearchDAO;
import kr.or.ddit.commons.dao.ZipCodeSearchDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.ZipCodeVO;

@WebServlet("/searchZip.do")
public class ZipTBController extends HttpServlet{
	
	IZipCodeSearchDAO dao = ZipCodeSearchDAOImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String pageParam = req.getParameter("page");
		String searchWord = req.getParameter("searchWord");
		PagingVO<ZipCodeVO> pagingVO = new PagingVO<>();
		pagingVO.setSearchVO(new SearchVO(null, searchWord));
		int totalRecord = dao.selectTotalCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		int currentPage = 1;
		if(StringUtils.isNotBlank(pageParam) && StringUtils.isNumeric(pageParam)) {
			currentPage = Integer.parseInt(pageParam);
		}
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
	}
}


















