package com.nic.edetection.iservice;

import java.util.List;

import com.nic.edetection.dto.DistrictMasterDto;


public interface IDistrictMasterService {
	public List<DistrictMasterDto> getDistrictMasterList();
	public DistrictMasterDto getDistrictMasterByDistrictId(int districtId);
	public List<DistrictMasterDto> getDistrictMasterListByStateCode(String stateCd);

}
