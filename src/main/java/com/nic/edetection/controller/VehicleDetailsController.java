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

import com.nic.edetection.dto.VehicleDetailsDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IVehicleDetailsService;
import com.nic.edetection.livedb.service.VehicleDetailsFromLiveDbService;

@RestController
@RequestMapping("/api/v1")
public class VehicleDetailsController {
@Autowired IVehicleDetailsService vehicleDetailsService;
@Autowired VehicleDetailsFromLiveDbService vd;
	
	@GetMapping("/vehicle-details")
	public List<VehicleDetailsDto> getVehicleDetailsList() throws SQLException{
		return vehicleDetailsService.getVehicleDetails();
	}
	@SuppressWarnings("finally")
	@PostMapping("/vehicle-details-by-date/{userId}")
	public List<VehicleDetailsDto> getVehicleDetailsByDate(@RequestBody String date, @PathVariable(value="userId")Long userId) throws ParseException{
//		System.out.println("method ");
		try {
			vd.getVehicleDetails(userId,date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return vehicleDetailsService.getVehicleDetailsListByDate(date);
		}
		
	}
	
	@PostMapping("/vehicle-details-by-date-class-comparision")
	public List<VehicleDetailsDto> getVehicleDetailsByDateAndClassComparision(@RequestBody String date) throws ParseException{
		return vehicleDetailsService.getInvalidVehicleByDateAndClassComparision(date);
	}
	
	@GetMapping("/vehicle-details/{id}")
	public ResponseEntity<VehicleDetailsDto> getVehicleDetailsById(@Valid @PathVariable(name="id")Long id) throws ResourceNotFoundException{
		return vehicleDetailsService.getVehicleDetailsById(id);
	}
	
	@GetMapping("/vehicle-detail/getByVehicleNo")
	public List<VehicleDetailsDto> getVehicleDetailsListByVehicleNo( ) throws SQLException{
		return vehicleDetailsService.getVehicleDetails();
		//return vehicleDetailsService.getVehicleDetailsByVehicleNo(vehicleNo);
	}
	 
	@GetMapping("/invalid-vehicle-details")
	public List<VehicleDetailsDto> getInvalidVehicleDetails(){
		return vehicleDetailsService.getInvalidVehicleDetails();
	}
	
	@GetMapping("/invalid-vehicle-details-by-type/{offenseType}")
	public List<VehicleDetailsDto> getInvalidVehicleDetailsByType(@Valid @PathVariable(value="offenseType") String offenseType){
		return vehicleDetailsService.getInvalidVehicleByType(offenseType); 
	}
	@PostMapping("/vehicle-details-by-date-type")
	private List<VehicleDetailsDto> getInvalidVehicleDetailsByDateAndType(@Valid @RequestBody Map<String,String> obj) throws ParseException{
		return vehicleDetailsService.getInvalidVehicleListByDateAndType(obj.get("offenseType"),obj.get("tDate"));
	}
	
	@PostMapping("/vehicleDetailsUpdateById")
	private VehicleDetailsDto updateVehicleDetailsByDate(@Valid @RequestBody Map<String,Long> obj) throws ResourceNotFoundException {
		//System.out.println("Method called");
		return vehicleDetailsService.updateVehicleDetails(obj.get("id"),obj.get("userid"));
	}
	
	@GetMapping("/vehicle-details-count")
	private long getInvalidVehicleDetailsCountI() {
		return vehicleDetailsService.getInvalidVehiclesNumber();
	}
	

}
