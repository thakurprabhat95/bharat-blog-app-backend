package com.blogapi.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapi.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		// file Name
		String filename = file.getOriginalFilename();
		// abc.png

		// generate random file name
		String randomId = UUID.randomUUID().toString();
		
		//random name+extension(6594be6a-0c7b-48bd-8684-141620ef2a2e.png)
		String randomFileName = randomId.concat(filename.substring(filename.lastIndexOf(".")));
		
		
		
		// create full path
		String filepath = path + File.separator + randomFileName;

		// create folder if not available
		File file2 = new File(path);
		if (!file2.exists()) {
			file2.mkdir();
		}

		// copy file
		Files.copy(file.getInputStream(), Paths.get(filepath));

		return randomFileName;
	}

	@Override
	public InputStream downloadImage(String path, String filename) throws FileNotFoundException {
		
		String fullPathName=path+ File.separator+filename;
		InputStream inputStream=new FileInputStream(fullPathName);
		return inputStream;
	}


}
