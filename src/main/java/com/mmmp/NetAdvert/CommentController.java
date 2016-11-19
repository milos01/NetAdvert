package com.mmmp.NetAdvert;

import java.sql.Date;
import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.NetAdvert.model.Advert;
import com.mmmp.NetAdvert.model.Comment;
import com.mmmp.NetAdvert.model.User;
import com.mmmp.NetAdvert.service.AdverService;

@RestController
@RequestMapping(value="api/comment")
public class CommentController {

	
	private AdverService adverService;
	
	@Autowired(required = true)
	@Qualifier(value = "adverService")
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Comment> createComment(@RequestParam("advert_id") int advert_id,@RequestParam("comment") String text,HttpSession session){
		User u = (User) session.getAttribute("logedUser");
//		User u = this.adverService.findUser("doslicmm@live.com");
		Advert advert = this.adverService.findAdvert(advert_id);
		Comment comment = new Comment();
		comment.setAdvert(advert);
		comment.setUser(u);
		java.util.Date currentDate = new java.util.Date();
		
		try {
			comment.setDate(getFormatedDate(currentDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		comment.setText(text);
		
		this.adverService.createComment(comment);
		
		return new ResponseEntity<Comment>(comment,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public  ResponseEntity<Void> deleteComment(@PathVariable("id") int id){
		Comment c = this.adverService.findComment(id);
		this.adverService.deleteComment(c);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	public Date getFormatedDate(java.util.Date date) throws ParseException {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date parsed = format.parse(date);
		Date sql = new Date(date.getTime());
		return sql;
	}
}
