package com.aim.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocket
@Slf4j
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
	
	private final WebSocketHandler webSocketHandler;

	// 어떤 URL로 받을지 설정
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		log.info("registerWebSocketHandlers");
		registry.addHandler(webSocketHandler, "/ws-chat")
        		.setAllowedOrigins("*");
	}
}
