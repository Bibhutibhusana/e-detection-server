package com.nic.edetection.iservice;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.model.VehicleDetails;

@Service
@Transactional
public interface IVehicleDetailsService {
	public List<VehicleDetails> getVehicleDetails() throws SQLException;

	public ResponseEntity<VehicleDetails> getVehicleDetailsById(@Valid Long id) throws ResourceNotFoundException;
	public List<Map<String, String>> getVehicleDetailsByVehicleNo(String vehicleNo);
	public VehicleDetails createVehicleDetails(@Valid VehicleDetails vehicleDetails);
	public List<VehicleDetails> addAllVehicles(@Valid List<VehicleDetails> vehicleDetailsList);
	public List<VehicleDetails> getInvalidVehicleDetails();
	public List<VehicleDetails> getInvalidVehicleByType(String offenseType);
	public List<VehicleDetails> getVehicleDetailsListByDate(String date) throws ParseException;

	public List<VehicleDetails> getInvalidVehicleListByDateAndType(String offenseType, String date) throws ParseException;
 
}
