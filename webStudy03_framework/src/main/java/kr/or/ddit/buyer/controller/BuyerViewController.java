package kr.or.ddit.buyer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.BuyerVO;

@CommandHandler
public class BuyerViewController{
	IBuyerService service = new BuyerServiceImpl();
	
	@URIMapping("/buyer/buyerView.do")
	public String doGet(@RequestParameter(name="what")String what, HttpServletRequest req) throws ServletException, IOException {
		BuyerVO buyer = service.retrieveBuyer(what);
		req.setAttribute("buyer", buyer);
		return  "buyer/buyerView";
	}
}






