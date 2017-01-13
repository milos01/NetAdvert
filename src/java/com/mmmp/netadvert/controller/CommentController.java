package com.mmmp.netadvert.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.DTO.NewCommentDTO;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Comment;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value="api/comment")
public class CommentController {

	@Autowired
	private AdverService adverService;
	
	@Autowired(required = true)
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}
	/**
	 * 
	 * @param advert_id - id of advert that you can leave comment
	 * @param text - text commentary
	 * @param session
	 * @return Comment object, http response 200 ok
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Comment> createComment(@RequestBody NewCommentDTO commentt,HttpSession session){
//		User u = (User) session.getAttribute("logedUser");
		User u = this.adverService.findUser("milan@gmail.com");
		Advert advert = this.adverService.findAdvert(commentt.getAdvert_id());
		
		if (advert==null){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Comment comment = new Comment();
		comment.setAdvert(advert);
		comment.setUser(u);
		Date currentDate = new Date();
		comment.setDate(currentDate);
		
		if (commentt.getText()==null || commentt.getText().equals("")){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		comment.setText(commentt.getText());
		
		this.adverService.createComment(comment);
		
		
		return new ResponseEntity<Comment>(comment,HttpStatus.OK);
	}
	
	/**
	 * Method will delete an comment of advert. If advert doesn't exist return 500 Internal server error.
	 * @param id - id of comment
	 * @return Http response 200 ok
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public  ResponseEntity<Void> deleteComment(@PathVariable("id") Integer id){
		if (id==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			this.adverService.deleteComment(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * Method will retuns list of comments an advert.
	 * @param id - id of advert
	 * @return List of comments, http response 200 ok
	 */
	@RequestMapping(value="/advert/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Comment>> findComments(@PathVariable("id") int id){
		List<Comment> list = this.adverService.allCommentsOfAdvert(id);
		if (list==null || list.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Comment>>(list,HttpStatus.OK);
	}
	
	public Date getFormatedDate(java.util.Date date) throws ParseException {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date parsed = format.parse(date);
		Date sql = new Date(date.getTime());
		return sql;
	}
}
