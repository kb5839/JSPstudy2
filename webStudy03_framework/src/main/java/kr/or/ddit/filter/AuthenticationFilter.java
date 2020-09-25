package kr.or.ddit.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 보호 자원에 대한 인증 여부를 판단.
 *
 */
public class AuthenticationFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	private Map<String, String[]> secured;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 객체 생성 및 초기화 완료.", getClass().getName());
		secured = new LinkedHashMap<>();
		filterConfig.getServletContext().setAttribute("secured", secured);
		Properties properties = new Properties();
		InputStream inStream = AuthenticationFilter.class.getResourceAsStream("/kr/or/ddit/securedResources.properties");
		try {
			properties.load(inStream);
			Enumeration<Object> keys = properties.keys();
			while (keys.hasMoreElements()) {
				String uri = (String) keys.nextElement();
				String value = properties.getProperty(uri);
				String[] roles = value.split(",");
				Arrays.sort(roles);
				secured.put(uri.trim(), roles);
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		int length = req.getContextPath().length();
		uri = uri.substring(length).split(";")[0];
		
		boolean pass = true;
		if(secured.containsKey(uri)) {
			// 보호 자원 요청
			HttpSession session = req.getSession();
			Object authMember = session.getAttribute("authMember");
			if(authMember==null) {
				pass = false;
			}
		}
		if(pass) {
			chain.doFilter(request, response);
		}else {
			// 로그인하지 않은 상태에서 보호 자원 요청.
			resp.sendRedirect(req.getContextPath() + "/login/loginForm.jsp");
		}
	}

	@Override
	public void destroy() {
		logger.info("{} 객체 소멸.", getClass().getName());
	}

}





