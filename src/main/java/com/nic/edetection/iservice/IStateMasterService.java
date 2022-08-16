package com.nic.edetection.iservice;

import java.util.List;

import com.nic.edetection.dto.StateMasterDto;

public interface IStateMasterService {
	public List<StateMasterDto> getAllStateMasterDtoList();
	public StateMasterDto getStateMasterByStateCode(String stateCode);

}
