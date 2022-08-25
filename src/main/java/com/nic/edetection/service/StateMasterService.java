package com.nic.edetection.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.StateMasterDto;
import com.nic.edetection.iservice.IStateMasterService;
import com.nic.edetection.repo.StateMasterRepo;
@Service
public class StateMasterService implements IStateMasterService{
	@Autowired
	private StateMasterRepo stateMasterRepo;
	
	@Autowired private ModelMapper modelMapper;
	@Override
	public List<StateMasterDto> getAllStateMasterDtoList() {
		// TODO Auto-generated method stub
		List<StateMasterDto> stateList = stateMasterRepo.findAll().stream().map((value) -> modelMapper.map(value,StateMasterDto.class)).collect(Collectors.toList());
		return stateList;
	}

	@Override
	public StateMasterDto getStateMasterByStateCode(String stateCode) {
		// TODO Auto-generated method stub
		StateMasterDto stateMaster = stateMasterRepo.findByStateCode(stateCode);
		return stateMaster;
	}

}
