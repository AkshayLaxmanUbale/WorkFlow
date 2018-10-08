package com.workflow.controller;

import java.io.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	@RequestMapping(value="/uploadfile", method=RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				
				File dir = new File("uploadfiles/");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				if(!serverFile.exists()) {
					serverFile.createNewFile();
				}
				System.out.println(serverFile);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				
				return new ResponseEntity<String>("{\"message\":\"File Uploaded successfully\"}",HttpStatus.OK);

				
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("{\"message\":\"File Uploaded exception\"}",HttpStatus.OK);
			}
		} else {
			//return "You failed to upload " + name
			//		+ " because the file was empty.";
			return new ResponseEntity<String>("{\"message\":\"File Uploaded error\"}",HttpStatus.OK);
		}
		
		//return new ResponseEntity<String>("{\"message\":\"File Uploaded successfully\"}",HttpStatus.OK);
	}


}
