package com.sist.web.restcontroller;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.vo.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import com.sist.web.kafka.NoticeProducer;
import com.sist.web.mapper.*;
import com.sist.web.service.BoardCommentService;

@RestController
@RequiredArgsConstructor
public class BoardCommentRestController {
	private final BoardCommentMapper bMapper;
	private final BoardCommentService bService;
	private final SimpMessagingTemplate template;
	private final NoticeProducer nProducer;
	// => insert/update/delete => 화면 데이터 갱신
	public Map<String,Object> commentListData(int page, int board_no) {
		Map<String,Object> map=new HashMap<>();
		int start=(page*10)-10;
		map.put("start", start);
		map.put("board_no", board_no);
		
		List<BootCommentVO> list=bMapper.boardCommentListData(map);
		int count=bMapper.boardCommentCount(board_no);
		int totalpage=(int)(Math.ceil(count/10.0));
		
		map=new HashMap<>();
		map.put("list", list);
		map.put("curpage", page);
		map.put("totalpage", totalpage);
		map.put("count", count);
		
		return map;
	}
	
	@Async
	@GetMapping("/reply/list_vue")
	public ResponseEntity<Map<String,Object>> board_list(@RequestParam("board_no") int board_no, @RequestParam("page") int page) {
		Map<String,Object> map=new HashMap<>();
		try {
			map=commentListData(page, board_no);
		} catch (Exception e) {
			// 예전 코드: return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
		// 예전 코드: return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_OK)
	}
	@Async
	@PostMapping("/reply/insert_vue")
	/*
	 * 	@RequestBody : JSON => 객체형
	 * 	@ResponseBody : 객체를 JSON으로 변경해서 브라우저로 전송
	 * 	------------- 최근에 @RestController로 변경
	 */
	// 내장 객체 => @Controller / @RestController
	// => DispatcherServlet 연결
	public ResponseEntity<Map<String,Object>> reply_insert(@RequestBody BootCommentVO vo, HttpSession session){
		Map<String,Object> map=new HashMap<>();
		try {
			String id=(String)session.getAttribute("userid");
			String name=(String)session.getAttribute("username");
			if(id==null) {
				System.out.println("로그인 정보 없음");
			}
			vo.setId(id);
			vo.setName(name);
			
			bMapper.boardCommentInsert(vo);
			
			map=commentListData(vo.getPage(), vo.getBoard_no());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
	}
	
	@PostMapping("/reply/rereply_insert_vue")
	public ResponseEntity<Map<String,Object>> rereply_insert(@RequestBody BootCommentVO vo,HttpSession session){
		Map<String,Object> map=new HashMap<>();
		try {
			BootCommentVO pvo=bMapper.boardParentInfoData(vo.getNo());
			bMapper.boardGroupStepIncrement(pvo.getGroup_id(), pvo.getGroup_step());
			vo.setGroup_id(pvo.getGroup_id());
			vo.setGroup_step(pvo.getGroup_step()+1);
			vo.setGroup_tab(pvo.getGroup_tab()+1);
			vo.setRoot(vo.getNo());
			vo.setId((String)session.getAttribute("userid"));
			vo.setName((String)session.getAttribute("username"));
			bMapper.boardCommentReReply(vo);
			bMapper.boardDepthIncrement(vo.getNo());
			if(!pvo.getId().equals(vo.getId())) {
//				template.convertAndSend("/sub/notice/"+pvo.getId(),"[📢댓글 알림]"+vo.getId()+"님이 댓글을 달았습니다");
				ChatMessage notice=new ChatMessage(vo.getId(),pvo.getId(),"[📢댓글 알림]"+vo.getId()+"님이 댓글을 달았습니다");
				nProducer.sendNotice(notice);
			}
			map=commentListData(vo.getPage(), vo.getBoard_no());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
	}
}