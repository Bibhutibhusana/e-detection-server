package com.nic.edetection.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IVehicleTransactionHistoryService;
import com.nic.edetection.model.VehicleTransactionHistory;

@RestController
@CrossOrigin(origins={"http://localhost:4200","http://192.168.137.77:4200","http://192.168.43.199:4200","https://bibhutibhusana.github.io"})
@RequestMapping("/api/v1")
public class VehicleTransactionHistoryController {
	@Autowired IVehicleTransactionHistoryService vehicleTransactioniHistoryService;
	
	@GetMapping(value = "/vehicle-transaction-history")
	public List<VehicleTransactionHistory> getVehicleTransactionHistoryList(){
		return vehicleTransactioniHistoryService.getVehicleTransactionHistoryList();
	}
	
	@GetMapping("/vehicle-transaction-history/{id}")
	public ResponseEntity<VehicleTransactionHistory> getVehicleTransactionHistoryById(@Valid @PathVariable(name="id")Long id) throws ResourceNotFoundException{
		return vehicleTransactioniHistoryService.getVehicleTransactionHistoryById(id);
	}

}
