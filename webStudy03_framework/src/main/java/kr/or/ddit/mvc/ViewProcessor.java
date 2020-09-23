package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewProcessor implements IViewProcessor {
	private String prefix = "";
	private String suffix = "";

	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/* 
	 * viewName 이 "redirect:" 으로 시작되는 경우, redirection
	 */
	@Override
	public void viewProcess(String viewName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean redirect = viewName.startsWith("redirect:");
		if(redirect) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			viewName = prefix + viewName + suffix;
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}
}














