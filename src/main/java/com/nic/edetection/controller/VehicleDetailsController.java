package com.nic.edetection.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IVehicleDetailsService;
import com.nic.edetection.model.VehicleDetails;

@RestController
@CrossOrigin(origins={"http://localhost:4200","http://192.168.137.77:4200","http://192.168.43.199:4200","https://bibhutibhusana.github.io"})
@RequestMapping("/api/v1")
public class VehicleDetailsController {
@Autowired IVehicleDetailsService vehicleDetailsService;
	
	@GetMapping("/vehicle-details")
	public List<VehicleDetails> getVehicleDetailsList() throws SQLException{
		return vehicleDetailsService.getVehicleDetails();
	}
	@PostMapping("/vehicle-details-by-date")
	public List<VehicleDetails> getVehicleDetailsByDate(@RequestBody String date) throws ParseException{
		return vehicleDetailsService.getVehicleDetailsListByDate(date);
	}
	
	@GetMapping("/vehicle-details/{id}")
	public ResponseEntity<VehicleDetails> getVehicleDetailsById(@Valid @PathVariable(name="id")Long id) throws ResourceNotFoundException{
		return vehicleDetailsService.getVehicleDetailsById(id);
	}
	
	@GetMapping("/vehicle-detail/getByVehicleNo")
	public List<VehicleDetails> getVehicleDetailsListByVehicleNo( ) throws SQLException{
		return vehicleDetailsService.getVehicleDetails();
		//return vehicleDetailsService.getVehicleDetailsByVehicleNo(vehicleNo);
	}
	 
	@GetMapping("/invalid-vehicle-details")
	public List<VehicleDetails> getInvalidVehicleDetails(){
		return vehicleDetailsService.getInvalidVehicleDetails();
	}
	
	@GetMapping("/invalid-vehicle-details-by-type/{offenseType}")
	public List<VehicleDetails> getInvalidVehicleDetailsByType(@Valid @PathVariable(value="offenseType") String offenseType){
		return vehicleDetailsService.getInvalidVehicleByType(offenseType); 
	}
	@PostMapping("/vehicle-details-by-date-type")
	private List<VehicleDetails> getInvalidVehicleDetailsByDateAndType(@Valid @RequestBody Map<String,String> obj) throws ParseException{
		return vehicleDetailsService.getInvalidVehicleListByDateAndType(obj.get("offenseType"),obj.get("tDate"));
	}
	

}
