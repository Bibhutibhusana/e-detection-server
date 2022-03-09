package com.nic.edetection.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IVehicleTransactionHistoryService;
import com.nic.edetection.model.VehicleTransactionHistory;
import com.nic.edetection.repo.VehicleTransactionHistoryRepository;

@Service
@Transactional
public class VehicleTransactionHistoryService implements IVehicleTransactionHistoryService{
	@Autowired VehicleTransactionHistoryRepository vehicleTransactionHistoryRepository; 

	@Override
	public List<VehicleTransactionHistory> getVehicleTransactionHistoryList() {
		// TODO Auto-generated method stub
		return vehicleTransactionHistoryRepository.findAll();
	}

	@Override
	public ResponseEntity<VehicleTransactionHistory> getVehicleTransactionHistoryById(Long id) throws ResourceNotFoundException {
		VehicleTransactionHistory vehicleTransactionHistory = vehicleTransactionHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("VehicleHistory not found for this id::"+id));
        return ResponseEntity.ok().body(vehicleTransactionHistory);
	}

	@Override
	public VehicleTransactionHistory saveVehicleTransactionHistory(
			VehicleTransactionHistory vehicleTransactionHistory) {
		// TODO Auto-generated method stub
		return vehicleTransactionHistoryRepository.save(vehicleTransactionHistory);
	}

	@Override
	public List<VehicleTransactionHistory> saveAllVehicleTransactionHistoryList(
			List<VehicleTransactionHistory> vehicleList) {

		return vehicleTransactionHistoryRepository.saveAll(vehicleList);
	}

}
