package kr.or.ddit.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.vo.FileCommandVO;

/**
 * 쿠키의 생성과 획득을 지원하는 유틸리티
 *
 *SRP 
 *OCP
 *LSP
 *ISP
 *DIP(Depedency Injection Principle), DI(Depedency Injection) pattern, Strategy Pattern
 */
public class CookieUtils {
	private HttpServletRequest req;
	private Map<String, Cookie> cookieMap;
	
	public CookieUtils(HttpServletRequest req) {
		super();
		this.req = req;
		cookieMap = new LinkedHashMap<>();
		Cookie[] cookies = req.getCookies();
		if(cookies!=null) {
			for(Cookie tmp : cookies) {
				cookieMap.put(tmp.getName(), tmp);
			}
		}
	}
	
	public Cookie getCookie(String name){
		return cookieMap.get(name);
	}
	
	public boolean exists(String name) {
		return cookieMap.containsKey(name);
	}
	
	public String getCookieValue(String name) throws IOException{
		Cookie cookie = cookieMap.get(name);
		String value = null;
		if(cookie!=null) value = URLDecoder.decode( cookie.getValue() , "UTF-8" );
		return value;
	}

	public static Cookie createCookie(String name, String value) throws IOException{
		value = URLEncoder.encode(value, "UTF-8");
		Cookie cookie = new Cookie(name, value);
		return cookie;
	}
	
	public static Cookie createCookie(String name, String value, int maxAge) throws IOException{
		Cookie cookie = createCookie(name, value);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	public static enum TextType {DOMAIN, PATH}
	/**
	 * @param name
	 * @param value
	 * @param type 다음 파라미터 text 사용 조건을 결정함.
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static Cookie createCookie(String name, String value, TextType type, String text) throws IOException{
		Cookie cookie = createCookie(name, value);
		if(TextType.DOMAIN==type) {
			cookie.setDomain(text);
		}else if(TextType.PATH == type){
			cookie.setPath(text);
		}
		return cookie;
	}
	
	public static Cookie createCookie(String name, String value, TextType type, String text, int maxAge) throws IOException{
		Cookie cookie = createCookie(name, value, type, text);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	public static Cookie createCookie(String name, String value, String domain, String path) throws IOException{
		Cookie cookie = createCookie(name, value);
		cookie.setPath(path);
		cookie.setDomain(domain);
		return cookie;
	}
	
	public static Cookie createCookie(String name, String value, String domain, String path, int maxAge) throws IOException{
		Cookie cookie = createCookie(name, value, domain, path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
}

















