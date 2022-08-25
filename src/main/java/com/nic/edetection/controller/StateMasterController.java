package com.nic.edetection.controller; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.dto.StateMasterDto;
import com.nic.edetection.iservice.IStateMasterService;

@RestController
@RequestMapping("/api/v1/")
public class StateMasterController {
	@Autowired
	private IStateMasterService stateMasterService;
	
	@GetMapping("/stateMasterList")
	private List<StateMasterDto> getStateMasterList(){
		return stateMasterService.getAllStateMasterDtoList();
	}

}