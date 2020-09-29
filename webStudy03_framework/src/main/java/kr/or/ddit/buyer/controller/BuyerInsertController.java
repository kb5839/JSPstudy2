package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.prod.dao.IOthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.vo.BuyerVO;

@CommandHandler
public class BuyerInsertController{
    IBuyerService service = new BuyerServiceImpl();   
    IOthersDAO othersDAO = new OthersDAOImpl();
	
    private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodGuList());
		req.setAttribute("currentAction", "/buyer/buyerInsert.do");
	}
    
    @URIMapping("/buyer/buyerInsert.do")
	public String form(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req);
		
		return "buyer/buyerForm";
	}
	
    @URIMapping(value="/buyer/buyerInsert.do", method=HttpMethod.POST)
	public String update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		addAttribute(req);
		
		BuyerVO buyer = new BuyerVO(); // command object
		req.setAttribute("buyer", buyer);
		try {
			BeanUtils.populate(buyer, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		Map<String, String> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(buyer, errors);
		String goPage = null;
		String message = null;
		if (valid) {
//		2. 로직 선택 service.createbuyer(buyer)
			ServiceResult result = service.createBuyer(buyer);
//		3. 로직의 실행 결과에 따른 분기 페이지 선택
			switch (result) {
			case OK:
				goPage = "redirect:/buyer/buyerView.do?what="+buyer.getBuyer_id();
				break;
			default: // FAIL
				message = "서버 오류, 잠시뒤 다시 .";
				goPage = "buyer/buyerForm";
				break;
			}
		} else {
			goPage = "buyer/buyerForm";
		}
		req.setAttribute("message", message);
//		4. 모델 데이터 공유
		return goPage;
	}

    private boolean validate(BuyerVO buyer, Map<String, String> errors) {
		boolean valid = true;
		
		return valid;
	}
}