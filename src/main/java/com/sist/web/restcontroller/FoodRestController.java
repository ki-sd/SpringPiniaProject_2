package com.sist.web.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.sist.web.service.FoodService;
import com.sist.web.vo.FoodVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FoodRestController {
	private final FoodService fService;
	
	@GetMapping("/food/list_vue")
	public ResponseEntity<Map<String,Object>> food_list_vue(@RequestParam(value="page",defaultValue="1")int page){
		Map<String,Object> map=new HashMap<>();
		try {
			List<FoodVO> list=fService.foodListData(page);
			int[] pages=fService.foodListPage(page);
			String[] tags= {"curpage","totalpage","startPage","endPage","count"};
			map.put("list", list);
			for(int i=0;i<pages.length;i++) {
				map.put(tags[i], pages[i]);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(map);
	}
}
