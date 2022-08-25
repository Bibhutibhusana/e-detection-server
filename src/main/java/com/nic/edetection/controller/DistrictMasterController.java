package com.nic.edetection.controller; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.dto.DistrictMasterDto;
import com.nic.edetection.iservice.IDistrictMasterService;

@RestController
@RequestMapping("/api/v1/")
public class DistrictMasterController {
	@Autowired
	private IDistrictMasterService districtMasterService;
	
	@GetMapping("/districtMasterList")
	private List<DistrictMasterDto> getDistrictMasterList(){
		return districtMasterService.getDistrictMasterList();
	}
	@GetMapping("/districtMasterListByStateCode/{stateCd}")
	private List<DistrictMasterDto> getDistrictMasterListByStateCode(@PathVariable(value="stateCd") String stateCd){
		return districtMasterService.getDistrictMasterListByStateCode(stateCd);
	}

}