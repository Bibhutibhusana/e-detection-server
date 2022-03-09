package com.nic.edetection;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nic.edetection.service.FileUploadService;

//exclude = { DataSourceAutoConfiguration.class, 
//	     HibernateJpaAutoConfiguration.class,
//	     DataSourceTransactionManagerAutoConfiguration.class }
@SpringBootApplication
//@EnableTransactionManagement
public class EDetectionServerApplication {
	@Resource
	  FileUploadService storageService;
	public static void main(String[] args) {
		SpringApplication.run(EDetectionServerApplication.class, args);
	}

	  public void run(String... arg) throws Exception {
	    storageService.deleteAll();
	    storageService.init();
	  }

}
   