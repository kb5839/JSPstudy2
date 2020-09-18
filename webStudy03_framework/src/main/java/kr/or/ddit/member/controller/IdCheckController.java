package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;

@WebServlet("/idCheck.do")
public class IdCheckController extends HttpServlet{
	private IMemberService service = MemberServiceImpl.getInstance();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String inputId = req.getParameter("inputId");
		if(StringUtils.isBlank(inputId)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수파라미터 누락");
			return;
		}
		boolean validId = false;
		try {
			service.retrieveMember(inputId);
		}catch (CustomException e) {
			validId = true;
		} 
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("valid", validId);
		if(!validId) resultMap.put("useId", "추천아이디");
		ObjectMapper mapper = new ObjectMapper();
		try(
			PrintWriter out = resp.getWriter();
		){
			mapper.writeValue(out, resultMap);
		}
	}
}















