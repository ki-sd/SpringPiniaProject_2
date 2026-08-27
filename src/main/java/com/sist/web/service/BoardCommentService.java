package com.sist.web.service;

import com.sist.web.vo.BootCommentVO;

public interface BoardCommentService {
//	public BootCommentVO boardParentInfoData(int no);
//	public void boardGroupStepIncrement(int group_id,int group_step);
	public BootCommentVO boardCommentReReply(int pno,BootCommentVO vo);
//	public void boardDepthIncrement(int no);
}
