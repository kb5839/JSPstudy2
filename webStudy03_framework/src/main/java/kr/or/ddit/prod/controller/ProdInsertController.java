package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.IOthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.ProdVO;

@WebServlet("/prod/prodInsert.do")
public class ProdInsertController extends HttpServlet{
	IProdService service = new ProdServiceImpl();
	IOthersDAO othersDAO = new OthersDAOImpl();
	
	public void addAttribute(HttpServletRequest req){
		req.setAttribute("lprodList", othersDAO.selectLprodGuList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req);
		req.getRequestDispatcher("/WEB-INF/views/prod/prodForm.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청 파라미터 획득
		req.setCharacterEncoding("UTF-8");
		addAttribute(req);
		ProdVO prod = new ProdVO();
		req.setAttribute("prod", prod);
//		member.setMem_id(req.getParameter("mem_id"));
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(prod, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		2. 검증(DB 스키마 구조 참고)
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		CommonValidator<ProdVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(prod, errors, InsertGroup.class);
		
		String goPage = null;
		boolean redirect = false;
		String message = null;
		if(valid) {
//		3. 통과
//		4. 통과한 경우, 로직을 이용한 등록
			ServiceResult result = service.createProd(prod);
			switch (result) {
			case FAILED:
				goPage = "/WEB-INF/views/prod/prodForm.jsp";
				message = "서버 문제로 등록이 완료되지 않았습니다 잠시 후 다시 시도해주세요.";
				break;

			default:
//				PostRedirectGet pattern
				goPage = "/prod/prodView.do?what="+prod.getProd_id();
				redirect = true;
				break;
			}
			
		}else {
//		   불통
			goPage = "/WEB-INF/views/prod/prodForm.jsp";
			
		}
//		  등록 이후의 경우의 수에 대한 처리
		
		req.setAttribute("message", message);
		if(redirect) {
			resp.sendRedirect(req.getContextPath() + goPage);
		}else {
			req.getRequestDispatcher(goPage).forward(req, resp);
		}
	}
}



