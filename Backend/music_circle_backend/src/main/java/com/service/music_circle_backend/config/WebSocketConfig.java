package com.service.music_circle_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // Register the endpoint of STOMP, and use SockJS protocol
        stompEndpointRegistry.addEndpoint("/notifications-ws", "/im") .setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Configure global message broker, client will subscribe these message brokers to receive messages
        registry.enableSimpleBroker("/notifications", "/b", "/g");

        // configure point to point message's prefix
        registry.setUserDestinationPrefix("/notify");
        registry.setApplicationDestinationPrefixes("/notify", "/user");
    }
}

