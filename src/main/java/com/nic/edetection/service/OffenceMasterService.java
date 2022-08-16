package com.nic.edetection.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.OffenceMasterDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.iservice.IOffenceMasterService;
import com.nic.edetection.model.OffenceMaster;
import com.nic.edetection.repo.OffenceMasterRepo;

@Service
public class OffenceMasterService implements IOffenceMasterService{

	@Autowired OffenceMasterRepo offenceMasterRepo;
	@Autowired ModelMapper modelMapper;
	@Override
	public List<OffenceMasterDto> getOffenceMasterList() {
		// TODO Auto-generated method stub
		return offenceMasterRepo.findAll().stream().map(value -> modelMapper.map(value, OffenceMasterDto.class)).collect(Collectors.toList());
	}

	@Override
	public OffenceMasterDto getOffenceMasterById(long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		OffenceMaster offenceMaster  = offenceMasterRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Offence not exist with id :" + id));
		return modelMapper.map(offenceMaster, OffenceMasterDto.class);
	}

}
