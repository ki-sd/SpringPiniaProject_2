package com.sist.web.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import lombok.Data;
//NO      NOT NULL NUMBER         
//NAME    NOT NULL VARCHAR2(51)   
//SUBJECT NOT NULL VARCHAR2(4000) 
//CONTENT NOT NULL CLOB           
//PWD     NOT NULL VARCHAR2(10)   
//REGDATE          DATE           
//HIT              NUMBER     

/*
 *     JPA (Java Persistence API
 *     1) Java ORM (Object Relation Mapping)의 표준
 *                         -------- 관계형 데이터베이스
 *                         | MyBatis / Hibernate(JPA)
 *     2) 자바객체와 데이터베이스 컬럼 매핑
 *           --            ---
 *            |             |
 *            --------------- 동일
 *            => INSERT / UPDATE / DELETE
 *        => Entity : 반드시 컬럼과 일치
 *           save() / delete()
 *             | - insert/update
 *     3) 자동으로 SQL문장을 만든다 (ORM)
 *     
 *     
 *     = MyBatis / JPA
 */

@Entity
@Table(name = "bootboard")
@Data
public class BootBoard {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int no;
	private int hit;
	private String name,subject,content,pwd;
	@Column(insertable=true,updatable = false, name="regdate")
	private LocalDateTime regdate;
	@PrePersist
	public void perSist() {
		regdate=LocalDateTime.now();
	}
}
