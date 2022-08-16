package com.nic.edetection.iservice;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.VehicleTransactionHistoryDto;
import com.nic.edetection.exception.ResourceNotFoundException;


@Service
@Transactional

public interface IVehicleTransactionHistoryService {
	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryList();

	public ResponseEntity<VehicleTransactionHistoryDto> getVehicleTransactionHistoryById(Long id) throws ResourceNotFoundException;
	public VehicleTransactionHistoryDto saveVehicleTransactionHistory(VehicleTransactionHistoryDto vehicleTransactionHistory);
	public List<VehicleTransactionHistoryDto> saveAllVehicleTransactionHistoryList(List<VehicleTransactionHistoryDto> vehicleList);

	public long getVehicleTransactionHistoryCount();

	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryListLimitBy1000(Date date);

	public void saveAllVehicleTransactionHistory(
			List<VehicleTransactionHistoryDto> vehicleTransactionHistoryListForUpdate);

	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryByApplicationNoWithUser(
			@Valid String vehicleNo);

}
