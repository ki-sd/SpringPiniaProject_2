package com.sist.web.service;
import java.util.*;
import org.springframework.stereotype.Service;

import com.sist.web.mapper.CommentMapper;
import com.sist.web.vo.CommentVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentMapper mapper;
	
	@Override
	public List<CommentVO> commentListData(Map map) {
		List<CommentVO> list=mapper.commentListData(map);
		return list;
	}

	@Override
	public int[] commentPageData(int page,int fno) {
		int count=mapper.commentRowCount(fno);
		int start=page*10-10;
		int totalpage=(int)Math.ceil(count/10.0);
		int[] pages= {page,totalpage,start,count};
		return pages;
	}

	@Override
	public void commentInsert(CommentVO vo) {
		mapper.commentInsert(vo);
	}

	@Override
	public void commentDelete(int no) {
		mapper.commentDelete(no);
	}

	@Override
	public void commentUpdate(CommentVO vo) {
		mapper.commentUpdate(vo);
	}

}
