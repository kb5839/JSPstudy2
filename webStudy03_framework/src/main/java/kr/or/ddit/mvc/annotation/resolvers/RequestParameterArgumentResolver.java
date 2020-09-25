package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

public class RequestParameterArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		RequestParameter requestParameter = parameter.getAnnotation(RequestParameter.class);
		Class<?> parameterType = parameter.getType();
		return requestParameter!=null && (ClassUtils.isPrimitiveOrWrapper(parameterType) || String.class.equals(parameterType));
	}

	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest request, HttpServletResponse response) {
		RequestParameter requestParameter = parameter.getAnnotation(RequestParameter.class);
		Class<?> parameterType = parameter.getType();
		String paramName = requestParameter.name();
		String paramValue = request.getParameter(paramName);
		Object realParameter = null;
		if(requestParameter.required() && StringUtils.isBlank(paramValue)) {
			throw new IllegalArgumentException(paramName+" 은 필수 요청파라미터임. 근데 없음.");
		}else {
			if(StringUtils.isBlank(paramValue)) {
				paramValue = requestParameter.defaultValue();
			}
			if(String.class.equals(parameterType)){
				realParameter = paramValue;
			}else if(byte.class.equals(parameterType) || Byte.class.equals(parameterType)) {
				realParameter = Byte.parseByte(paramValue);
			}else if(short.class.equals(parameterType) || Short.class.equals(parameterType)) {
				realParameter = Short.parseShort(paramValue);
			}else if(int.class.equals(parameterType) || Integer.class.equals(parameterType)) {
				realParameter = Integer.parseInt(paramValue);
			}else if(long.class.equals(parameterType) || Long.class.equals(parameterType)) {
				realParameter = Long.parseLong(paramValue);
			}else if(float.class.equals(parameterType) || Float.class.equals(parameterType)) {
				realParameter = Float.parseFloat(paramValue);
			}else if(double.class.equals(parameterType) || Double.class.equals(parameterType)) {
				realParameter = Double.parseDouble(paramValue);
			}else if(boolean.class.equals(parameterType) || Boolean.class.equals(parameterType)) {
				realParameter = Boolean.parseBoolean(paramValue);
			}else if(char.class.equals(parameterType) || Character.class.equals(parameterType)) {
				realParameter = paramValue.charAt(0);
			}
		}
		return realParameter;
	}

}













