package com.nic.edetection.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.EChallanDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IEChallanService;
import com.nic.edetection.model.EChallan;
import com.nic.edetection.repo.EChallanRepo;

@Service
@Transactional
public class EChallanService implements IEChallanService{
	@Autowired 
	private EChallanRepo eChallanRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	@Autowired
//	private ListUtility listUtility;
	

	@Override
	public List<EChallanDto> getEChallanList() {
		// TODO Auto-generated method stub
		List<EChallan> echallans = eChallanRepo.findAll();
		List<EChallanDto> echallanDtos = echallans.stream().map(echallan -> modelMapper.map(echallan, EChallanDto.class)).collect(Collectors.toList());
		return echallanDtos;
	}

	@Override
	public  ResponseEntity<EChallanDto> getEChallanById(Long id) throws ResourceNotFoundException {
		EChallan echallanDetails = eChallanRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("EChallan not found for this id::"+id));
		// TODO Auto-generated method stub
		EChallanDto echallanDto = modelMapper.map(echallanDetails, EChallanDto.class);
		return ResponseEntity.ok().body(echallanDto);
	}

	@Override
	public EChallanDto createEChallan(EChallanDto echallan) {
		// TODO Auto-generated method stub
		EChallan echallanToSave = modelMapper.map(echallan,EChallan.class);
		 echallanToSave = eChallanRepo.save(echallanToSave);
		return modelMapper.map(echallanToSave, EChallanDto.class);
	}

	@Override
	public List<EChallanDto> saveAllEChallan(List<EChallanDto> echallans) {
		List<EChallan> echallanList = echallans.stream().map(echallan -> modelMapper.map(echallan, EChallan.class)).collect(Collectors.toList());
		List<EChallanDto> eChallanToSend=  eChallanRepo.saveAll(echallanList).stream().map(echallan -> modelMapper.map(echallan, EChallanDto.class)).collect(Collectors.toList());
		return eChallanToSend;
	}

	public List<EChallanDto> getEChallanListByDate(String fromDt, String toDt) throws ParseException {
		java.util.Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDt);
		java.util.Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDt);
		java.sql.Date fd= new java.sql.Date(fromDate.getTime());
		java.sql.Date td = new java.sql.Date(toDate.getTime()+ 1 * 24 * 60 * 60 * 1000);
		 List<EChallanDto> echallanDtos =  eChallanRepo.findAllByOpDateBetweenGroupByIdAndTransactionDate(fd,td).stream().map(val -> modelMapper.map(val, EChallanDto.class)).collect(Collectors.toList());
		 return echallanDtos;
	}

	@Override
	public List<EChallanDto> getIssuedChallanListByDate(String fromDt, String toDt) throws ParseException {
		java.util.Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDt);
		java.util.Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDt);
		java.sql.Date fd= new java.sql.Date(fromDate.getTime());
		java.sql.Date td = new java.sql.Date(toDate.getTime()+ 1 * 24 * 60 * 60 * 1000);
		//System.out.println("FromDate "+fd+" toDate: "+td);
		return eChallanRepo.findAllIssuedChallanByDate(fd,td).stream().map(val -> modelMapper.map(val,EChallanDto.class)).collect(Collectors.toList());
	}
	@Override
	public Long getTotalActionedChallans() {
		return eChallanRepo.getTotalInwardChallans();
	}
	@Override
	public Long getTotalIssuedChallans() {
		return eChallanRepo.getTotalIssuedChallans();
	}

	@Override
	public List<EChallanDto> getEChallanByTransactionDate(java.util.Date transactionDate) {
		// TODO Auto-generated method stub
		java.sql.Date fd= new java.sql.Date(transactionDate.getTime());
		java.sql.Date td = new java.sql.Date(transactionDate.getTime()+ 1 * 24 * 60 * 60 * 1000);
		return eChallanRepo.findAllByTransactionDateBetweenAndChallanNoIsNull(fd,td).stream().map(value -> modelMapper.map(value, EChallanDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<EChallanDto> getAllByOpDateBetweenAndUserId(String fromDt, String toDt, Long id) throws ParseException {
		
		java.util.Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDt);
		java.util.Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(toDt);
		java.sql.Date fd= new java.sql.Date(fromDate.getTime());
		java.sql.Date td = new java.sql.Date(toDate.getTime()+ 1 * 24 * 60 * 60 * 1000);
		//System.out.println("FromDate "+fd+" toDate: "+id);
		return eChallanRepo.findAllByOpDateBetweenAndUserId(fd,td,id).stream().map(value -> modelMapper.map(value, EChallanDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<EChallanDto> getAllByTransactionDateAndUserId(String transactionDate, Long id) throws ParseException {
		java.util.Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(transactionDate);
		java.sql.Date fd= new java.sql.Date(fromDate.getTime());
		java.sql.Date td = new java.sql.Date(fromDate.getTime()+ 1 * 24 * 60 * 60 * 1000);
		System.out.println(transactionDate+" "+td+" "+id);
		return eChallanRepo.findAllByTransactionDateAndUserId(fd,td,id).stream().map(value -> modelMapper.map(value, EChallanDto.class)).collect(Collectors.toList());
	}



}
