package com.hellomeritz.chat.global.config;

import com.hellomeritz.chat.global.handler.ChatErrorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@EnableWebSocketMessageBroker
public class SocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatErrorHandler chatErrorHandler;

    public SocketConfig(ChatErrorHandler chatErrorHandler) {
        this.chatErrorHandler = chatErrorHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .setErrorHandler(chatErrorHandler)
            .addEndpoint("/ws/connect")
            .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }

}
