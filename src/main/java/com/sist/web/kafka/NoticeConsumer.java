package com.sist.web.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sist.web.vo.ChatMessage;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
/*
 *      NoticeProdcer => sendNotice()
 *         | notice-topic
 *      NoticeConsumer => consumerNotice()
 *      
 *      send() => topic => @KafkaListener => convertAndSend() => subscribe() 구독 : 데이터를 받는 곳 (STOMP)
 *                Producer     Consumer
 *      
 *      1) kafka로 메세지를 보낸다 : Producer
 *         => kafkaTemplate.send(
 *                 TOPIC,
 *                 notice.getReceiver(),
 *                 notice
 *            );
 *      2) 큐 => 메세지를 저장하는 공간 : notice-topic
 *      3) 메세지를 받는다 Consumer
 *         @KafkaListener(
 *            topics = "notice-topic",
 *            groupId="notice-group"
 *         )
 *      4) 브라우저로 데이터 전송
 *         template.convertAndSend(
 *            dest,
 *            notice.getMessage()
 *         );
 *      5) 데이터를 받아서 출력
 *         this.stomp.subscribe('/sub/notice/'+id,msg=>{
 *                 this.showToast(msg.body)
 *                 this.
 *         })
 *         
 */
public class NoticeConsumer {
	private final SimpMessagingTemplate template;
	// STOMP를 이용해 브라우저로 메세지 전송
	@KafkaListener(
		topics="notice-topic", // Producer에서 생성한 키와 동일
		groupId="notice-group" // Consumer Group
	)
	public void consumerNotice(ChatMessage notice) {
		// Kafka에서 메세지가 들어오면 => Spring에서 자동 호출
		System.out.println("Kafka 알림 수신:"+notice);
		String dest="/sub/notice/"+notice.getReceiver();
		template.convertAndSend(
			dest,
			notice.getMessage()
		);
		System.out.println("STOMP알림 전송완료:"+dest);
	}
}
