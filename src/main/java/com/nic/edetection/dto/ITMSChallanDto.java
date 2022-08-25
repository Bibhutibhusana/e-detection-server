package com.nic.edetection.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ITMSChallanDto {
	@JsonProperty(value="challan_amt")
	private int challan_amt;
	@JsonProperty(value="offence_id")
	private String offence_id;

	@JsonProperty(value="speed_limit")
	private int speed_limit;
	@JsonProperty(value="actual_speed")
	private int actual_speed;
	@JsonProperty(value="district_id")
	private String district_id;
	@JsonProperty(value="challan_time")
	private String challan_time;
	@JsonProperty(value="challan_address")
	private String challan_address;
	@JsonProperty(value="lat")
	private String lat;
	@JsonProperty(value="long")
	private String longitude;
	@JsonProperty(value="challan_doc_no")
	private String challan_doc_no;
	@JsonProperty(value="acc_vehicle_class")
	private String acc_vehicle_class;
	@JsonProperty(value="accused_type")
	private String accused_type;
	@JsonProperty(value="accused")
	private Map<String,AccusedDto> accused;
	
	// not used
	@JsonProperty(value="dl_number")
	private String dl_number;
	@JsonProperty(value="challan_vehicle_img" )
	private String[] challan_vehicle_img;
	////////////
	@JsonProperty(value="type")
	private int type;
	@JsonProperty(value="violation_id")
	private String violation_id;
	@JsonProperty(value="challan_source_type")
	private String challan_source_type;
	
}
