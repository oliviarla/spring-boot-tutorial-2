package com.oliviarla.springboot.config.auth;

import com.oliviarla.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        boolean isLoginUserAnnotation=parameter.getParameterAnnotation(LoginUser.class)!=null; //@LoginUser 어노테이션 붙어있을 시 true
        boolean isUserClass= SessionUser.class.equals(parameter.getParameterType()); //파라미터 클래스 타입이 SessionUser.class일 경우 true
        return isLoginUserAnnotation&&isUserClass;
    } //컨트롤러 메소드의 특정 파라미터를 지운하는지 판단

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    } //파라미터에 전달할 객체 생성, 세션에서 객체 가져옴

}
