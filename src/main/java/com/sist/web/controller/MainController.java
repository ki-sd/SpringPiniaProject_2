package com.sist.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sist.web.service.FoodService;
import com.sist.web.vo.FoodVO;

import java.util.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final FoodService fService;
	
	@GetMapping("/")
	public String main_main(Model model) {
		model.addAttribute("main_html","main/home");
		return "main/main";
	}
	@GetMapping("/food/detail_before")
	public String food_detail_before(@RequestParam("no")int no,HttpServletResponse response,RedirectAttributes ra) {
		Cookie cookie=new Cookie("food_"+no,String.valueOf(no));
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		ra.addAttribute("no", no);
		return "redirect:/food/detail";
	}
	@GetMapping("/food/detail")
	public String food_detail(@RequestParam("no")int no,Model model) {
		FoodVO vo=fService.foodDetailData(no);
		model.addAttribute("no", no);
		model.addAttribute("vo", vo);
		model.addAttribute("main_html", "food/detail");
		return "main/main";
	}
	@RequestMapping("/member/login")
	public String member_login(Model model)
	{
		model.addAttribute("main_html", "member/login");
		return "main/main";
	}
}
