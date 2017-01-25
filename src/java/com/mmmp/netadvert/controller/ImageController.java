package com.mmmp.netadvert.controller;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Picture;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

@RestController
public class ImageController {
	@Autowired
	private AdverService adverService;

	/**
	 * This method is part of advert rest service. Method uploads realestate image and sets if uploaded picture is profile picture.
	 * @param file picture that is being uploaded
	 * @param session
	 * @param id realestate id for which is picture added
	 * @param isProfile parameter if picture is profile
	 * @return Http status 200 OK
	 * @throws IllegalStateException
	 * @throws IOException
	 * @see Advert, Picture
	 */
	@RequestMapping(value="/api/upload", method=RequestMethod.POST)
	public ResponseEntity<Picture> uploadImages(@RequestParam("file") MultipartFile file,HttpSession session, @RequestParam("realestate") int id, @RequestParam("is_profile") boolean isProfile,Principal user) throws IllegalStateException, IOException{
		User u=this.adverService.findUser(user.getName());
        if(u==null){
        	return new ResponseEntity<> (HttpStatus.FORBIDDEN);
        }
        if(file==null){
        	return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
//        File convFile = new File( file.getOriginalFilename());
//        file.transferTo(convFile);
        Advert a = this.adverService.findAdvert(id);
        if(a==null){
        	return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        boolean exists = false;
        if(a.getUser().getId()==u.getId() && a.getDeleted()==false){
        		exists = true;
        }
        if(exists = false){
        	return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
		String ss = "/Users/macbookpro/Desktop";
		if (Files.notExists(Paths.get(ss))){
			try {
				Files.createDirectories(Paths.get(ss));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Date dd = new Date();
        String filename=id + "_" + dd.getTime() + "_" + file.getOriginalFilename();  
        
        Picture pic = new Picture();
        
        pic.setPictureName(filename);
        pic.setProfile(false);
        pic.setAdvert(a);
        pic.setUser(u);
        pic.setProfile(isProfile);
          
        try{  
        byte barr[]=file.getBytes();  
          
        BufferedOutputStream bout=new BufferedOutputStream(
                 new FileOutputStream(ss+"/"+filename));
        bout.write(barr);
        bout.flush();
        bout.close();
          
        }catch(Exception e){System.out.println(e);}
        
        this.adverService.addPicture(pic);
        
		return new ResponseEntity<Picture>(pic, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service. It deletes uploaded advert image
	 * @param name picture id which is being deleted
	 * @return Http status 200 OK
	 * @see Advert, Picture
	 */
	@RequestMapping(value="/api/image/{id:.*}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteImage(@PathVariable("id") String name,Principal user){
		User u = this.adverService.findUser(user.getName());
        if(u==null){
        	return new ResponseEntity<Void> (HttpStatus.FORBIDDEN);
        }
		Picture pic = this.adverService.findPictureByName(name);
		if(pic==null){
			return new ResponseEntity<Void> (HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(pic.getUser().getId()!=u.getId()){
			return new ResponseEntity<Void> (HttpStatus.FORBIDDEN);
		}
		this.adverService.deletePicture(pic);
		return new ResponseEntity<Void> (HttpStatus.OK);
	}

}
