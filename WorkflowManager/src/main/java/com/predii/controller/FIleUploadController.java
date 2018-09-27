package com.predii.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FIleUploadController {

	@RequestMapping(value="/uploadfile", method=RequestMethod.POST)
	public ResponseEntity<String> uploadFile(HttpServletRequest request, HttpServletResponse response){
		
		MultipartHttpServletRequest mRequest;

		String filename = "upload";

		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();
			Iterator itr = mRequest.getFileNames();
			while (itr.hasNext()) {
				MultipartFile mFile = mRequest.getFile((String)itr.next());
				String fileName = mFile.getOriginalFilename();
				System.out.println(fileName);
				java.nio.file.Path path = Paths.get("C:/Data/DemoUpload/" + filename);
				Files.deleteIfExists(path);
				InputStream in = mFile.getInputStream();
				Files.copy(in, path);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("File Uploaded successfully",HttpStatus.OK);
	}

}
