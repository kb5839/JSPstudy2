package kr.or.ddit.commons.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MypageControllerServlet{
	private IMemberService service = MemberServiceImpl.getInstance();
	@URIMapping("/mypage.do")
	public String doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		String goPage = null;
		MemberVO member = service.retrieveMember(authMember.getMem_id());
		req.setAttribute("authMember", member);
		goPage = "member/mypage";
		
		return goPage;
	}
}

















