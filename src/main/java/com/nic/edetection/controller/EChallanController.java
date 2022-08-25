package com.nic.edetection.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.edetection.dto.AccusedDto;
import com.nic.edetection.dto.EChallanDto;
import com.nic.edetection.dto.ITMSChallanDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IEChallanService;
import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.iservice.IVehicleDetailsService;

import lombok.Data;


@RestController
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
	private List<ITMSChallanDto> saveAllEChallanAndCallIfms(@Valid @RequestBody List<EChallanDto> echallans) throws Exception{
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
			String resOut =  demoPostRESTAPI(itemsChallan);
//			Response res = (Response) resOut;
			///////////////// Calling ITMS Api for challan issuance
//			String url ="https://staging.parivahan.gov.in/echallann/api/citizen-challan";
//			RestTemplate restTemplate = new RestTemplate();
//			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
//			//Add the Jackson Message converter
//			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//			// Note: here we are making this converter to process any kind of response, 
//			// not only application/*json, which is the default behaviour
//			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));        
//			messageConverters.add(converter);  
//			restTemplate.setMessageConverters(messageConverters); 
//			Response res = restTemplate.postForObject(url,itemsChallan, Response.class);
//			System.out.println(res.message+"  "+res.result.challan_no + "   vehicle_no:"+itemsChallan.getChallan_doc_no());
			System.out.println(resOut);
			Response res = new ObjectMapper().readValue(resOut, Response.class);  
//			System.out.println(res);
			System.out.println("challan_No:"+res.status);
			System.out.println("Challan_link"+res.result.challan_pdf);	
		
			
		}
		}
		
		
		
		
		
//		for(EChallanDto e : echallans) {
//			String uniqueId = e.getChallanTrackId();
//			List<Map<String,Object>> itmsObjectList = vehicleDetailsService.getITMSObjectListByUniqueId(uniqueId); 
//		}
		ResponseEntity<String> res = ResponseEntity.ok().body("success");
		return itmsChallans;
		
		
	}

///////////////////// Function for ITMS api call////////////////////////////
	
	public  String demoPostRESTAPI(ITMSChallanDto itmsChallan) throws Exception 
	{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<ITMSChallanDto> entity = new HttpEntity<ITMSChallanDto>(itmsChallan,headers);
//	      try {
	      return restTemplate.exchange(
	         "https://staging.parivahan.gov.in/echallann/api/citizen-challan", HttpMethod.POST, entity, String.class).getBody();
//	      }
//	      catch(HttpStatusCodeException e) {
//	          return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
//	                  .body(e.getResponseBodyAsString());
//	       }
//		
		
		
	}
//	  DefaultHttpClient httpClient = new DefaultHttpClient();
//	   
//	  
//	   
//	  StringWriter writer = new StringWriter();
//	  JAXBContext jaxbContext = JAXBContext.newInstance(ITMSChallanDto.class);
//	  Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//	  jaxbMarshaller.marshal(itmsChallan, writer);
//	   
//	  try
//	  {
//	    //Define a postRequest request
//	    HttpPost postRequest = new HttpPost("https://staging.parivahan.gov.in/echallann/api/citizen-challan");
//	     
//	    //Set the API media type in http content-type header
//	    postRequest.addHeader("content-type", "application/xml");
//	     
//	    //Set the request post body
//	    StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
//	    postRequest.setEntity(userEntity);
//	      
//	    //Send the request; It will immediately return the response in HttpResponse object if any
//	    org.apache.http.HttpResponse response = httpClient.execute(postRequest);
//	    System.out.println(response.getEntity()+"  "+response.getParams() + "   vehicle_no:"+response.getEntity().getContent());
//	     
//	    //verify the valid error code first
//	    int statusCode = response.getStatusLine().getStatusCode();
//	    if (statusCode != 201) 
//	    {
//	      throw new RuntimeException("Failed with HTTP error code : " + statusCode);
//	    }
//	  }
//	  finally
//	  {
//	    //Important: Close the connect
//	    httpClient.getConnectionManager().shutdown();
//	  }
//	}
	
	
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