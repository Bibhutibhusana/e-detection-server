package com.nic.edetection.iservice;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nic.edetection.dto.EChallanDto;
import com.nic.edetection.dto.ITMSChallanDto;
import com.nic.edetection.exception.ResourceNotFoundException;

@Service
@Transactional
public interface IEChallanService {
	public List<EChallanDto> getEChallanList();
	public ResponseEntity<EChallanDto> getEChallanById(Long id) throws ResourceNotFoundException;
	public EChallanDto createEChallan(EChallanDto echallan);
	public List<EChallanDto> saveAllEChallan(List<EChallanDto> echallans);
	public List<EChallanDto> getEChallanListByDate(String fromDt, String toDt) throws ParseException;
	public List<EChallanDto> getIssuedChallanListByDate(String fromDt, String toDt) throws ParseException;
	public Long getTotalActionedChallans();
	public Long getTotalIssuedChallans();
	public List<EChallanDto> getEChallanByTransactionDate(Date transactionDate);
	public List<EChallanDto> getAllByOpDateBetweenAndUserId(String fromDt, String toDt,Long id) throws ParseException;
	public List<EChallanDto> getAllByTransactionDateAndUserId(String transactionDate, Long id) throws ParseException;
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<String> demoPostRESTAPI(List<String> itemsChallan) throws RestClientException, InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException;

	

}
