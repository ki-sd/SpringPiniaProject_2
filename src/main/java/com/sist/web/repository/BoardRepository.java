package com.sist.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.web.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	
}
