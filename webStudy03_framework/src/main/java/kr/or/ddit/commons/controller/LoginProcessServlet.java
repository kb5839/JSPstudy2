package kr.or.ddit.commons.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.commons.service.AuthenticateServiceImpl;
import kr.or.ddit.commons.service.IAuthenticateService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.utils.CookieUtils;
import kr.or.ddit.utils.CookieUtils.TextType;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/login/loginProcess.do")
public class LoginProcessServlet extends HttpServlet{
	private IAuthenticateService service = new AuthenticateServiceImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청 파리미터 확보
		String mem_id = req.getParameter("mem_id"); 
		String mem_pass = req.getParameter("mem_pass");
		String saveId = req.getParameter("saveId");
		
//		2. 검증
//			- 통과 : 3번 처리
//			- 불통 : 	400 error 발생.
		
		if(StringUtils.isBlank(mem_id) || StringUtils.isBlank(mem_pass)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수 파리미터 누락");
			return;
		}
//		3. 인증 처리( 입력한 아이디와 비밀번호가 동일하면 성공)
//			- 성공 : welcome page 로 이동 (redirect)
//					index.jsp 생성 (authId  라는 속성으로 현재 로그인한 유저의 아이디를 출력. )
//			- 실패 ( 비번 오류로 간주 ): loginForm.jsp 로 이동 (동일한 아이디를 다시 입력하지 않도록.)
		String goPage = null;
		boolean redirect = false;
		HttpSession session = req.getSession();
		if(session==null||session.isNew()) {
			resp.sendError(400,  "현재 요청이 최초요청일수 없음.");
			return;
		}
		String message = null;
		Object result = service.authenticate(MemberVO.builder().mem_id(mem_id).mem_pass(mem_pass).build());
		if(result instanceof MemberVO) {
			goPage = "/";
			redirect = true;
			session.setAttribute("authMember", result);
			
			int maxAge = 0;
			if("save".equals(saveId)) {
				// 기억
				maxAge = 60*60*24*7;
			}
			
			Cookie idCookie = CookieUtils.createCookie("idCookie", mem_id, TextType.PATH, req.getContextPath(), maxAge);
			resp.addCookie(idCookie);
			
		}else {
			goPage = "/login/loginForm.jsp";
			redirect = true;
			if(ServiceResult.NOTEXIST==result) {
				message =  mem_id+"에 해당하는 회원이 없음.";
			}else {
				message = "비번 오류";
			}
			session.setAttribute("message", message);
		}
		
		if(redirect) {
			resp.sendRedirect(req.getContextPath() + goPage );
		}else {
			req.getRequestDispatcher(goPage).forward(req, resp);
		}
	}
}















