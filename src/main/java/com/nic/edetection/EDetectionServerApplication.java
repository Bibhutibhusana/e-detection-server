package com.nic.edetection;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.nic.edetection.service.FileUploadService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

//exclude = { DataSourceAutoConfiguration.class, 
//	     HibernateJpaAutoConfiguration.class,
//	     DataSourceTransactionManagerAutoConfiguration.class }
@SpringBootApplication
@CrossOrigin
@Configuration
@EnableEncryptableProperties
//@EnableTransactionManagement
public class EDetectionServerApplication extends SpringBootServletInitializer {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Resource
	  FileUploadService storageService;
	public static void main(String[] args) {
		SpringApplication.run(EDetectionServerApplication.class, args);
		System.out.println("eDetection");
	}

	  public void run(String... arg) throws Exception {
	    storageService.deleteAll();
	    storageService.init();
	  }
	  
//	  @Bean
//	  public WebMvcConfigurer corsConfigurer() {
//		  return new WebMvcConfigurerAdapter() {
//			  @Override
//			  public void addCorsMappings(CorsRegistry registry) {
//				  registry.addMapping("/").allowedOrigins("http://localhost:4200");
//			  }
//		  };
	//  }

}
   