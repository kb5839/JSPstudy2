package kr.or.ddit.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

/**
 * 보호 자원에 대한 접근 권한 소유여부 확인
 *
 */
//@WebFilter("/*")
public class AuthorizationFilter implements Filter{

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 객체 생성 및 초기화 완료.", getClass().getName());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// authenticationFilter 에 있는 secured 맵을 가져오려면????
		Map<String, String[]> secured = (Map<String, String[]>) request.getServletContext().getAttribute("secured");
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		int length = req.getContextPath().length();
		uri = uri.substring(length).split(";")[0];
		boolean pass = true;
		
		if(secured.containsKey(uri)) {
			MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
			String userRole = authMember.getMem_role();
			String[] roles = secured.get(uri);
			if(Arrays.binarySearch(roles, userRole)<0) {
				pass = false;
			}
		}
		
		if(pass) {
			chain.doFilter(request, response);
		}else {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	public void destroy() {
		logger.info("{} 객체 소멸.", getClass().getName());
	}

}










