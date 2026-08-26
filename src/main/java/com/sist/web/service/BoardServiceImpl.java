package com.sist.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.web.entity.BootBoard;
import com.sist.web.repository.BootBoardRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl {
	private final BootBoardRepository bRepo;
	final int ROWSIZE=10;
	public Page<BootBoard> findAll(int page){
		Pageable pg=PageRequest.of(page-1, ROWSIZE,Sort.by(Sort.Direction.DESC,"no"));
		return bRepo.findAll(pg);
	}
	public int[] getPageData(Page<BootBoard> p,int page) {
		int[] datas=new int[4];
		int totalpage=p.getTotalPages();
		if (totalpage==0) {
			return new int[]{1,1,1,1}; 
		}
		final int BLOCK=10;
		int startPage=((page-1)/BLOCK*BLOCK)+1;
		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage) endPage=totalpage;
		datas[0]=page;
		datas[1]=totalpage;
		datas[2]=startPage;
		datas[3]=endPage;
		return datas;
	}
	public BootBoard findByNo(int no) {
		return bRepo.findByNo(no);
	}
	public void boardSave(BootBoard vo) {
		bRepo.save(vo);
	}
}
