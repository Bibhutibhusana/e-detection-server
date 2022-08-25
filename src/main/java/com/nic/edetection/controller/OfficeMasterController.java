package com.nic.edetection.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.dto.OfficeMasterDto;
import com.nic.edetection.iservice.IOfficeMasterService;

@RestController
@RequestMapping("/api/v1/")
public class OfficeMasterController {
	@Autowired
	private IOfficeMasterService officeMasterService;
	
	@GetMapping("/officeMasterList")
	private List<OfficeMasterDto> getOfficeMasterList(){
		return officeMasterService.getOfficeMasterList();
	}
	@GetMapping("/officeMasterListByDistrictId/{districtId}")
	private List<OfficeMasterDto> getOfficeMasterListByDistrictId(@Valid @PathVariable(value="districtId") Long districtId){
		return officeMasterService.getOfficeMasterListByDistrictId(districtId);
	}

}
