//package com.sist.web.listener;
//
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//import org.springframework.web.socket.messaging.SessionSubscribeEvent;
//
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class WebSocketEventListener {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final Set<String> connectedUsers = ConcurrentHashMap.newKeySet();
//
//    // 1. 연결 시 세션 목록에 사용자 추가
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        if (headerAccessor.getUser() != null) {
//            connectedUsers.add(headerAccessor.getUser().getName());
//        }
//    }
//
//    // 2. 사용자가 토픽 구독을 완료하는 시점에 전체 목록 전송
//    @EventListener
//    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String destination = headerAccessor.getDestination();
//
//        // /topic/users 구독 요청이 들어왔을 때 최신 접속자 목록 전송
//        if ("/topic/users".equals(destination)) {
//            messagingTemplate.convertAndSend("/topic/users", connectedUsers);
//        }
//    }
//
//    // 3. 연결 종료 시 목록에서 제거 후 갱신된 목록 전송
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        if (headerAccessor.getUser() != null) {
//            connectedUsers.remove(headerAccessor.getUser().getName());
//            messagingTemplate.convertAndSend("/topic/users", connectedUsers);
//        }
//    }
//}