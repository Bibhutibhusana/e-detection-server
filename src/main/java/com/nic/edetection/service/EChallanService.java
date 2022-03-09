package com.nic.edetection.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IEChallanService;
import com.nic.edetection.model.EChallan;
import com.nic.edetection.repo.EChallanRepo;

@Service
@Transactional
public class EChallanService implements IEChallanService{
	@Autowired 
	private EChallanRepo eChallanRepo;

	@Override
	public List<EChallan> getEChallanList() {
		// TODO Auto-generated method stub
		return eChallanRepo.findAll();
	}

	@Override
	public  ResponseEntity<EChallan> getEChallanById(Long id) throws ResourceNotFoundException {
		EChallan echallanDetails = eChallanRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("EChallan not found for this id::"+id));
		// TODO Auto-generated method stub
		return ResponseEntity.ok().body(echallanDetails);
	}

	@Override
	public EChallan createEChallan(EChallan echallan) {
		// TODO Auto-generated method stub
		return eChallanRepo.save(echallan);
	}

	@Override
	public List<EChallan> saveAllEChallan(List<EChallan> echallans) {
		return eChallanRepo.saveAll(echallans);
	}

	public List<EChallan> getEChallanListByDate(String fromDt, String toDt) throws ParseException {
		java.util.Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDt);
		java.util.Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDt);
		java.sql.Date fd= new java.sql.Date(fromDate.getTime());
		java.sql.Date td = new java.sql.Date(toDate.getTime()+ 1 * 24 * 60 * 60 * 1000);
		System.out.println("FromDate "+fd+" toDate: "+td);
		return eChallanRepo.findAllByOpDateBetweenGroupByIdAndTransactionDate(fd,td);
	}

	@Override
	public List<EChallan> getIssuedChallanListByDate(String fromDt, String toDt) throws ParseException {
		java.util.Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDt);
		java.util.Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDt);
		java.sql.Date fd= new java.sql.Date(fromDate.getTime());
		java.sql.Date td = new java.sql.Date(toDate.getTime()+ 1 * 24 * 60 * 60 * 1000);
		System.out.println("FromDate "+fd+" toDate: "+td);
		return eChallanRepo.findAllIssuedChallanByDate(fd,td);
	}

}
