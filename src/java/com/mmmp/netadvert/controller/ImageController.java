package com.mmmp.netadvert.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

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
import com.mmmp.netadvert.model.Realestate;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

@RestController
public class ImageController {
	@Autowired
	private AdverService adverService;

	@RequestMapping(value="/api/upload", method=RequestMethod.POST)
	public ResponseEntity<Picture> uploadImages(@RequestParam("file") MultipartFile file,HttpSession session, @RequestParam("realestate") int id, @RequestParam("is_profile") boolean isProfile){
		User u = (User) session.getAttribute("logedUser");
        if(u==null){
        	return new ResponseEntity<> (HttpStatus.FORBIDDEN);
        }
        if(file==null){
        	return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        Realestate r = this.adverService.findRealestate(id);
        if(r==null){
        	return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        boolean exists = false;
        for(Advert a : r.getAdverts()){
        	if(a.getUser().getId()==u.getId() && a.getIs_deleted()==false){
        		exists = true;
        		break;
        	}
        }
        if(exists = false){
        	return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
		String ss = System.getenv("SystemDrive") + File.separator + "NetAdvertImages";
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
        pic.setRealestate(r);
        pic.setUser(u);
        pic.setProfile(isProfile);
          
        try{  
        byte barr[]=file.getBytes();  
          
        BufferedOutputStream bout=new BufferedOutputStream(  
                 new FileOutputStream(ss+File.separator+filename));  
        bout.write(barr);  
        bout.flush();  
        bout.close();  
          
        }catch(Exception e){System.out.println(e);}
        
        this.adverService.addPicture(pic);
        
		return new ResponseEntity<Picture>(pic, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/image/{id:.*}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteImage(@PathVariable("id") String name, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
        if(u==null){
        	return new ResponseEntity<Void> (HttpStatus.FORBIDDEN);
        }
		Picture pic = this.adverService.findPictureByName(name);
		if(pic.getUser().getId()!=u.getId()){
			return new ResponseEntity<Void> (HttpStatus.FORBIDDEN);
		}
		this.adverService.deletePicture(pic);
		return new ResponseEntity<Void> (HttpStatus.OK);
	}

}
