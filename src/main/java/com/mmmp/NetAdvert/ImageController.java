package com.mmmp.NetAdvert;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mmmp.NetAdvert.service.AdverService;

@Controller
public class ImageController {
	@Autowired
	 private AdverService adverService;
	@RequestMapping(value="/api/upload", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadImages(@RequestParam("file") CommonsMultipartFile file,HttpSession session){
		String path=session.getServletContext().getRealPath("/"); 
		System.out.println(path);
        String filename=file.getOriginalFilename();  
          
        System.out.println(path+" "+filename);  
        try{  
        byte barr[]=file.getBytes();  
          
        BufferedOutputStream bout=new BufferedOutputStream(  
                 new FileOutputStream(path+"/"+filename));  
        bout.write(barr);  
        bout.flush();  
        bout.close();  
          
        }catch(Exception e){System.out.println(e);}  
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
