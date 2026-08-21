package com.sist.web.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.CommentService;
import com.sist.web.vo.CommentVO;

import jakarta.servlet.http.HttpSession;

import java.util.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentRestController {
	private final CommentService cService;
	
	public Map<String,Object> commonData(int page,int fno) {
		Map<String,Object> map=new HashMap<>();
		int[] pages=cService.commentPageData(page, fno);
		map.put("start", pages[2]);
		map.put("fno",fno);
		List<CommentVO> list=cService.commentListData(map);
		map=new HashMap<>();
		map.put("rList", list);
		map.put("curpage", page);
		map.put("totalpage", pages[1]);
		map.put("count", pages[3]);
		return map;
	}
	
	@GetMapping("/comment/list_vue")
	public ResponseEntity<Map<String,Object>> comment_list(@RequestParam(value="page",defaultValue="1")int page,@RequestParam("fno")int fno){
		Map<String,Object> map=new HashMap<>();
		try {
			map=commonData(page, fno);
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
	}
	@PostMapping("/comment/insert_vue")
	public ResponseEntity<Map<String,Object>> comment_insert(@RequestBody CommentVO vo,HttpSession session){
		Map<String,Object> map=new HashMap<>();
		try {
			String id=(String)session.getAttribute("userid");
			String name=(String)session.getAttribute("username");
			vo.setId(id);
			vo.setName(name);
			cService.commentInsert(vo);
			int[] pages=cService.commentPageData(vo.getPage(), vo.getFno());
			map.put("curpage", pages[0]);
			map.put("totalpage",pages[1]);
			map.put("count", pages[3]);
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
	}
	@DeleteMapping("/comment/delete_vue")
	public ResponseEntity<Map<String,Object>> comment_delete(@RequestParam("no")int no,@RequestParam("page")int page,@RequestParam("fno")int fno){
		Map<String,Object> map=new HashMap<>();
		try {
			cService.commentDelete(no);
			map=commonData(page, fno);
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
	}
	@PutMapping("/comment/update_vue")
	public ResponseEntity<Map<String,Object>> comment_update(@RequestBody CommentVO vo){
		Map<String,Object> map=new HashMap<>();
		try {
			cService.commentUpdate(vo);
			map=commonData(vo.getPage(), vo.getFno());
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
	}
}
