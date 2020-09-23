package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletSpecArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		boolean support = HttpServletRequest.class.equals(parameterType)||
				HttpServletResponse.class.equals(parameterType)||
				HttpSession.class.equals(parameterType);
		return support;
	}

	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp) {
		Class<?> parameterType = parameter.getType();
		Object realParameter = null;
		if(HttpServletRequest.class.equals(parameterType)) {
			realParameter = req;
		}else if (HttpServletResponse.class.equals(parameterType)) {
			realParameter = resp;
		}else if (HttpSession.class.equals(parameterType)) {
			realParameter = req.getSession();
		}
		return realParameter;
	}

}
