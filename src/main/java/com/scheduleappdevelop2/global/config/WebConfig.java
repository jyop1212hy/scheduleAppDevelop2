package com.scheduleappdevelop2.global.config;

import com.scheduleappdevelop2.global.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    /**
     * AuthFilter 등록 설정
     * - 애플리케이션 전역에 인증 필터를 적용한다.
     * - /users/login, /users(POST), GET 요청 등은 Filter에서 예외 처리된다.
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        /// 등록할 필터 지정
        registrationBean.setFilter(new AuthFilter());

        // 필터를 적용할 URL 패턴
        registrationBean.addUrlPatterns("/*");

        // 필터 실행 우선순위 (1이 가장 먼저 실행)
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
