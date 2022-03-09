package com.nic.edetection.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IVehicleDetailsService;
import com.nic.edetection.livedb.service.VehicleDetailsFromLiveDbService;
import com.nic.edetection.model.VehicleDetails;
import com.nic.edetection.repo.VehicleDetailsRepository;
import java.text.SimpleDateFormat;

@Service
@Transactional
public class VehicleDetailsService implements IVehicleDetailsService {
	
	@Autowired VehicleDetailsRepository vehicleDetailsRepository;
 
	@Override
	public List<VehicleDetails> getVehicleDetails() {
		VehicleDetailsFromLiveDbService vd = new VehicleDetailsFromLiveDbService();
		try {
			vd.getVehicleDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehicleDetailsRepository.findAll();
	}

	@Override
	public ResponseEntity<VehicleDetails> getVehicleDetailsById(@Valid Long id) throws ResourceNotFoundException {
		VehicleDetails vehicleDetails =vehicleDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("VehicleDetails not found for this id::"+id));
        return ResponseEntity.ok().body(vehicleDetails);
	}
	
	/// This is to be omitted.
	public List<Map<String, String>> getVehicleDetailsByVehicleNo(String vehicleNo) {
		
		
		
		return vehicleDetailsRepository.getVehicleDetailsListByVehicleNo(vehicleNo);
	}

	@Override
	public VehicleDetails createVehicleDetails(@Valid VehicleDetails vehicleDetails) {
		
		return vehicleDetailsRepository.save(vehicleDetails);
	}
	
	public List<VehicleDetails> getInvalidVehicleDetails(){
		return vehicleDetailsRepository.findInvalidVehicleList();
	}

	@Override
	public List<VehicleDetails> getInvalidVehicleByType(String offenseType) {
		 
		if(offenseType.equals("tax_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByTaxUpto(offenseType, offenseType);
		}
		else if(offenseType.equals("fit_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByFitUpto(offenseType, offenseType);
		}
		else if(offenseType.equals( "ins_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByInsUpto(offenseType, offenseType);
		}
		else if(offenseType.equals("nonusestat")) {
			return vehicleDetailsRepository.findInvalidVehiclesByNonUse(offenseType, offenseType);
		}
		else {
			return null;
		}
		//return vehicleDetailsRepository.findByTransactionDateGreaterThan();
	//return vehicleDetailsRepository.findInvalidVehiclesByType(offenseType, offenseType);
		
		//return vehicleDetailsRepository.findAllWhereTransactionDateGreaterThanTaxUpto();
	}
	
	public List<VehicleDetails> getVehicleDetailsListByDate(String transactionDate) throws ParseException{
		java.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(transactionDate);
		java.sql.Date fromDt= new java.sql.Date(date.getTime());
		java.sql.Date toDt;
		toDt =new java.sql.Date(date.getTime() + 1 * 24 * 60 * 60 * 1000);
		return vehicleDetailsRepository.findVehicleDetailsListByDate(fromDt,toDt);
	}

	@Override
	public List<VehicleDetails> getInvalidVehicleListByDateAndType(String offenseType, String transactionDate) throws ParseException {
		
		java.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(transactionDate);
		java.sql.Date fromDt= new java.sql.Date(date.getTime());
		java.sql.Date toDt;
		toDt =new java.sql.Date(date.getTime() + 1 * 24 * 60 * 60 * 1000);
		System.out.println("fromDate :"+ fromDt+" To Date: "+toDt +" Type : "+offenseType);
		if(offenseType.equals("tax_upto")) {
			 return vehicleDetailsRepository.findVehicleDetailsListByDateAndTaxUpto(fromDt,toDt);
		}
		else if(offenseType.equals("fit_upto")) {
			 return vehicleDetailsRepository.findVehicleDetailsListByDateAndFitUpto(fromDt,toDt);
		}
		else if(offenseType.equals( "ins_upto")) {
			 return vehicleDetailsRepository.findVehicleDetailsListByDateAndInsUpto(fromDt,toDt);
		}
		else if(offenseType.equals("nonusestat")) {
			 return vehicleDetailsRepository.findVehicleDetailsListByDateAndNonUse(fromDt,toDt);
		}
		else {
			return null;
		}
		
	}

	@Override
	public List<VehicleDetails> addAllVehicles(@Valid List<VehicleDetails> vehicleDetailsList) {
		return vehicleDetailsRepository.saveAll(vehicleDetailsList);
	}
	
	

}
