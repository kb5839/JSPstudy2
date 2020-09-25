package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;


@CommandHandler
public class IdCheckController{
	private IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping(value="/idCheck.do", method=HttpMethod.POST)
	public String doPost(@RequestParameter(name="inputId") String inputId, HttpServletResponse resp) throws ServletException, IOException {
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
		return null;
	}
}















