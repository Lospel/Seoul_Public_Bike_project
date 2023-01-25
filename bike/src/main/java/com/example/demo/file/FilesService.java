package com.example.demo.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesService {
    @Autowired(required = true)
	FilesRepository filesRepository;
	
	public void save(Image files) {
		Image f = new Image();
		f.setFilename(files.getFilename());
		f.setFileOriName(files.getFileOriName());
		f.setFileurl(files.getFileurl());
		// f.setData(files.getData());
		
		filesRepository.save(f);
	}
}
