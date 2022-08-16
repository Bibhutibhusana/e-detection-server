package com.nic.edetection.iservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nic.edetection.dto.OffenceMasterDto;
import com.nic.edetection.exception.ResourceNotFoundException;

@Service
public interface IOffenceMasterService {
	public List<OffenceMasterDto> getOffenceMasterList();
	public OffenceMasterDto getOffenceMasterById(long id) throws ResourceNotFoundException;
	
}
