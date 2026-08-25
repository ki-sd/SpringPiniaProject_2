package com.sist.web.controller;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.sist.web.vo.ChatMessage;
import lombok.RequiredArgsConstructor;

/*
 *     1. 사용자가 채팅페이지 접속
 *     2. SpringSecurity가 로그인 사용자 확인
 *     3. ThymeLeaf => LOGIN_USER 생성
 *     4. Vue.createApp()
 *     5. Pinia등록
 *     6. useChatStore()
 *     7. onMounted()
 *     8. SockJS 연결
 *     8-1. STOMP 연결 => this.stomp=Stomp.over(socket)
 *     9. 서버 채팅 : destination subscribe
 *        채널 => 출력위치설정
 *     10. 실시간 메세지 대기
 *            |
 *         store.msg => Enter => store.send()
 *            |
 *         STOMP SEND
 *            |
 *         WebSocket에서 처리
 *            |
 *         상대방 / 전체 메세지 전송
 *            |
 *         STOMP => Message 수신
 *            |
 *         store.message에 추가
 *            |
 *         Vue 수행 => 화면에 채팅 출력
 */

@Controller
@RequiredArgsConstructor
public class ChatController {
	// STOMP => 서버에서 특정한 클라이언트에게 메세지를 전송하는 역할
	// 1:1 , 알림 => id를 포함
	private final SimpMessagingTemplate template;
	private final Set<String> users=ConcurrentHashMap.newKeySet();
	// 여러 쓰레드에서 동시에 안전하게 사용할 수 있게 만든다
	// 중복을 제거해서 관리 : WebSocket사용시 사용자 정보
	/*
	 *     /topic/public
	 *     => 접속자 모든 사람에게 메세지 전송
	 *     => /user/{username}/queue/notify
	 *        => 1:1채팅
	 *     => jackson을 이용해서 메세지를 JSON으로 자동화 처리
	 */
	@MessageMapping("/chat/public")
    @SendTo("/topic/chat") // 전체 채팅 => /topic
	public ChatMessage publicChat(ChatMessage msg,Principal p) {
		// HttpSession 사용불가
		// SpringSecurity 이용 => Principal => Session 형식
		msg.setSender(p.getName());
		return msg; // /topic/chat => 모든 접속자에게 전송
	}
	
    @MessageMapping("/chat/private")
    public void privateChat(ChatMessage msg,Principal p) {
        String sender = p.getName();
        msg.setSender(sender);
        template.convertAndSendToUser(msg.getReceiver(),"/queue/chat",msg);
        template.convertAndSendToUser(sender,"/queue/chat",msg);
    }
    // 접속자 목록 전송
    @MessageMapping("/chat/join")
    public void join(Principal p) {
    	String username=p.getName();
    	users.add(username);
    	template.convertAndSend("/topic/users",users);
	}

    @GetMapping("/chat")
    public String chat_page(Model model) {
    	model.addAttribute("main_html", "chat/chat");
        return "main/main";
    }

}
