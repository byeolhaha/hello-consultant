package com.hellomeritz.member.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class IpSensor implements HandlerInterceptor {

    private static final ThreadLocal<String> clientIPThreadLocal = new ThreadLocal<>();
    private final ObjectMapper objectMapper;

    private String[] headerTypes =
            {"X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
                    "HTTP_X_FORWARDED_FOR"};

    public IpSensor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        String ip = null;
        for (String headerType : headerTypes) {
            ip = request.getHeader(headerType);
            if (ip != null) break;
        }
        if (ip == null) ip = request.getRemoteAddr();

        // 스레드 로컬에 IP 값을 저장
        clientIPThreadLocal.set(ip);

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex)
            throws Exception {
        clientIPThreadLocal.remove();
    }

    public String getClientIP() {
        return clientIPThreadLocal.get();
    }
}
