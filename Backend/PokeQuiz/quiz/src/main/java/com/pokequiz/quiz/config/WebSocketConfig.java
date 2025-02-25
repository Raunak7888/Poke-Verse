package com.pokequiz.quiz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple in-memory message broker for broadcasting messages
        config.enableSimpleBroker("/topic", "/queue"); // Add "/queue" for private messages

        // Prefix for client-to-server communication (e.g., "/app/sendAnswer")
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket endpoint for client connections
        registry.addEndpoint("/quiz-ws")
                .setAllowedOriginPatterns("*") // Allow all origins (replace with specific origins for production)
                .withSockJS(); // Fallback to SockJS for older browsers
    }
}
