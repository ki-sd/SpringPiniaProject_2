package com.sist.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.mapper.BoardCommentMapper;
import com.sist.web.vo.BootCommentVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardCommentServiceImpl implements BoardCommentService {
	private final BoardCommentMapper bMapper;
	
	@Override
	@Transactional
	public BootCommentVO boardCommentReReply(int pno, BootCommentVO vo) {
		// 상위 댓글 정보 읽기
		BootCommentVO pvo=bMapper.boardParentInfoData(pno);
		bMapper.boardGroupStepIncrement(pvo.getGroup_id(), pvo.getGroup_step());
		vo.setGroup_id(pvo.getGroup_id());
		vo.setGroup_step(pvo.getGroup_step()+1);
		vo.setGroup_tab(pvo.getGroup_tab()+1);
		vo.setRoot(vo.getNo());
		bMapper.boardCommentReReply(vo);
		bMapper.boardDepthIncrement(vo.getNo());
		return vo;
	}

}
