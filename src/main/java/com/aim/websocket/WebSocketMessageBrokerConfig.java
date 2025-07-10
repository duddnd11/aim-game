package com.aim.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer{
	
	@Bean
	public HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor() {	
		return new HttpSessionHandshakeInterceptor();
	}
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/socket");       //클라이언트에서 보낸 메세지를 받을 prefix (클라이언트 -> 소켓)
        registry.enableSimpleBroker("/chat","/pvp");    //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달 (소켓 -> 클라이언트)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")   //SockJS 연결 주소
                .withSockJS()  //버전 낮은 브라우저에서도 적용 가능
                .setInterceptors(httpSessionHandshakeInterceptor());
        
        registry.addEndpoint("/ws-pvp")   //SockJS 연결 주소
		        .withSockJS()  //버전 낮은 브라우저에서도 적용 가능
		        .setInterceptors(httpSessionHandshakeInterceptor());
        
    }

}
