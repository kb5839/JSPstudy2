package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.filter.wrapper.PartWrapper;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelData;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberUpdateController{
	private IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping("/myDataUpdate.do")
	public String updateForm(HttpSession session, HttpServletRequest req) throws ServletException, IOException {
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		MemberVO member = service.retrieveMember(authMember.getMem_id());
		req.setAttribute("command", "update");
		req.setAttribute("member", member);
		String goPage = "member/registForm";
		return goPage;
	}
	
	
	@URIMapping(value="/myDataUpdate.do", method=HttpMethod.POST)
	public String doPost(@ModelData(name="member")MemberVO member,  HttpServletRequest req) throws ServletException, IOException {
		if(req instanceof FileUploadRequestWrapper) {
			PartWrapper mem_image = ((FileUploadRequestWrapper) req).getPartWrapper("mem_image");
			member.setMem_image(mem_image);
		}
//		2. 검증(DB 스키마 구조 참고)
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		CommonValidator<MemberVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(member, errors, UpdateGroup.class);
		
		String goPage = null;
		String message = null;
		if(valid) {
//		3. 통과
//		4. 통과한 경우, 로직을 이용한 수정
			ServiceResult result = service.modifyMember(member);
			switch (result) {
			case INVALIDPASSWORD:
				goPage = "member/registForm";
				message = "비밀번호 오류.";
				break;
			case FAILED:
				goPage = "member/registForm";
				message = "서버 문제로 수정이 완료되지 않았습니다 잠시 후 다시 시도해주세요.";
				break;

			default:
				goPage = "redirect:/mypage.do";
				break;
			}
			
		}else {
//		   불통
			goPage = "member/registForm";
			
		}
//		  등록 이후의 경우의 수에 대한 처리
		req.setAttribute("message", message);
		return goPage;
	}
}












