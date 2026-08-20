package com.sist.web.vo;
import java.util.*;

import lombok.Data;
//NO      NOT NULL NUMBER       
//FNO     NOT NULL NUMBER       
//ID               VARCHAR2(20) 
//NAME    NOT NULL VARCHAR2(51) 
//MSG     NOT NULL CLOB         
//REGDATE          DATE      
@Data
public class CommentVO {
	private int no,fno,page;
	private String id,name,msg,dbday;
	private Date regdate;
}
