package com.ajay.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker   // web socket message broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // this means we want to add a new stomp endpoint to our websocket configuration
        /*
        * ws -> web socket
        * wss -> if we want to work with secure websockets like http or https
        * */
        registry.addEndpoint("/ws").withSockJS();
    }


    /*
     * This is the point where we want to enable the simple broker or
     * to add the application destination prefixes
     * */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");  // should go to destination, having /app in their path
        registry.enableSimpleBroker("/topic");
    }
}
