package com.nic.edetection.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.dto.VehicleTransactionHistoryDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.iservice.IVehicleTransactionHistoryService;

@RestController
@RequestMapping("/api/v1")
public class VehicleTransactionHistoryController {
	@Autowired IVehicleTransactionHistoryService vehicleTransactionHistoryService;
	
	@Autowired IUserLoginService userLoginService;
	
	@GetMapping(value = "/vehicle-transaction-history")
	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryList(){
		return vehicleTransactionHistoryService.getVehicleTransactionHistoryList();
	}
	@PostMapping(value="/vehicle-transaction-history-by-transaction-date")
	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryListByTransactionDate(@Valid @RequestBody Date transactionDate){
		return vehicleTransactionHistoryService.getVehicleTransactionHistoryListByTransactionDate(transactionDate);
	}
	
	@GetMapping("/vehicle-transaction-history/{id}")
	public ResponseEntity<VehicleTransactionHistoryDto> getVehicleTransactionHistoryById(@Valid @PathVariable(name="id")Long id) throws ResourceNotFoundException{
		return vehicleTransactionHistoryService.getVehicleTransactionHistoryById(id);
	}
	
	@GetMapping("/vehicle-transaction-history-count")
	public long getVehicleTransactionHistoryNumber() {
		return vehicleTransactionHistoryService.getVehicleTransactionHistoryCount();
	}

	@PostMapping("/vehicle-transaction-history-upload-status-userwise")
	public List<Map<Object,Object>> getTransactionHistoryUploadStatusUserWise(@RequestBody String userid){ 
		return userLoginService.getTollWiseDataUploadStatus();
	}
	@PostMapping("transactionDate-history-by-transactionDate-upload-status-userwise")
	public List<Map<Object,Object>> getTransactionHistoryByTransactionDateUploadStatus(@RequestBody Map<String,String> obj){
		String transactionDate=obj.get("transactionDate");
		String userid = obj.get("userid");
		return userLoginService.getTollWiseTransactionWiseDataUploadStatus( userid,transactionDate);
	}
	@GetMapping("vehicle-transaction-history-by-vehicle-no/{vehiclNo}")
	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryListByApplicationNo(@Valid @PathVariable(name="vehicleNo") String vehicleNo){
		return vehicleTransactionHistoryService.getVehicleTransactionHistoryByApplicationNoWithUser(vehicleNo);
	}
	
	@DeleteMapping("/vehicle-transaction-history/{id}")
	public Map<String, Boolean> deleteVehicleTransactionHistory( @PathVariable(value="id") Long id) throws ResourceNotFoundException{
		System.out.println(id);
		return vehicleTransactionHistoryService.deleteVehicleTransactionHistory(id);
	}
}
