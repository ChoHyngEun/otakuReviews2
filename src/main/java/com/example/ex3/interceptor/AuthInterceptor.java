package com.example.ex3.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.ex3.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // css, js, img 파일 예외처리
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/css") || requestURI.startsWith("/js") || requestURI.startsWith("/img")) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null && !requestURI.equals("/login") && !requestURI.equals("/") && !requestURI.equals("/index") && !requestURI.equals("/signup")  && !requestURI.equals("/otp")  && !requestURI.equals("/otp-c")) {
            response.sendRedirect("/login");
            return false;
        } else if (user != null && requestURI.equals("/main")) {
            // 사용자 인증 정보가 있는 경우 "/main" 요청에 대해서는 리다이렉트를 시키지 않음
            return true;
        }

        // 예외 페이지 설정
        if (requestURI.equals("/index") || requestURI.equals("/") || requestURI.equals("/signup") || requestURI.equals("/otp") || requestURI.equals("/otp-c")) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.getViewName().startsWith("redirect:")) {
            modelAndView.getModelMap().addAttribute("basePath", request.getContextPath());
        }
    }

}