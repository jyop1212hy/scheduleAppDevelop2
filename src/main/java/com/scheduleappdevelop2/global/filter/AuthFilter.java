package com.scheduleappdevelop2.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // ===== 예외 처리 경로 =====
        boolean isLogin = uri.equals("/users/login") && method.equals("POST");
        boolean isSignup = uri.equals("/users") && method.equals("POST");
        boolean isUserRead = uri.startsWith("/users") && method.equals("GET");
        boolean isScheduleRead = uri.startsWith("/schedules") && method.equals("GET");

        if (isLogin || isSignup || isUserRead || isScheduleRead) {
            chain.doFilter(request, response);
            return;
        }

        // ===== 세션 검사 =====
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("로그인이 필요합니다.");
            return;
        }

        // 인증 성공 → 다음으로 진행
        chain.doFilter(request, response);
    }
}
