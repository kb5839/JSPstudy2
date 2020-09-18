package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.util.BeanUtil;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/registMember.do")
public class MemberRegistController extends HttpServlet {
	private IMemberService service = MemberServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/member/registForm.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청 파라미터 획득
		req.setCharacterEncoding("UTF-8");
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
//		member.setMem_id(req.getParameter("mem_id"));
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(member, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(member);

//		2. 검증(DB 스키마 구조 참고)
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		CommonValidator<MemberVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(member, errors, InsertGroup.class);
		
		String goPage = null;
		boolean redirect = false;
		String message = null;
		if(valid) {
//		3. 통과
//		4. 통과한 경우, 로직을 이용한 등록
			ServiceResult result = service.registMember(member);
			switch (result) {
			case PKDUPLICATED:
				goPage = "/WEB-INF/views/member/registForm.jsp";
				message = "아이디 중복, 확인 후 다시 넣으세요.";
				break;
			case FAILED:
				goPage = "/WEB-INF/views/member/registForm.jsp";
				message = "서버 문제로 등록이 완료되지 않았습니다 잠시 후 다시 시도해주세요.";
				break;

			default:
				goPage = "/login/loginForm.jsp";
				redirect = true;
				break;
			}
			
		}else {
//		   불통
			goPage = "/WEB-INF/views/member/registForm.jsp";
			
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
