package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.UserFunction;
import com.example.model.Alarm;
import com.example.model.Clubnotice;
import com.example.model.Feel;
import com.example.model.Hit;
import com.example.model.Noticecom;
import com.example.repository.AlarmRepository;
import com.example.repository.ClubInterestRepository;
import com.example.repository.ClubRepository;
import com.example.repository.ClubUserRepository;
import com.example.repository.ClubnoticeRepository;
import com.example.repository.FeelRepository;
import com.example.repository.HitRepository;
import com.example.repository.ImgRepository;
import com.example.repository.NoticecomRepository;
import com.example.repository.UserInterestRepository;
import com.example.repository.UserRepository;

@Service
public class BoardService {
   @Autowired
   EntityManager entityManager;

   @Autowired
   private ClubUserRepository clubUserRepository;
   @Autowired
   private ClubRepository clubRepository;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private UserInterestRepository userinterestRepository;
   @Autowired
   private ImgRepository imgRepository;
   @Autowired
   private AlarmRepository alarmRepository;
   @Autowired
   private ClubnoticeRepository clubnoticeRepository;
   @Autowired
   private NoticecomRepository noticecomRepository;
   @Autowired
   private HitRepository hitRepository;
   @Autowired
   private FeelRepository feelRepository;
   
   @Autowired
   private UserFunction userFunction;

