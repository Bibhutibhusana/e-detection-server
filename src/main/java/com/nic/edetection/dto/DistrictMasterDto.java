package com.nic.edetection.dto;

import lombok.Data;

@Data
public class DistrictMasterDto {
	private Long id;
	private int districtId;
	private String districtName;
	private String stateCode;
}
