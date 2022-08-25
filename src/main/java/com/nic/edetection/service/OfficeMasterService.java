package com.nic.edetection.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.OfficeMasterDto;
import com.nic.edetection.iservice.IOfficeMasterService;
import com.nic.edetection.repo.OfficeMasterRepo;

@Service
public class OfficeMasterService implements IOfficeMasterService {
	@Autowired
	private OfficeMasterRepo officeMasterRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<OfficeMasterDto> getOfficeMasterList() {
		// TODO Auto-generated method stub
		return officeMasterRepo.findAll().stream().map(value -> modelMapper.map(value, OfficeMasterDto.class)).collect(Collectors.toList());
	}
	@Override
	public List<OfficeMasterDto> getOfficeMasterListByDistrictId(Long districtId){
		return officeMasterRepo.findByDistId(districtId).stream().map(value -> modelMapper.map(value, OfficeMasterDto.class)).collect(Collectors.toList());
	}
	

}
