package com.nic.edetection.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.VehicleTransactionHistoryDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IVehicleTransactionHistoryService;
import com.nic.edetection.model.VehicleTransactionHistory;
import com.nic.edetection.repo.VehicleTransactionHistoryRepository;

@Service
@Transactional
public class VehicleTransactionHistoryService implements IVehicleTransactionHistoryService{

	@Autowired VehicleTransactionHistoryRepository vehicleTransactionHistoryRepository; 
	@Autowired ModelMapper modelMapper;

	@Override
	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryList() {
		// TODO Auto-generated method stub
		return vehicleTransactionHistoryRepository.findAll().stream().map(value -> modelMapper.map(value,VehicleTransactionHistoryDto.class)).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<VehicleTransactionHistoryDto> getVehicleTransactionHistoryById(Long id) throws ResourceNotFoundException {
		VehicleTransactionHistory vehicleTransactionHistory = vehicleTransactionHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("VehicleHistory not found for this id::"+id));
		VehicleTransactionHistoryDto vehHistory = modelMapper.map(vehicleTransactionHistory, VehicleTransactionHistoryDto.class);
        return ResponseEntity.ok().body(vehHistory);
	}

	@Override
	public VehicleTransactionHistoryDto saveVehicleTransactionHistory(
			VehicleTransactionHistoryDto vehicleTransactionHistory) {
		// TODO Auto-generated method stub
		VehicleTransactionHistory vehHistory = modelMapper.map(vehicleTransactionHistory, VehicleTransactionHistory.class);
				vehicleTransactionHistory = modelMapper.map( vehicleTransactionHistoryRepository.save(vehHistory), VehicleTransactionHistoryDto.class);
		return vehicleTransactionHistory;
	}

	@Override
	public List<VehicleTransactionHistoryDto> saveAllVehicleTransactionHistoryList(
			List<VehicleTransactionHistoryDto> vehicleList) {
		List<VehicleTransactionHistory> vehListHistory = vehicleList.stream().map(value -> modelMapper.map(vehicleList, VehicleTransactionHistory.class)).collect(Collectors.toList());
		return vehicleTransactionHistoryRepository.saveAll(vehListHistory).stream().map(value -> modelMapper.map(value, VehicleTransactionHistoryDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public long getVehicleTransactionHistoryCount() {
		//System.out.print(vehicleTransactionHistoryRepository.count());
		return vehicleTransactionHistoryRepository.count();
	}

	@Override
	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryListLimitBy1000(Date transactionDate) {
		//System.out.println("method is called!");
		Date toDt;
		toDt =new Date(transactionDate.getTime() + 1 * 24 * 60 * 60 * 1000);
//		System.out.println(transactionDate +" "+toDt);
		return vehicleTransactionHistoryRepository.getVehicleTransactionHistoryListLimitBy1000(transactionDate,toDt).stream().map(value -> modelMapper.map(value, VehicleTransactionHistoryDto.class)).collect(Collectors.toList());
	}
	public void saveAllVehicleTransactionHistory(
			List<VehicleTransactionHistoryDto> vehicleTransactionHistoryListForUpdate) {
		List<VehicleTransactionHistory> vehHistoryList = vehicleTransactionHistoryListForUpdate.stream().map(value -> modelMapper.map(value, VehicleTransactionHistory.class)).collect(Collectors.toList());
		vehicleTransactionHistoryRepository.saveAll(vehHistoryList);
	}

	@Override
	public List<VehicleTransactionHistoryDto> getVehicleTransactionHistoryByApplicationNoWithUser(
			@Valid String vehicleNo) {
		// TODO Auto-generated method stub
		List<VehicleTransactionHistoryDto> vehList = vehicleTransactionHistoryRepository.findByVehicleNoAndUser(vehicleNo);
		return vehList;
	}


}