   public void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager;
   }
   
   public Map<String,Object> readBoard(int token, int page) {
		Map<String,Object> posts=new HashMap<>();
		List<Clubnotice> clubnotice=clubnoticeRepository.getClubnotices(0);
		
		List<Map<String,Object>> maplist=new ArrayList<>();
		
		if(page*20>=clubnotice.size()&&clubnotice.size()>page*20-20) {
			for(int i=page*20-20;i<clubnotice.size();i++) {
				int count=0;
				List<Feel> flist=feelRepository.liked(3, clubnotice.get(i).getNoticeid());
					for(int k=0;k<flist.size();k++) {
						if(flist.get(k).getUserid()==token) {
							count++;
						}
					}
					boolean liked=false;
					if(count==1) {
						liked=true;
					}
				Map<String,Object> map=new HashMap<>();
	    		  Map<String,Object> userMap=new HashMap<>();
	    		  userMap.put("id", clubnotice.get(i).getUserid());
	    		  userMap.put("nickName",  userRepository.getUserName(clubnotice.get(i).getUserid()));
	    		  userMap.put("image", imgRepository.getImg(1, clubnotice.get(i).getUserid())); //유저이미지
	    		  
	    		  map.put("type", clubnotice.get(i).getType());
	    		  map.put("id", clubnotice.get(i).getNoticeid());
	    		  map.put("title", clubnotice.get(i).getTitle());
	    		  map.put("content", clubnotice.get(i).getContent());
	    		  map.put("writedate", clubnotice.get(i).getWritedate());
	    		  map.put("user", userMap);
	    		  map.put("views", hitRepository.readcounts(3, clubnotice.get(i).getNoticeid()));
	    		  map.put("likes", feelRepository.likes(3, clubnotice.get(i).getNoticeid()));
	    		  map.put("liked", liked);
	    		  Map<String,Object> comments=new HashMap<>();
	    		  comments.put("id", 0);
	    		 List<Noticecom> notice= noticecomRepository.noticecom(clubnotice.get(i).getNoticeid());
	    		 List<Map<String,Object>> reMap=new ArrayList<>();
	    		
	    		 for(int k=0;k<notice.size();k++) {
	    			 Map<String,Object> coMap=new HashMap<>();
	    			 Map<String,Object> uMap=new HashMap<>();
	    			 uMap.put("id", notice.get(k).getUserid());
	    			 uMap.put("nickName", userRepository.getUserName(notice.get(k).getUserid()));
	    			 uMap.put("image", imgRepository.getImg(1, notice.get(k).getUserid()));
	    			 coMap.put("id", notice.get(k).getComid());
	    			 coMap.put("user", uMap);
	    			 coMap.put("content",notice.get(k).getContent());
	    			 coMap.put("writedate", notice.get(k).getWritedate());
	    			 reMap.add(coMap);
	    		 }
	    		 map.put("comments", userFunction.ListToMap(reMap, "id"));
	    		 maplist.add(map);
			}
			posts.put("posts",userFunction.ListToMap(maplist, "id"));
		}
		else if(page*20<clubnotice.size()) {
			for(int i=page*20-20;i<page*20;i++) {
				int count=0;
				List<Feel> flist=feelRepository.liked(3, clubnotice.get(i).getNoticeid());
					for(int k=0;k<flist.size();k++) {
						if(flist.get(k).getUserid()==token) {
							count++;
						}
					}
					boolean liked=false;
					if(count==1) {
						liked=true;
					}
				
				 Map<String,Object> map=new HashMap<>();
	    		  Map<String,Object> userMap=new HashMap<>();
	    		  userMap.put("id", clubnotice.get(i).getUserid());
	    		  userMap.put("nickName",  userRepository.getUserName(clubnotice.get(i).getUserid()));
	    		  userMap.put("image", imgRepository.getImg(1, clubnotice.get(i).getUserid())); //유저이미지
	    		  
	    		  map.put("type", clubnotice.get(i).getType());
	    		  map.put("id", clubnotice.get(i).getNoticeid());
	    		  map.put("title", clubnotice.get(i).getTitle());
	    		  map.put("content", clubnotice.get(i).getContent());
	    		  map.put("writedate", clubnotice.get(i).getWritedate());
	    		  map.put("user", userMap);
	    		  map.put("views", hitRepository.readcounts(3, clubnotice.get(i).getNoticeid()));
	    		  map.put("likes", feelRepository.likes(3, clubnotice.get(i).getNoticeid()));
	    		  map.put("liked", liked);
	    		  Map<String,Object> comments=new HashMap<>();
	    		  comments.put("id", 0);
	    		 List<Noticecom> notice= noticecomRepository.noticecom(clubnotice.get(i).getNoticeid());
	    		 List<Map<String,Object>> reMap=new ArrayList<>();
	    		 for(int k=0;k<notice.size();k++) {
	    			 Map<String,Object> coMap=new HashMap<>();
	    			 Map<String,Object> uMap=new HashMap<>();
	    			 uMap.put("id", notice.get(k).getUserid());
	    			 uMap.put("nickName", userRepository.getUserName(notice.get(k).getUserid()));
	    			 uMap.put("image", imgRepository.getImg(1, notice.get(k).getUserid()));
	    			 coMap.put("id", notice.get(k).getComid());
	    			 coMap.put("user", uMap);
	    			 coMap.put("content",notice.get(k).getContent());
	    			 coMap.put("writedate", notice.get(k).getWritedate());
	    			 reMap.add(coMap);
	    		 }
	    		 map.put("comments", userFunction.ListToMap(reMap, "id"));
	    		 maplist.add(map);
			}
			posts.put("posts",userFunction.ListToMap(maplist, "id"));			
		}
		System.out.println(clubnotice.size());
		Boolean hasMorePages=true;
		if(clubnotice.size()/20+1==page)
			hasMorePages=false;
		posts.put("hasMorePages", hasMorePages);
	
		return posts;
	}
   
   // 4-5-2 : 그룹 공지글 작성하기
   // request : 공지글 내용
   // response : """{ success : Boolean } 가입성공:OK , 가입실패:BAD_REQUEST"""
