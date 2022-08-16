package com.nic.edetection.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.nic.edetection.dto.DistrictMasterDto;
import com.nic.edetection.iservice.IDistrictMasterService;
import com.nic.edetection.repo.DistrictMasterRepo;

public class DistrictMasterService implements IDistrictMasterService {
	@Autowired
	private DistrictMasterRepo districtMasterRepo;

	@Autowired
	private ModelMapper modelMapper;


	@Override
	public List<DistrictMasterDto> getDistrictMasterList() {
		// TODO Auto-generated method stub
		List<DistrictMasterDto> districtMasterList = districtMasterRepo.findAll().stream().map((value) -> modelMapper.map(value,DistrictMasterDto.class)).collect(Collectors.toList());
		return districtMasterList;
	}

	@Override
	public DistrictMasterDto getDistrictMasterByDistrictId(int districtId) {
		// TODO Auto-generated method stub
		DistrictMasterDto districtMaster = districtMasterRepo.findByDistrictId(districtId);
		return districtMaster;
	}

}
