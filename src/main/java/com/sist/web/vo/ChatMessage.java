package com.sist.web.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChatMessage {
	private String sender,receiver,message;
}
