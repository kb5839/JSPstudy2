package kr.or.ddit.commons.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ZipTB_DTController{
	
	IZipCodeSearchDAO dao = ZipCodeSearchDAOImpl.getInstance();
	
	@URIMapping("/searchZip_DT.do")
	public String doGet(
			@RequestParameter(name="search[value]", required=false) String searchWord,
			@RequestParameter(name="draw", required=false) String draw,
			@RequestParameter(name="start", required=false, defaultValue="0") int start,
			@RequestParameter(name="length", required=false, defaultValue="10") int length,
			HttpServletResponse resp) throws ServletException, IOException {
//		String searchWord = req.getParameter("search[value]"); // 검색 키워드
//		String draw = req.getParameter("draw");
//		String startParam = req.getParameter("start"); // startRow-1
//		String lengthParam = req.getParameter("length"); // screenSize
		
		PagingVO<ZipCodeVO> pagingVO = new PagingVO<>();
		int currentPage = 1;
//		if(StringUtils.isNumeric(startParam) && StringUtils.isNumeric(lengthParam)) {
//			int start = Integer.parseInt(startParam);
//			int length = Integer.parseInt(lengthParam);
		pagingVO.setScreenSize(length);
		currentPage = (start+length)/length;
//		}
		
		// 검색 전 전체 레코드 수
		int totalRecordNonSearch = dao.selectTotalCount(pagingVO);
		// 검색 후 전체 레코드 수
		pagingVO.setSearchVO(new SearchVO(null, searchWord));
		int totalRecord = dao.selectTotalCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		pagingVO.setCurrentPage(currentPage);
		
		List<ZipCodeVO> zipList = dao.selectZipcodeList(pagingVO);
		
		// server-side processing 요구 데이터 스펙
		Map<String, Object> target = new HashMap<>();
		target.put("draw", draw);
		target.put("recordsTotal", totalRecordNonSearch);
		target.put("recordsFiltered", totalRecord);
		target.put("data", zipList);
		
		resp.setContentType("application/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();	
		){
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, target);
		}
		return null;
	}
}


















