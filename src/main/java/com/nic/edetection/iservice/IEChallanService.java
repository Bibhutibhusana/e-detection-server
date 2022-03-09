package com.nic.edetection.iservice;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.model.EChallan;

@Service
@Transactional
public interface IEChallanService {
	public List<EChallan> getEChallanList();
	public ResponseEntity<EChallan> getEChallanById(Long id) throws ResourceNotFoundException;
	public EChallan createEChallan(EChallan echallan);
	public List<EChallan> saveAllEChallan(List<EChallan> echallans);
	public List<EChallan> getEChallanListByDate(String fromDt, String toDt) throws ParseException;
	public List<EChallan> getIssuedChallanListByDate(String fromDt, String toDt) throws ParseException;
	
	

}
