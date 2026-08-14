package com.sist.web.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.mapper.FoodMapper;
import com.sist.web.vo.FoodVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
	private final FoodMapper fMapper;
	private final int ROW=12;
	private final int BLOCK=10;
	@Override
	public List<FoodVO> foodListData(int page) {
		int start=(page*ROW)-ROW;
		List<FoodVO> list=fMapper.foodListData(start);
		return list;
	}
	
	@Override
	public int[] foodListPage(int page) {
		int count=fMapper.foodCount();
		int totalpage=(int)Math.ceil(count/(double)ROW);
		int startPage=((page-1)/BLOCK*BLOCK)+1;
		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage) endPage=totalpage;
		int[] pages= {page,totalpage,startPage,endPage,count};
		return pages;
	}

	@Override
	@Transactional
	public FoodVO foodDetailData(int no) {
		FoodVO vo=fMapper.foodDetailData(no);
		fMapper.foodHitIncrement(no);
		return vo;
	}

	@Override
	public List<FoodVO> foodFindData(String type,String fd,int page) {
		Map<String,Object> map=new HashMap<>();
		int start=(page*ROW)-ROW;
		map.put("type", type);
		map.put("fd", fd);
		map.put("start", start);
		List<FoodVO> list=fMapper.foodFindData(map);
		return list;
	}

	@Override
	public int[] foodFindPage(String type,String fd,int page) {
		Map<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("fd", fd);
		int count=fMapper.foodFindCount(map);
		int totalpage=(int)Math.ceil(count/(double)ROW);
		int startPage=((page-1)/BLOCK*BLOCK)+1;
		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage) endPage=totalpage;
		int[] pages= {page,totalpage,startPage,endPage,count};
		return pages;
	}

}
