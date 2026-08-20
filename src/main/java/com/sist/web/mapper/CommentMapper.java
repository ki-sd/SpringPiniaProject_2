package com.sist.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.CommentVO;

import java.util.*;
//<select id="commentListData" resultType="com.sist.web.vo.CommentVO" parameterType="hashmap">
//SELECT no,fno,id,name,msg,TO_CHAR(regdate,'yyyy-mm-dd hh24:mi:ss') AS dbday
//FROM piniaComment
//WHERE fno=#{fno}
//ORDER BY no DESC
//OFFSET #{start} ROWS FETCH NEXT 5 ROWS ONLY
//</select>
//<select id="commentRowCount" resultType="int" parameterType="int">
//SELECT COUNT(*)
//FROM piniaComment
//WHERE fno=#{fno}
//</select>
//<insert id="commentInsert" parameterType="com.sist.web.vo.CommentVO">
//INSERT INTO piniaComment (no,fno,id,name,msg) 
//VALUES (pc_no_seq.nextval,#{fno},#{id},#{name},#{msg})
//</insert>
@Mapper
@Repository
public interface CommentMapper {
	public List<CommentVO> commentListData(Map map);
	public int commentRowCount(int fno);
	public void commentInsert(CommentVO vo);
}
