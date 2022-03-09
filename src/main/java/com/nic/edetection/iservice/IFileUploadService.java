package com.nic.edetection.iservice;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public interface IFileUploadService {
	 public void init();
	  public Boolean save(MultipartFile file);
	  public Resource load(String filename);
	  public void deleteAll();
	  public Stream<Path> loadAll();
}
