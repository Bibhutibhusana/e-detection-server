package com.nic.edetection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.dto.UserStatusDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IUserStatusService;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class UserStatusController {
	@Autowired
	private IUserStatusService userStatusService;
	
	@GetMapping("/getUserStatusList")
	private List<UserStatusDto> getUserStatusList(){
		return userStatusService.getUserStatusList();
	}

	@GetMapping("/getUserStatusById")
	private UserStatusDto getUserStatusById(@RequestBody String id) throws ResourceNotFoundException {
		return userStatusService.getUserStatusByid(id);
	}
	
	@GetMapping("/createUserStatus")
	private UserStatusDto createUserStatus(@RequestBody UserStatusDto u) {
		return userStatusService.createUserStatus(u);
	}
	@GetMapping("/getUserStatusByVehicleNo/{vehicleNo}")
	private List<UserStatusDto> getUserStatusListByVehicleNo(@PathVariable(value="vehicleNo")String vehicleNo){
		return userStatusService.getUserStatusByVehicleNo(vehicleNo);
	}
}
