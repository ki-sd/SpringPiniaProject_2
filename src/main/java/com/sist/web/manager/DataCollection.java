package com.sist.web.manager;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.web.vo.RealFindVO;

public class DataCollection {
	public static List<RealFindVO> dataCollect(){
		List<RealFindVO> list=new ArrayList<>();
		try{
			Document doc=Jsoup.connect("https://rank.ezme.net").get();
			Elements words=doc.select(".rank_word");
			Elements images=doc.select(".rank_img");
			for(int i=0;i<words.size();i++){
				String w=words.get(i).text();
				String img=images.get(i).attr("data-pagespeed-lazy-src");
		        RealFindVO vo=new RealFindVO();
		        vo.setRank(i+1);
		        vo.setWord(w);
		        vo.setImages(img);
		        list.add(vo);
//		        System.out.println(w);
//		        System.out.println(img);
//		        System.out.println("======================");
			}
		}catch(Exception ex){}
		return list;
	}
}
