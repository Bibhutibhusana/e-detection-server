package com.nic.edetection.iservice;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.VehicleDetailsDto;
import com.nic.edetection.exception.ResourceNotFoundException;


@Service
@Transactional
public interface IVehicleDetailsService {
	public List<VehicleDetailsDto> getVehicleDetails() throws SQLException;

	public ResponseEntity<VehicleDetailsDto> getVehicleDetailsById(@Valid Long id) throws ResourceNotFoundException;
	public List<Map<String, String>> getVehicleDetailsByVehicleNo(String vehicleNo);
	public VehicleDetailsDto createVehicleDetails(@Valid VehicleDetailsDto vehicleDetails);
	public List<VehicleDetailsDto> addAllVehicles(@Valid List<VehicleDetailsDto> vehicleDetailsList);
	public List<VehicleDetailsDto> getInvalidVehicleDetails();
	public List<VehicleDetailsDto> getInvalidVehicleByType(String offenseType);
	public List<VehicleDetailsDto> getVehicleDetailsListByDate(String date) throws ParseException;

	public List<VehicleDetailsDto> getInvalidVehicleListByDateAndType(String offenseType, String date) throws ParseException;

	public List<VehicleDetailsDto> getInvalidVehicleByDateAndClassComparision(String date) throws ParseException;
	public long getInvalidVehiclesNumber();
	public VehicleDetailsDto updateVehicleDetails(Long id, Long userid) throws ResourceNotFoundException;

	public List<Map<String, Object>> getITMSObjectListByUniqueId(List<String> uniqueIdList);

}
 