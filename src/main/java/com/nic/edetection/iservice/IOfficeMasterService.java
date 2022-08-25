package com.nic.edetection.iservice;

import java.util.List;

import com.nic.edetection.dto.OfficeMasterDto;

public interface IOfficeMasterService {
	public List<OfficeMasterDto> getOfficeMasterList();

	public List<OfficeMasterDto> getOfficeMasterListByDistrictId(Long districtId);
}
