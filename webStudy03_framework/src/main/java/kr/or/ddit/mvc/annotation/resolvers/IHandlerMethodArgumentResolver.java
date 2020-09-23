package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHandlerMethodArgumentResolver {

	boolean isSupported(Parameter parameter);
	Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp);
}
