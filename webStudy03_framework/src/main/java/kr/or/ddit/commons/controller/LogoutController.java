package kr.or.ddit.commons.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;

@CommandHandler
public class LogoutController{
	@URIMapping(value="/login/logout.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session==null || session.isNew()) {
			resp.sendError(400);
			return null;
		}else {
//			session.removeAttribute("authId");
			session.invalidate();
			return "redirect:/";
		}
	}
}















