package com.scheduleappdevelop2.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;

        String uri = httpReq.getRequestURI();

        // 인증 예외 경로 (로그인/회원가입)
        if (uri.startsWith("/users/login") || uri.startsWith("/users/signup")) {
            chain.doFilter(request, response);
            return;
        }

        // 세션 확인
        HttpSession session = httpReq.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"로그인이 필요합니다.\"}");
            ((jakarta.servlet.http.HttpServletResponse) response).setStatus(401);
            return;
        }

        // 통과
        chain.doFilter(request, response);
    }
}
