package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.BoardService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class BoardController {
   @Autowired
   private BoardService boardService;

   @RequestMapping("/board")
   public Map<String,Object> readBoard(int token, int page){
	  return boardService.readBoard(token, page);
   }

   @PostMapping("/board")
   public Map<String,Object> writePost(@RequestBody String json) throws JSONException {
	   JSONObject obj=new JSONObject(json);
	   int token=obj.getInt("token");
	   int id=0;
	   String title=obj.getString("title");
	   String content=obj.getString("content");
	
      return boardService.writePost(token,id,title,content);
     
   }
   
   @PostMapping("/board/read")
   public Map<String,Object> boardReadCount(@RequestBody String json) throws JSONException {
	   JSONObject obj=new JSONObject(json);
	   int userid=obj.getInt("token"); //유저id
	   int id=obj.getInt("id"); //게시글id
	   
	  int result= boardService.addReadCount(userid,id);
	  Map<String,Object> map=new HashMap<>(); 
	  if(result==1) {
		   map.put("increment", true);
		   
	   }
	  else {
		  map.put("increment", false);
	  }
	  return map;
	   
   }
   @PostMapping("/board/like")
   public ResponseEntity<String> addLike(@RequestBody String json) throws JSONException{
	   JSONObject obj=new JSONObject(json);
	   int token=obj.getInt("token"); //유저
	   int id=obj.getInt("id"); //게시글
	   Boolean liked=obj.getBoolean("liked"); //true : 좋아요 false : 좋아요 취소
	   if(true) {
		   boardService.addLike(token,id,liked);
		   	return new ResponseEntity<>(HttpStatus.OK);
		   
	   }
	   else {
		   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	   }
	   
   }
   @PostMapping("/board/comment")
   public Map<String,Object> addComment(@RequestBody String json) throws JSONException{
	   JSONObject obj=new JSONObject(json);
	   		 
	   
	   int token=obj.getInt("token");
	   int id=obj.getInt("id");
	   String content=obj.getString("content");
	   
	   return boardService.addComment(token,id,content);
   }
   
}












