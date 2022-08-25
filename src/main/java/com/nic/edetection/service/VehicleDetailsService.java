package com.nic.edetection.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.UserStatusDto;
import com.nic.edetection.dto.VehicleDetailsDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IUserStatusService;
import com.nic.edetection.iservice.IVehicleDetailsService;
import com.nic.edetection.model.VehicleDetails;
import com.nic.edetection.repo.VehicleDetailsRepository;

@Service
@Transactional
public class VehicleDetailsService implements IVehicleDetailsService {

	@Autowired
	VehicleDetailsRepository vehicleDetailsRepository;
	@Autowired
	IUserStatusService userStatusService;
	
	@Autowired ModelMapper modelMapper;

	@Override
	public List<VehicleDetailsDto> getVehicleDetails() {
//		VehicleDetailsFromLiveDbService vd = new VehicleDetailsFromLiveDbService();
//		try {
//			vd.getVehicleDetails(userId);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return vehicleDetailsRepository.findAll().stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<VehicleDetailsDto> getVehicleDetailsById(@Valid Long id) throws ResourceNotFoundException {
		VehicleDetails vehicleDetails = vehicleDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("VehicleDetails not found for this id::" + id));
		return ResponseEntity.ok().body(modelMapper.map(vehicleDetails, VehicleDetailsDto.class));
	}

	/// This is to be omitted.
	public List<Map<String, String>> getVehicleDetailsByVehicleNo(String vehicleNo) {

		return vehicleDetailsRepository.getVehicleDetailsListByVehicleNo(vehicleNo);
	}

	@Override
	public VehicleDetailsDto createVehicleDetails(@Valid VehicleDetailsDto vehicleDetails) {

		VehicleDetails vehicleDetail = modelMapper.map(vehicleDetails, VehicleDetails.class);
		return modelMapper.map(vehicleDetailsRepository.save(vehicleDetail), VehicleDetailsDto.class);
	}

	public List<VehicleDetailsDto> getInvalidVehicleDetails() {
		return vehicleDetailsRepository.findInvalidVehicleList().stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<VehicleDetailsDto> getInvalidVehicleByType(String offenseType) {

		if (offenseType.equals("tax_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByTaxUpto(offenseType, offenseType).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} else if (offenseType.equals("fit_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByFitUpto(offenseType, offenseType).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} else if (offenseType.equals("ins_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByInsUpto(offenseType, offenseType).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} else if (offenseType.equals("nonusestat")) {
			return vehicleDetailsRepository.findInvalidVehiclesByNonUse(offenseType, offenseType).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} 
		else if (offenseType.equals("puc_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByPucUpto(offenseType, offenseType).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} 
		else if (offenseType.equals("permit_upto")) {
			return vehicleDetailsRepository.findInvalidVehiclesByPermitUpto(offenseType, offenseType).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} 
		else {
			return null;
		}
		
	}

	public List<VehicleDetailsDto> getVehicleDetailsListByDate(String transactionDate) throws ParseException {

		java.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(transactionDate);
		java.sql.Date fromDt = new java.sql.Date(date.getTime());
		java.sql.Date toDt;
		toDt = new java.sql.Date(date.getTime() + 1 * 24 * 60 * 60 * 1000);
		// System.out.println("From Date : "+ fromDt+" and ToDate: "+toDt);
		return vehicleDetailsRepository.findVehicleDetailsListByDate(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<VehicleDetailsDto> getInvalidVehicleListByDateAndType(String offenseType, String transactionDate)
			throws ParseException {

		java.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(transactionDate);
		java.sql.Date fromDt = new java.sql.Date(date.getTime());
		java.sql.Date toDt;
		toDt = new java.sql.Date(date.getTime() + 1 * 24 * 60 * 60 * 1000);
		// System.out.println("fromDate :"+ fromDt+" To Date: "+toDt +" Type :
		// "+offenseType);
		if (offenseType.equals("tax_upto")) {
			return vehicleDetailsRepository.findVehicleDetailsListByDateAndTaxUpto(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} else if (offenseType.equals("fit_upto")) {
			return vehicleDetailsRepository.findVehicleDetailsListByDateAndFitUpto(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} else if (offenseType.equals("ins_upto")) {
			return vehicleDetailsRepository.findVehicleDetailsListByDateAndInsUpto(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		} else if (offenseType.equals("nonusestat")) {
			return vehicleDetailsRepository.findVehicleDetailsListByDateAndNonUse(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
		}
		 else if (offenseType.equals("puc_upto")) {
				return vehicleDetailsRepository.findVehicleDetailsListByDateAndPucUpto(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
			}
		 else if (offenseType.equals("permit_upto")) {
				return vehicleDetailsRepository.findVehicleDetailsListByDateAndPermitUpto(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
			}else {
			return null;
		}

	}

	@Override
	public List<VehicleDetailsDto> addAllVehicles(@Valid List<VehicleDetailsDto> vehicleDetailsList) {
		List<VehicleDetails> vehicleList = vehicleDetailsList.stream().map(value -> modelMapper.map(value, VehicleDetails.class)).collect(Collectors.toList());
		return vehicleDetailsRepository.saveAll(vehicleList).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<VehicleDetailsDto> getInvalidVehicleByDateAndClassComparision(String transactionDate)
			throws ParseException {
		java.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(transactionDate);
		java.sql.Date fromDt = new java.sql.Date(date.getTime());
		java.sql.Date toDt;
		toDt = new java.sql.Date(date.getTime() + 1 * 24 * 60 * 60 * 1000);

		return vehicleDetailsRepository.findAllByDateAndClassComparision(fromDt, toDt).stream().map(value -> modelMapper.map(value, VehicleDetailsDto.class)).collect(Collectors.toList());
	}

	@Override
	public long getInvalidVehiclesNumber() {
		return vehicleDetailsRepository.countInvalidVehicles();
	}

	@Override
	public VehicleDetailsDto updateVehicleDetails(Long id, Long userid) throws ResourceNotFoundException {
		Optional<VehicleDetails> v = vehicleDetailsRepository.findById(id);

		VehicleDetails vehDtls = v.get();
		vehDtls.setIsChallanIssued(Boolean.TRUE);
		UserStatusDto uStatus = new UserStatusDto();
		try {
			uStatus = userStatusService.getUserStatusByid(vehDtls.getUniqueId());
		} catch (Exception e) {
			// System.out.println("Erorr is "+e);
		}

		uStatus.setChallanIssueStatus("y");
		uStatus.setChallanIssueStatusDate(new Date());
		uStatus.setUpdatedBy(userid);
		uStatus.setUpdatedDate(new Date());
		return modelMapper.map(vehicleDetailsRepository.save(vehDtls), VehicleDetailsDto.class);
	}

	@Override
	public List<Map<String, Object>> getITMSObjectListByUniqueId(List<String> uniqueIds) {
		return vehicleDetailsRepository.findITMSObjectListByUniqueId(uniqueIds);
	}

}
