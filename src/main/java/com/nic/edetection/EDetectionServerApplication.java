package com.nic.edetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nic.edetection.service.FileUploadService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@SpringBootApplication
@Configuration
@EnableEncryptableProperties
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
	  
	  @Bean
	  public CorsFilter corsFilter() {
	      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      final CorsConfiguration config = new CorsConfiguration();
	      config.setAllowCredentials(true);
	      // Don't do this in production, use a proper list  of allowed origins
	      List<String> origins = new ArrayList<String>();
	      origins.add("http://localhost:4200");
	      origins.add("http://192.168.137.105:4200");
	      origins.add("http://192.168.29.199:4200");
	      origins.add("https://odtransportmis.nic.in/eDetection");
	      config.setAllowedOriginPatterns(origins);
	      config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept","authorization"));
	      config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	      source.registerCorsConfiguration("/**", config);
	      return new CorsFilter(source);
	  }
	  

}
   