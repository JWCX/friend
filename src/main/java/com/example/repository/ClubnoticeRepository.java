package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Clubnotice;

public interface ClubnoticeRepository extends JpaRepository<Clubnotice, Integer>{

   @Query(value="select * from clubnotice where meetdate>now() and clubid=:clubid order by meetdate",nativeQuery=true)
   public List<Clubnotice> ongoingnotice(@Param("clubid")int clubid);
   
   //최근 7일이내 공지글 
   @Query(value="select * from clubnotice where type=1 and clubid=:clubid and date(writedate)>=subdate(now(),Interval 7 day)) order by noticeid desc",nativeQuery=true)
   public List<Clubnotice> recentAnnouncements(int clubid);
   
   @Query(value="select * from clubnotice where noticeid=:noticeid",nativeQuery=true)
   public Clubnotice getClubnotice(@Param("noticeid")int noticeid);

   @Query(value="select * from clubnotice where clubid=:clubid order by noticeid desc",nativeQuery=true)
   public List<Clubnotice> getClubnotices(@Param("clubid") int clubid);
   
   @Query(value="SELECT n.*" + 
		   		"FROM clubnotice n, clubnotice c, hit h " + 
		   		"WHERE c.clubid=0 AND h.id=c.noticeid AND n.noticeid=c.noticeid " + 
		   		"GROUP BY c.noticeid " + 
		   		"ORDER BY (SELECT COUNT(f.id) FROM feel f WHERE f.id=c.noticeid GROUP BY c.noticeid)*1.5 + " + 
		   		"	(SELECT COUNT(com.noticeid) FROM noticecom com WHERE com.noticeid=c.noticeid GROUP BY c.noticeid)*2.5 +" + 
		   		"	COUNT(h.id)" + 
		   		"DESC LIMIT 10;",nativeQuery=true)
   public List<Clubnotice> getPopularPosts();
}

