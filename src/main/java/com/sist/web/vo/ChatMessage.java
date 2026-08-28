package com.sist.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *      실행
 *      ------
 *      Vue => 댓글 작성
 *       |
 *      BoardCommentRestController
 *       |
 *      DB 댓글 저장
 *      -------------------------
 *       | 전송 (알림)
 *       알림을 생성
 *       NoticeProducer
 *       |     -------- Kafka에서 메세지를 보낸다
 *       kafka => notice-topic
 *       |
 *       NoticeConsumer
 *       |     ------- Kafka에서 메세지를 읽는다
 *       SimpMessageTemplate
 *       |
 *       STOMP
 *       |
 *       Vue => boardStore.js
 *       |
 *       showToast
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
	private String sender,receiver,message;
}
