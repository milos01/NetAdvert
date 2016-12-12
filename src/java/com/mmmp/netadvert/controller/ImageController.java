package com.mmmp.netadvert.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mmmp.netadvert.model.Picture;
import com.mmmp.netadvert.model.Realestate;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

@RestController
public class ImageController {
	@Autowired
	private AdverService adverService;
	@RequestMapping(value="/api/upload", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadImages(@RequestParam("file") CommonsMultipartFile file,HttpSession session, @RequestParam("realestate") int id){
		//String path=session.getServletContext().getRealPath("/"); 
		String path=new File("NetAdvert" + File.separator +"images").getAbsolutePath();
		String s = Paths.get("Konstrukcija i testiranje softvera").toAbsolutePath().toString();
		System.out.println(System.getenv("SystemDrive"));
		String ss = System.getenv("SystemDrive") + File.separator + "NetAdvertImages";
		File ff;
		if (Files.notExists(Paths.get(ss))){
			System.out.println("uso");
			System.out.println(ss);
			try {
				Files.createDirectories(Paths.get(ss));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        String filename=id + "_" + file.getOriginalFilename();  
        
        //User u = (User) session.getAttribute("loggedUser");
        User u = this.adverService.findUser("doslicmm@live.com");
        Realestate r = this.adverService.findRealestate(id);
        
        Picture pic = new Picture();
        
        pic.setPictureName(filename);
        pic.setProfile(false);
        pic.setRealestate(r);
        pic.setUser(u);
          
        try{  
        byte barr[]=file.getBytes();  
          
        BufferedOutputStream bout=new BufferedOutputStream(  
                 new FileOutputStream(ss+File.separator+filename));  
        bout.write(barr);  
        bout.flush();  
        bout.close();  
          
        }catch(Exception e){System.out.println(e);}
        
        this.adverService.addPicture(pic);
        
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/image", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteImage(@RequestParam("image") int imageId){
		Picture pic = this.adverService.findPicture(imageId);
		this.adverService.deletePicture(pic);
		return new ResponseEntity<Void> (HttpStatus.OK);
	}

}
