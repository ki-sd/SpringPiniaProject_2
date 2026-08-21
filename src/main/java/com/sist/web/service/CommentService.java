package com.sist.web.service;
import java.util.*;

import com.sist.web.vo.CommentVO;

public interface CommentService {
	public List<CommentVO> commentListData(Map map);
	public int[] commentPageData(int page,int fno);
	public void commentInsert(CommentVO vo);
	public void commentDelete(int no);
	public void commentUpdate(CommentVO vo);
}
