package com.sist.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.FoodVO;

import java.util.*;
@Mapper
@Repository
public interface FoodMapper {
	public List<FoodVO> foodListData(int start);
	public int foodCount();
	public FoodVO foodDetailData(int no);
	public void foodHitIncrement(int no);
	public List<FoodVO> foodFindData(Map<String,Object> map);
	public int foodFindCount(Map<String,Object> map);
}
