package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

@CommandHandler
public class BuyerReadController {
    IBuyerService service = new BuyerServiceImpl();   
	
    @URIMapping("/buyer/buyerList.do")
	public String list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pageParam = req.getParameter("page");
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		SearchVO searchVO = new SearchVO(searchType, searchWord);
		
		int currentPage = 1;
		if(StringUtils.isNumeric(pageParam)) {
			currentPage = Integer.parseInt(pageParam);
		}
		
		PagingVO<BuyerVO> pagingVO = new PagingVO<>(5, 3);
		pagingVO.setSearchVO(searchVO);
		
		pagingVO.setTotalRecord(service.retrieveBuyerCount(pagingVO));
		
		pagingVO.setCurrentPage(currentPage);
		List<BuyerVO> buyerList = service.retrieveBuyerList(pagingVO);
		pagingVO.setData(buyerList);
		
		req.setAttribute("pagingVO", pagingVO);
		return "buyer/buyerList";
	}
    
    @URIMapping("/buyer/buyerView.do")
	public String view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String buyer_id = req.getParameter("what");
		if(StringUtils.isBlank(buyer_id)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		BuyerVO buyer = service.retrieveBuyer(buyer_id);
		req.setAttribute("buyer", buyer);
		return "buyer/buyerView";
	}
}
