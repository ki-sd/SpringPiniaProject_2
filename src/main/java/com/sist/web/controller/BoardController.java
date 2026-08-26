package com.sist.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.entity.BootBoard;
import com.sist.web.service.BoardServiceImpl;
import java.util.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardServiceImpl bService;
	
	@GetMapping("/board/list")
	public String board_list(@RequestParam(value="page",defaultValue="1")int page,Model model) {
		Page<BootBoard> p=bService.findAll(page);
		List<BootBoard> list=new ArrayList<>();
		if(p!=null && p.hasContent()) {
			list=p.getContent();
		}
		int[] pages=bService.getPageData(p, page);
		model.addAttribute("list", list);
		model.addAttribute("curpage",page);
		model.addAttribute("totalpage",pages[1]);
		model.addAttribute("startPage",pages[2]);
		model.addAttribute("endPage",pages[3]);
		model.addAttribute("main_html", "board/list");
		return "main/main";
	}
	
	@GetMapping("/board/detail")
	public String board_detail(@RequestParam("no")int no,Model model) {
		BootBoard vo=bService.findByNo(no);
		vo.setHit(vo.getHit()+1);
		bService.boardSave(vo);
		model.addAttribute("vo", vo);
		model.addAttribute("main_html", "board/detail");
		return "main/main";
	}
	
	@GetMapping("/board/insert")
	public String board_insert(Model model) {
		model.addAttribute("main_html", "board/insert");
		return "main/main";
	}
	
	@PostMapping("/board/insert_ok")
	public String board_insert_ok(@ModelAttribute("vo")BootBoard vo) {
		bService.boardSave(vo);
		return "redirect:/board/list";
	}
	
}
