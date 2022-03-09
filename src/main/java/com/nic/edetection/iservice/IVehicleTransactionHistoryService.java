package com.nic.edetection.iservice;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.model.VehicleTransactionHistory;

@Service
@Transactional

public interface IVehicleTransactionHistoryService {
	public List<VehicleTransactionHistory> getVehicleTransactionHistoryList();

	public ResponseEntity<VehicleTransactionHistory> getVehicleTransactionHistoryById(Long id) throws ResourceNotFoundException;
	public VehicleTransactionHistory saveVehicleTransactionHistory(VehicleTransactionHistory vehicleTransactionHistory);
	public List<VehicleTransactionHistory> saveAllVehicleTransactionHistoryList(List<VehicleTransactionHistory> vehicleList);

}
