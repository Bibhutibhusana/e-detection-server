package com.nic.edetection.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.dto.OffenceMasterDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IOffenceMasterService;
import com.nic.edetection.model.OffenceMaster;

@RestController
@RequestMapping("/api/v1")
public class OffenceMasterController {
	@Autowired
	private ModelMapper modelMapper;
	
	private IOffenceMasterService offenceMasterService;
	
	public OffenceMasterController( IOffenceMasterService offenceMasterService) {
		super();
		this.offenceMasterService = offenceMasterService;
	}
	@GetMapping("/getOffenceMasterList")
	public List<OffenceMasterDto> getOffenceMasterList(){
		return offenceMasterService.getOffenceMasterList().stream().map(post -> modelMapper.map(post, OffenceMasterDto.class))
				.collect(Collectors.toList());
	}
	@GetMapping("/getOffenceById/{id}")
	public ResponseEntity<OffenceMasterDto> getOffenceMasterById(@PathVariable(value ="id") Long id) throws ResourceNotFoundException {
		OffenceMasterDto offenceMaster =  offenceMasterService.getOffenceMasterById(id);
		return ResponseEntity.ok().body(offenceMaster);
	}

}
