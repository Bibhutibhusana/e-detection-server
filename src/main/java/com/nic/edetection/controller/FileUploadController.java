package com.nic.edetection.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.nic.edetection.iservice.IFileUploadService;
import com.nic.edetection.model.FileInfo;
import com.nic.edetection.model.VehicleTransactionHistory;
import com.nic.edetection.repo.VehicleTransactionHistoryRepository;
import com.opencsv.CSVWriter;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/v1/")
public class FileUploadController {
	@Autowired  VehicleTransactionHistoryRepository vehicleTransactionHistoryRepo;
	

	@Value("${base-path}")
	private String basePath;
	
	@Autowired
	  IFileUploadService storageService;
	
	 @PostMapping("/upload")
	  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	      storageService.save(file);
	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.ok(message);
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	    }
	  }
	  @GetMapping("/files")
	  public ResponseEntity<List<FileInfo>> getListFiles() {
	    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
	      String filename = path.getFileName().toString();
	      String url = MvcUriComponentsBuilder
	          .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build().toString();
	      return new FileInfo(filename, url);
	    }).collect(Collectors.toList());
	    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	  }
	  
	  @GetMapping("/files/{filename:.+}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
	    Resource file = storageService.load(filename);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }
	  
	  
	
		/*
		 * @Value("${base-path}") private String basePath;
		 * 
		 * @GetMapping("/upload-excel") private File uploadDepartmentUserData() throws
		 * IOException, ParseException { VehicleTransactionHistory
		 * vehicleTransactionHistory;
		 * 
		 * String fileName =
		 * "D:\\NIC\\Vahan_app\\excel_sheets\\Vehicle_transaction_updated.csv";
		 * 
		 * File uploadedFile = new File(basePath+"/"+"upload.csv");
		 * 
		 * FileWriter fileWriter = new FileWriter(uploadedFile);
		 * 
		 * CSVWriter writer = new CSVWriter(fileWriter);
		 * 
		 * 
		 * Scanner csvReader = new Scanner(new FileReader(fileName)); String row; int i
		 * = 0;
		 * 
		 * while ((row = csvReader.nextLine()) != null) {
		 * 
		 * String[] data = row.split(","); writer.writeNext(data);
		 * //vehicleTransactionHistoryRepo.saveAll(data); if(i > 200 && i< 200) {
		 * vehicleTransactionHistory = new VehicleTransactionHistory(); Date date1=new
		 * SimpleDateFormat("dd/MM/yyyy").parse(data[1]);
		 * vehicleTransactionHistory.setTransactionDate(date1);
		 * vehicleTransactionHistory.setVehicleNo(data[2]);
		 * vehicleTransactionHistory.setVtClass(data[3]);
		 * vehicleTransactionHistoryRepo.save(vehicleTransactionHistory);
		 * System.out.println(data[0]+ " "+ data[1]+" " +data[2]+" "+data[3] ); } i +=1;
		 * 
		 * } csvReader.close(); File toBeUploadedFile = new File(fileName); return
		 * toBeUploadedFile; }
		 */
}
 