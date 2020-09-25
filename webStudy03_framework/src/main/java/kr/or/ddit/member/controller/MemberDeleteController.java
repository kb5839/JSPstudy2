package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberDeleteController{
	IMemberService service = MemberServiceImpl.getInstance();
	@URIMapping(value="/leaveApp.do", method=HttpMethod.POST)
	public String doPost(@RequestParameter(name="mem_pass")String mem_pass, HttpSession session) throws ServletException, IOException {
		MemberVO authMember =(MemberVO) session.getAttribute("authMember");
		String mem_id = authMember.getMem_id();
		MemberVO leaveMember = MemberVO.builder()
										.mem_id(mem_id)
										.mem_pass(mem_pass)
										.build();
		ServiceResult result = service.removeMember(leaveMember);
		String goPage = null;
		String message = null;
		switch (result) {
			case INVALIDPASSWORD:
				goPage = "redirect:/mypage.do";
				message = "비밀번호 오류.";
				session.setAttribute("message", message);
				break;
			case FAILED:
				goPage = "redirect:/mypage.do";
				message = "서버 문제로 수정이 완료되지 않았습니다 잠시 후 다시 시도해주세요.";
				session.setAttribute("message", message);
				break;
	
			default:
				session.invalidate();
				goPage = "redirect:/";
				break;
		}
		
		return goPage;
	}
}









