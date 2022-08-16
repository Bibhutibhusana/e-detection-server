package com.nic.edetection.controller;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.nic.edetection.dto.AccusedDto;
import com.nic.edetection.dto.EChallanDto;
import com.nic.edetection.dto.ITMSChallanDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IEChallanService;
import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.iservice.IVehicleDetailsService;


@RestController
@CrossOrigin
//(origins={"http://localhost:4200","http://192.168.137.77:4200","http://192.168.43.199:4200","https://bibhutibhusana.github.io","http://localhost:8081","http://localhost"})
@RequestMapping("/api/v1")
public class EChallanController {
	@Autowired
	private IEChallanService eChallanService;
	
	@Autowired private IUserLoginService userLoginService;
	
	@Autowired private IVehicleDetailsService vehicleDetailsService;
	
	@GetMapping("/echallan")
	private List<EChallanDto> getEchallanList(){
		return eChallanService.getEChallanList();
	}
	
	@GetMapping("/echallan/{id}")
	private ResponseEntity<EChallanDto> getEChallanById(@Valid @PathVariable(value="id")Long id) throws ResourceNotFoundException {
		return eChallanService.getEChallanById(id);
	}

	@PostMapping("/echallan")
	private EChallanDto createEChallan(@Valid @RequestBody EChallanDto echallan) {
//		UserLogin u = this.userLoginService.getUserLoginByid(userId);
//		if(echallan.getId() == null || echallan.getId() == 0) {
//			echallan.setCreatedBy(userId);
//			echallan.setCreatedDate(new Date());
//		}
//		else {
//			echallan.setUpdatedBy(userId);
//			echallan.setUpdatedDate(new Date());
//		}
		return eChallanService.createEChallan(echallan);	
	}
	
	@PostMapping("/echallans")
	private List<EChallanDto> saveAllEChallan(@Valid @RequestBody List<EChallanDto> echallans) {
		return eChallanService.saveAllEChallan(echallans);
	}
	
	@PostMapping("/saveEChallanAndCallIfms")
	private List<ITMSChallanDto> saveAllEChallanAndCallIfms(@Valid @RequestBody List<EChallanDto> echallans) throws ParseException{
		try {
		eChallanService.saveAllEChallan(echallans);
		}
		catch(Exception e){
			return null;
		}
		List<String> uniqueIdList = echallans.stream()
                .map(value -> value.getChallanTrackId())
                .collect(Collectors.toList());
		
		List<Map<String,Object>> itemsObjectList = vehicleDetailsService.getITMSObjectListByUniqueId(uniqueIdList); 
		
		List<ITMSChallanDto> itmsChallans = new ArrayList<ITMSChallanDto>();
		if(itemsObjectList.size() != 0) {
		for(Map<String,Object>item: itemsObjectList) {
			AccusedDto accused = new AccusedDto();
			accused.setAcc_type("owner");
			accused.setRemark("Detected through eDetection");
			accused.setAcc_id((String) item.get("challan_doc_no"));
			accused.setAcc_name((String) item.get("acc_name"));
			accused.setAcc_mobile_no((String) item.get("acc_mob_no"));
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
			itemsChallan.setDistrict_id((int)item.get("district_id"));
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
			itemsChallan.setViolation_id(((BigInteger)(item.get("violation_id"))).longValue());
			itemsChallan.setChallan_source_type("eDetection");
			itmsChallans.add(itemsChallan);
		}
		}
		
		
//		for(EChallanDto e : echallans) {
//			String uniqueId = e.getChallanTrackId();
//			List<Map<String,Object>> itmsObjectList = vehicleDetailsService.getITMSObjectListByUniqueId(uniqueId); 
//		}
		ResponseEntity<String> res = ResponseEntity.ok().body("success");
		return itmsChallans;
		
		
	}
	@PostMapping("/echallan-get-by-date")
	private List<EChallanDto> getEChallanListByDate(@Valid @RequestBody Map<String,String> date) throws ParseException{
		List<EChallanDto> challanList =  eChallanService.getEChallanListByDate(date.get("fromDt"),date.get("toDt"));
		//System.out.println(challanList.get(0).getToll_name());
		return challanList;
	}
	@PostMapping("/challanByTransactionDate")
	private List<EChallanDto> getEChallanByTransactionDate(@RequestBody Date transactionDate){
		System.out.println(transactionDate);
		List<EChallanDto> challanList =  eChallanService.getEChallanByTransactionDate(transactionDate);
		return challanList;
	}
	
	@PostMapping("/challanByTransactionDateAndUserId")
	private List<EChallanDto> getEChallanListByTransactionDateAndUserId(@RequestBody Map<String,String> obj) throws ParseException{
		Long id = Long.parseLong(obj.get("userId"));
		//System.out.println(obj.get("transactionDate"));
		List<EChallanDto> challanList =  eChallanService.getAllByTransactionDateAndUserId(obj.get("transactionDate"),id);
		return challanList;
	}
	

	@PostMapping("/challanByOpDateAndUserId")
	private List<EChallanDto> getEChallanListByOpDateAndUserId(@RequestBody Map<String,String> obj) throws ParseException{
		//System.out.println(obj.get("fromDt")+" "+obj.get("toDt")+" "+obj.get("userId"));
		Long id = Long.parseLong(obj.get("userId"));
		
		List<EChallanDto> challanList =  eChallanService.getAllByOpDateBetweenAndUserId(obj.get("fromDt"),obj.get("toDt"),id);
		//System.out.println(challanList.size());
		return challanList;
	}
	
	@PostMapping("/issued-challan-list")
	private List<EChallanDto> getIssuedChallanList(@Valid @RequestBody Map<String,String> date) throws ParseException {
		return eChallanService.getIssuedChallanListByDate(date.get("fromDt"),date.get("toDt"));
	}
	
	@GetMapping("/totalChallanActioned")
	private Long getTotalActionedChallans() {
		return eChallanService.getTotalActionedChallans();
	}
	
	@GetMapping("/totalChallanIssued")
	private Long getTotalChallanIssued() {
		return eChallanService.getTotalIssuedChallans();
	}
	


}
