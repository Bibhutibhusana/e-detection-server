package com.nic.edetection.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.edetection.dto.AccusedDto;
import com.nic.edetection.dto.EChallanDto;
import com.nic.edetection.dto.ITMSChallanDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IEChallanService;
import com.nic.edetection.model.EChallan;
import com.nic.edetection.repo.EChallanRepo;

import lombok.Data;

@Service
@Transactional
public class EChallanService implements IEChallanService{
	@Autowired 
	private EChallanRepo eChallanRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private VehicleDetailsService vehicleDetailsService;
	
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



	
	@Async("threadPoolTaskExecutor")
	public  CompletableFuture<String> demoPostRESTAPI(List<String> uniqueIdList) throws JsonMappingException, JsonProcessingException 
	{
		
List<Map<String,Object>> itemsObjectList = vehicleDetailsService.getITMSObjectListByUniqueId(uniqueIdList); 
		
		List<ITMSChallanDto> itmsChallans = new ArrayList<ITMSChallanDto>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		List<Response> responseList=new ArrayList<Response>();
		if(itemsObjectList.size() != 0) {
		for(Map<String,Object>item: itemsObjectList) {
			AccusedDto accused = new AccusedDto();
			accused.setAcc_type("owner");
			accused.setRemark("Detected through eDetection");
			accused.setAcc_id((String) item.get("challan_doc_no"));
			accused.setAcc_name((String) item.get("acc_name"));
			accused.setAcc_mob_no((String) item.get("acc_mob_no"));
			accused.setAcc_father((String) item.get("acc_father"));
			String cadd = (String)item.get("c_add1") +", "+(String)item.get("c_add2")+", "+(String)item.get("c_district")+", "+(String)item.get("c_state")+", "+(String)item.get("c_pincode");
			String padd = (String)item.get("p_add1") +", "+(String)item.get("p_add2")+", "+(String)item.get("p_district")+", "+(String)item.get("p_state")+", "+(String)item.get("p_pincode");
			accused.setAcc_address(cadd);
			accused.setPermanent_address(padd);
			accused.setAcc_img("");
			accused.setCctv_image_1("");
			accused.setCctv_image_2("");
			ITMSChallanDto itemsChallan = new ITMSChallanDto();
			itemsChallan.setChallan_amt((int)item.get("challan_amt"));
			itemsChallan.setOffence_id(item.get("offence_id").toString());
			itemsChallan.setSpeed_limit(0);
			itemsChallan.setActual_speed(0);
			itemsChallan.setDistrict_id(item.get("district_id").toString());
			Date challanDate = (Date)item.get("challan_time");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String chDate = sdf.format(challanDate);
			
			itemsChallan.setChallan_time(chDate);
			itemsChallan.setChallan_address((String) item.get("challan_address")+" Toll Gate");	
			itemsChallan.setLat((String)item.get("lat"));
			itemsChallan.setLongitude((String) item.get("long"));
			itemsChallan.setChallan_doc_no((String) item.get("challan_doc_no"));
			itemsChallan.setAcc_vehicle_class((String) item.get("acc_vehicle_class"));
			itemsChallan.setAccused_type("owner");
			Map<String,AccusedDto> accused1 = new HashMap<>();
			accused1.put("owner", accused);
			itemsChallan.setAccused(accused1);
			itemsChallan.setDl_number("NO DL");
			String[] imgList = {"","",""};
			itemsChallan.setChallan_vehicle_img(imgList);
			itemsChallan.setType(7);
			itemsChallan.setViolation_id(item.get("violation_id").toString());
			itemsChallan.setChallan_source_type("eDetection"); 
			
			itmsChallans.add(itemsChallan);
			
		

	      HttpEntity<ITMSChallanDto> entity = new HttpEntity<ITMSChallanDto>(itemsChallan,headers);
//	      try {
	      ResponseEntity<String> res =  restTemplate.exchange(
	         "https://staging.parivahan.gov.in/echallann/api/citizen-challan", HttpMethod.POST, entity, String.class);
	      System.out.println("result is"+res);
	      Response resOut = new ObjectMapper().readValue(res.getBody(), Response.class); 
	      
	    eChallanRepo.updateChallanDetailsById(resOut.result.challan_no,Long.valueOf(itemsChallan.getViolation_id()));
//	      Optional<EChallan> eChallanToUpdate = eChallanRepo.findById(Long.valueOf(itemsChallan.getViolation_id()));
//	      EChallan echallan = eChallanToUpdate.get();
//	      echallan.setChallanNo(resOut.result.challan_no);
//	      echallan.setChallanIssueDate(new Date());
//	      eChallanRepo.save(echallan);
	      
	      responseList.add(resOut);
	    
//	      }
//	      catch(HttpStatusCodeException e) {
//	          return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
//	                  .body(e.getResponseBodyAsString());
//	       }
//		
 		}
		
		}
 		return CompletableFuture.completedFuture("Success");
	}
//	  DefaultHttpClient httpClient = new DefaultHttpClient();
//	   


}


@Data
class Response implements Serializable {
	String status;
	String message;
	result result;
}
@Data
class result implements Serializable{
	 String challan_no;
	 String challan_pdf;
}