//   @PostMapping("/group/notice")
   public Map<String,Object> writePost(int userid,int clubid,String title,String content) {
     
         Clubnotice cn = new Clubnotice();
         cn.setClubid(clubid);
         cn.setContent(content);
         cn.setMeetdate(null);
         cn.setMeetlocation(null);
         cn.setMeettime(null);
         cn.setNoticeid(0);
         cn.setTitle(title);
         cn.setType(3);
         cn.setUserid(userid);
         cn.setWritedate(new Date());
         clubnoticeRepository.save(cn);
         
         List<Integer> users=clubUserRepository.getMemberId(clubid);
         for(int i=0;i<users.size();i++) {
        	 Alarm al=new Alarm();
        	 al.setAlarmid(0);
            al.setGubun(6);
            al.setId(clubid);
            al.setMessage("그룹의 새 공지글이 작성되었어요");
            al.setReceiveyn(false);
            al.setUserid(users.get(i));
            al.setWritedate(new Date());
            alarmRepository.save(al);
         }
         Map<String,Object> posts=new HashMap<>();
         List<Clubnotice> clubnotice=clubnoticeRepository.getClubnotices(clubid);
         List<Map<String,Object>> list=new ArrayList<>();
         boolean hasMorePages=false;
         if(clubnotice.size()>=20) {
        	 hasMorePages=true;
        	 for(int i=0;i<20;i++) {
        		 Map<String,Object> map=new HashMap<>();
	    		  Map<String,Object> userMap=new HashMap<>();
	    		  
	    		  int count=0;
	    		  List<Feel> flist=feelRepository.liked(3, clubnotice.get(i).getNoticeid());
					for(int k=0;k<flist.size();k++) {
						if(flist.get(k).getUserid()==userid) {
							count++;
						}
					}
					boolean liked=false;
					if(count==1) {
						liked=true;
					}
	    		  userMap.put("id", clubnotice.get(i).getUserid());
	    		  userMap.put("nickName",  userRepository.getUserName(clubnotice.get(i).getUserid()));
	    		  userMap.put("image", imgRepository.getImg(1, clubnotice.get(i).getUserid())); //유저이미지
	    		  
	    		  map.put("type", clubnotice.get(i).getType());
	    		  map.put("id", clubnotice.get(i).getNoticeid());
	    		  map.put("title", clubnotice.get(i).getTitle());
	    		  map.put("content", clubnotice.get(i).getContent());
	    		  map.put("writedate", clubnotice.get(i).getWritedate());
	    		  map.put("user", userMap);
	    		  map.put("views", hitRepository.readcounts(3, clubnotice.get(i).getNoticeid()));
	    		  map.put("likes", feelRepository.likes(3, clubnotice.get(i).getNoticeid()));
	    		  map.put("liked", liked);
	    		  Map<String,Object> comments=new HashMap<>();
	    		  comments.put("id", 0);
	    		 List<Noticecom> notice= noticecomRepository.noticecom(clubnotice.get(i).getNoticeid());
	    		 List<Map<String,Object>> reMap=new ArrayList<>();
	    		 for(int k=0;k<notice.size();k++) {
	    			 Map<String,Object> coMap=new HashMap<>();
	    			 Map<String,Object> uMap=new HashMap<>();
	    			 uMap.put("id", notice.get(k).getUserid());
	    			 uMap.put("nickName", userRepository.getUserName(notice.get(k).getUserid()));
	    			 uMap.put("image", imgRepository.getImg(1, notice.get(k).getUserid()));
	    			 coMap.put("id", notice.get(k).getComid());
	    			 coMap.put("user", uMap);
	    			 coMap.put("content",notice.get(k).getContent());
	    			 coMap.put("writedate", notice.get(k).getWritedate());
	    			 reMap.add(coMap);
	    		 }
	    		 map.put("comments", userFunction.ListToMap(reMap, "id"));
	    		 list.add(map);
				
        	 }
        	 posts.put("posts",userFunction.ListToMap(list, "id"));
   
         }
         else {
        	 for(int i=0;i<clubnotice.size();i++) {
        		 Map<String,Object> map=new HashMap<>();
	    		  Map<String,Object> userMap=new HashMap<>();
	    		  userMap.put("id", clubnotice.get(i).getUserid());
	    		  userMap.put("nickName",  userRepository.getUserName(clubnotice.get(i).getUserid()));
	    		  userMap.put("image", imgRepository.getImg(1, clubnotice.get(i).getUserid())); //유저이미지
	    		  
	    		  map.put("type", clubnotice.get(i).getType());
	    		  map.put("id", clubnotice.get(i).getNoticeid());
	    		  map.put("title", clubnotice.get(i).getTitle());
	    		  map.put("content", clubnotice.get(i).getContent());
	    		  map.put("writedate", clubnotice.get(i).getWritedate());
	    		  map.put("user", userMap);
	    		  map.put("views", hitRepository.readcounts(3, clubnotice.get(i).getNoticeid()));
	    		  map.put("likes", feelRepository.likes(3, clubnotice.get(i).getNoticeid()));
	    		  
	    		  Map<String,Object> comments=new HashMap<>();
	    		  comments.put("id", 0);
	    		 List<Noticecom> notice= noticecomRepository.noticecom(clubnotice.get(i).getNoticeid());
	    		 List<Map<String,Object>> reMap=new ArrayList<>();
	    		 for(int k=0;k<notice.size();k++) {
	    			 Map<String,Object> coMap=new HashMap<>();
	    			 Map<String,Object> uMap=new HashMap<>();
	    			 uMap.put("id", notice.get(k).getUserid());
	    			 uMap.put("nickName", userRepository.getUserName(notice.get(k).getUserid()));
	    			 uMap.put("image", imgRepository.getImg(1, notice.get(k).getUserid()));
	    			 coMap.put("id", notice.get(k).getComid());
	    			 coMap.put("user", uMap);
	    			 coMap.put("content",notice.get(k).getContent());
	    			 coMap.put("writedate", notice.get(k).getWritedate());
	    			 reMap.add(coMap);
	    		 }
	    		 map.put("comments", userFunction.ListToMap(reMap, "id"));
				
	    		 list.add(map);
        	 }
        	 posts.put("posts",userFunction.ListToMap(list, "id"));
        	 }
         posts.put("hasMorePages", hasMorePages);
         
         
         
         
      return posts;
      
   }

   // 4-6 : 그룹 공지글 댓글
   // request : 댓글 내용
   // response : { success : Boolean } 가입성공:OK , 가입실패:BAD_REQUEST
   public int noticecom(int noticeid, int userid, boolean attendyn, String content) {
      if (true) {
         Noticecom nc = new Noticecom();
         nc.setAttendyn(attendyn);
         nc.setComid(0);
         nc.setContent(content);
         nc.setNoticeid(noticeid);
         nc.setUserid(userid);
         nc.setWritedate(new Date());

         noticecomRepository.save(nc);
         
         Alarm al=new Alarm();
         al.setGubun(7);
         al.setId(clubnoticeRepository.getClubnotice(noticeid).getClubid());
         al.setMessage("내공지에 댓글이달렸어요");
         al.setReceiveyn(false);
         al.setUserid(clubnoticeRepository.getClubnotice(noticeid).getUserid());
         al.setWritedate(new Date());
         alarmRepository.save(al);
         return 1;
      } else {
         return 0;
      }
   }
	

	public int addReadCount(int userid, int id) {
		// TODO Auto-generated method stub
		
		List<Hit> list=hitRepository.addReadCount(3, id, userid);
		if(list.size()==0) {
			Hit h=new Hit();
			h.setGubun(3);
			h.setHittime(new Date());
			h.setId(id);
			h.setUserid(userid);
			hitRepository.save(h);
			return 1;
		}
		else {
			return 0;
		}
	}

	//좋아요 수 추가하기
	public void addLike(int token, int id, Boolean liked) {
		// TODO Auto-generated method stub
		if(liked==true) {
			Feel feel=new Feel();
			
			feel.setGubun(3);
			feel.setId(id);
			feel.setType(1);
			feel.setUserid(token);
			feel.setWritedate(new Date());
			feelRepository.save(feel);
		}
		else {
			feelRepository.deleteByGubunAndIdAndUseridAndType(3, id, token, 1);
		}
	}
	
	public Map<String,Object> addComment(int token, int id, String content) {
		// TODO Auto-generated method stub
	
		Noticecom nc=new Noticecom();
		nc.setAttendyn(false);
		nc.setComid(0);
		nc.setContent(content);
		nc.setUserid(token);
		nc.setNoticeid(id);
		nc.setWritedate(new Date());
		noticecomRepository.save(nc);
		
		Map<String,Object> reMap=new HashMap<>();
		
		/*	"DB에 댓글 등록후 해당 게시글의 댓글 전체 반환
		comments: { 
		    id(댓글ID): { 
		        id: Integer,  // 댓글 id
		        user: {
		            id: Integer,  // 댓글 작성자 id
		            nickName: String,  // 댓글 작성자 닉네임
		            image: String,  // 댓글 작성자 프사 url
		        },
		        content: String  // 댓글 내용
		        writedate: Date  // 댓글 작성일
		    },
		    ...
		}"
*/	
		List<Noticecom> nlist=noticecomRepository.noticecom(id);
		List<Map<String,Object>> commentList=new ArrayList<>();
		
			for(int i=0;i<nlist.size();i++) {
				Map<String,Object> map=new HashMap<>();
				Map<String,Object> uMap=new HashMap<>();
				uMap.put("id", nlist.get(i).getUserid());
				uMap.put("nickName", userRepository.getUserName(nlist.get(i).getUserid()));
				uMap.put("image",imgRepository.getImg(1, nlist.get(i).getUserid()));
				
				map.put("id", nlist.get(i).getComid());
				map.put("user",uMap);
				map.put("content", nlist.get(i).getContent());
				map.put("writedate",nlist.get(i).getWritedate());
				commentList.add(map);
			}
			reMap.put("comments", userFunction.ListToMap(commentList, "id"));
		return reMap;
		}

	public Map<String,Object> getPopularPosts(int token) {
		Map<String,Object> posts=new HashMap<>();
		List<Map<String,Object>> maplist=new ArrayList<>();
		List<Clubnotice> clubnotice=clubnoticeRepository.getPopularPosts();
		
		
		for(int i=0;i<clubnotice.size();i++) {
			int count=0;
			List<Feel> flist=feelRepository.liked(3, clubnotice.get(i).getNoticeid());
				for(int k=0;k<flist.size();k++) {
					if(flist.get(k).getUserid()==token) {
						count++;
					}
				}
				boolean liked=false;
				if(count==1) {
					liked=true;
				}
			Map<String,Object> map=new HashMap<>();
    		  Map<String,Object> userMap=new HashMap<>();
    		  userMap.put("id", clubnotice.get(i).getUserid());
    		  userMap.put("nickName",  userRepository.getUserName(clubnotice.get(i).getUserid()));
    		  userMap.put("image", imgRepository.getImg(1, clubnotice.get(i).getUserid())); //유저이미지
    		  
    		  map.put("type", clubnotice.get(i).getType());
    		  map.put("id", clubnotice.get(i).getNoticeid());
    		  map.put("title", clubnotice.get(i).getTitle());
    		  map.put("content", clubnotice.get(i).getContent());
    		  map.put("writedate", clubnotice.get(i).getWritedate());
    		  map.put("user", userMap);
    		  map.put("views", hitRepository.readcounts(3, clubnotice.get(i).getNoticeid()));
    		  map.put("likes", feelRepository.likes(3, clubnotice.get(i).getNoticeid()));
    		  map.put("liked", liked);
    		  Map<String,Object> comments=new HashMap<>();
    		  comments.put("id", 0);
    		 List<Noticecom> notice= noticecomRepository.noticecom(clubnotice.get(i).getNoticeid());
    		 List<Map<String,Object>> reMap=new ArrayList<>();
    		
    		 for(int k=0;k<notice.size();k++) {
    			 Map<String,Object> coMap=new HashMap<>();
    			 Map<String,Object> uMap=new HashMap<>();
    			 uMap.put("id", notice.get(k).getUserid());
    			 uMap.put("nickName", userRepository.getUserName(notice.get(k).getUserid()));
    			 uMap.put("image", imgRepository.getImg(1, notice.get(k).getUserid()));
    			 coMap.put("id", notice.get(k).getComid());
    			 coMap.put("user", uMap);
    			 coMap.put("content",notice.get(k).getContent());
    			 coMap.put("writedate", notice.get(k).getWritedate());
    			 reMap.add(coMap);
    		 }
    		 map.put("comments", userFunction.ListToMap(reMap, "id"));
    		 maplist.add(map);
		}
		
		List<Integer> index = new ArrayList<>();
		for(Map<String, Object> el: maplist) {
			 index.add((Integer)el.get("id"));
		}
		posts.put("posts", userFunction.ListToMap(maplist, "id"));
		posts.put("index", index);
		
		return posts;
	}
}








