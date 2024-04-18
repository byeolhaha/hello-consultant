package com.hellomeritz.member.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class IpSensorInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<String> clientIPThreadLocal = new ThreadLocal<>();
    private final ObjectMapper objectMapper;

    public IpSensorInterceptor(ObjectMapper objectMapper) {
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
            handler, Exception ex)
            throws Exception {
        // 스레드 로컬 초기화
        clientIPThreadLocal.remove();
    }

    public ThreadLocal<String> getClientIPThreadLocal() {
        return clientIPThreadLocal;
    }
}
