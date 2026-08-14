package com.sist.web.service;
import java.util.*;

import com.sist.web.vo.FoodVO;

public interface FoodService {
	public List<FoodVO> foodListData(int page);
	public int[] foodListPage(int page);
	public FoodVO foodDetailData(int no);
	public List<FoodVO> foodFindData(String type,String fd,int page);
	public int[] foodFindPage(String type,String fd,int page);
}
