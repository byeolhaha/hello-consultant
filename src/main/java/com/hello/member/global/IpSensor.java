package com.hello.member.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class IpSensor implements HandlerInterceptor {

    private static final ThreadLocal<String> clientIPThreadLocal = new ThreadLocal<>();
    private final ObjectMapper objectMapper;

    private final String headerType = "X-Forwarded-For";

    public IpSensor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        String ip = request.getHeader(headerType);
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
