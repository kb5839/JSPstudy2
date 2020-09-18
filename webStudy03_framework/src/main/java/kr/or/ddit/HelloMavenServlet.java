package kr.or.ddit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mavenTest.do")
public class HelloMavenServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String model = "Hello Maven";
		req.setAttribute("contents", model);
		req.getRequestDispatcher("/WEB-INF/views/mavenTestView.jsp").forward(req, resp);
	}
}















