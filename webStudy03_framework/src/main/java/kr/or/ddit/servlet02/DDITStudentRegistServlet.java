package kr.or.ddit.servlet02;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.utils.TemplateUtils;

@WebServlet("/ddit/studentRegist.do")
public class DDITStudentRegistServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		1. getParameter, getParameterValues, getParameterMap
		String ddit_name = req.getParameter("ddit_name");
		String ddit_ageStr = req.getParameter("ddit_age");
		int ddit_age = Integer.parseInt(ddit_ageStr);
		String ddit_address = req.getParameter("ddit_address");
		String ddit_gen = req.getParameter("ddit_gen");
		String[] ddit_hobby = req.getParameterValues("ddit_hobby");
		String ddit_hp = req.getParameter("ddit_hp");
		String ddit_mail = req.getParameter("ddit_mail");
		String ddit_grade = req.getParameter("ddit_grade");
		String ddit_career = req.getParameter("ddit_career");
		String[] ddit_lic = req.getParameterValues("ddit_lic");
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		Enumeration<String> names = req.getParameterNames();
		String pattern = "<tr><th>%s</th><td>%s</td></tr>";
		StringBuffer content = new StringBuffer();
		for(Entry<String, String[]> entry :parameterMap.entrySet()) {
			content.append( String.format(pattern, entry.getKey(), Arrays.toString(entry.getValue())) );
		}
		
		Map<String, Object> attributeMap = new LinkedHashMap<>();
		attributeMap.put("content", content);
		
		String html = TemplateUtils.readAndReplace("/kr/or/ddit/servlet02/dditRegist.tmpl", attributeMap);
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(html);
		out.close();
	}
}


















