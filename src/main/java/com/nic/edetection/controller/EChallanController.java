package com.nic.edetection.controller;

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
import com.nic.edetection.iservice.IEChallanService;
import com.nic.edetection.model.EChallan;
import com.nic.edetection.service.EChallanService;

@RestController
@CrossOrigin(origins={"http://localhost:4200","http://192.168.137.77:4200","http://192.168.43.199:4200","https://bibhutibhusana.github.io"})
@RequestMapping("/api/v1")
public class EChallanController {
	@Autowired
	private IEChallanService eChallanService;
	
	@GetMapping("/echallan")
	private List<EChallan> getEchallanList(){
		return eChallanService.getEChallanList();
	}
	
	@GetMapping("/echallan/{id}")
	private ResponseEntity<EChallan> getEChallanById(@Valid @PathVariable(value="id")Long id) throws ResourceNotFoundException {
		return eChallanService.getEChallanById(id);
	}

	@PostMapping("/echallan")
	private EChallan createEChallan(@Valid @RequestBody EChallan echallan) {
		return eChallanService.createEChallan(echallan);	
	}
	
	@PostMapping("/echallans")
	private List<EChallan> saveAllEChallan(@Valid @RequestBody List<EChallan> echallans) {
		return eChallanService.saveAllEChallan(echallans);
	}
	
	@PostMapping("/echallan-get-by-date")
	private List<EChallan> getEChallanListByDate(@Valid @RequestBody Map<String,String> date) throws ParseException{
		return eChallanService.getEChallanListByDate(date.get("fromDt"),date.get("toDt"));
	}
	
	@PostMapping("/issued-challan-list")
	private List<EChallan> getIssuedChallanList(@Valid @RequestBody Map<String,String> date) throws ParseException {
		return eChallanService.getIssuedChallanListByDate(date.get("fromDt"),date.get("toDt"));
	}
	

}
