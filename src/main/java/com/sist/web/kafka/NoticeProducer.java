package com.sist.web.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeProducer {
	// NoticeProducer : Kafka Producer역할 수행 (메세지 생성)
	private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
	// Kafka로 데이터를 보내는 객체
	private static final String TOPIC="notice-topic";
	// /sub/private
	// Controller에서 호출
	public void sendNotice(ChatMessage notice) {
		// ChatMessage 객체 전송
		kafkaTemplate.send(
			TOPIC,	// notice-topic
			notice.getReceiver(),	// shim
			notice	// 메세지까지 포함
		);
		/*
		 *      Vue
		 *       |
		 *     RestController
		 *       | ChatMessage
		 *     Kafka => Producer
		 *       |
		 *     notice-topic
		 *       |
		 *     Consumer = STOMP = Vue
		 */
		System.out.println("Kafka 알림 전송:"+notice);
		// 확인
	}
	/*
	 * 		  User => Pinia
	 *          |
	 *      Controller
	 *          | send()
	 *        Kafka
	 *          |
	 *        notice-topic
	 *      
	 */
}
