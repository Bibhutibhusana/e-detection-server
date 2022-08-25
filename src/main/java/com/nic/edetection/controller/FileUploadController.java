package com.nic.edetection.controller;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
import com.nic.edetection.repo.VehicleTransactionHistoryRepository;

@RestController
@RequestMapping("/api/v1/")
public class FileUploadController {
	@Autowired  VehicleTransactionHistoryRepository vehicleTransactionHistoryRepo;
	

	@Value("${base-path}")
	private String basePath;
	
	@Autowired
	  IFileUploadService storageService;

	
	 @PostMapping("/upload/{userId}")
	  public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable(value = "userId") Long userId) {
	    String message = "";
	    Map<String,String> msg = new HashMap<>();
	    try {
	      String message1 = storageService.save(file,userId);
	     // System.out.println(message1);
	      //message = "Uploaded the file successfully: " + file.getOriginalFilename();
	     
	      msg.put("message", message1);
	      return ResponseEntity.ok(msg);
	    }  catch (NoSuchElementException e) {
	    	msg.put("message", "Duplicate Value Inserted");
	    	return ResponseEntity.status(HttpStatus.NO_CONTENT).body(msg);
		}
	    //	catch (ParseException e) {
//			msg.put("message", "Unparsable Date format : dd/mm/yyyy hh:mm");
//	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
//		}
//		catch(FileAlreadyExistsException e) {
//			msg.put("message", "File Already Exist");
//	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
//		} 
	    
	    catch (Exception e) {
	    	System.out.println(e);
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      msg.put("message", message);
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(msg);
	    }
	  }
	  @GetMapping("/files")
	  public ResponseEntity<List<FileInfo>> getListFiles() {
	    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
	      String filename = path.getFileName().toString();
	      long size =0;
	      try {
			 size = Files.size(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      String url = MvcUriComponentsBuilder
	          .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build().toString();
	      return new FileInfo(filename, url,size);
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
 