package com.nic.edetection.livedb.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.livedb.service.VehicleDetailsFromLiveDbService;
import com.nic.edetection.model.VehicleDetails;

@RestController
@CrossOrigin
//(origins={"http://localhost:4200","http://192.168.137.77:4200","http://192.168.43.199:4200","https://bibhutibhusana.github.io"})
@RequestMapping("/api/v1")
public class VehicleDetailsFromLiveDbController {
	@Autowired
	VehicleDetailsFromLiveDbService vehicleDetailsFromLiveDbService;
	
//	@GetMapping("/getVehicleDetailsFromLiveDb/{userId}")
//	public List<VehicleDetails> getVehicleDetailsFromLiveDb(@PathVariable(value="userId")Long userId) throws SQLException {
//		return vehicleDetailsFromLiveDbService.getVehicleDetails(userId);
//	}

}
