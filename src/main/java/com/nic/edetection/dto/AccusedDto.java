package com.nic.edetection.dto;

import lombok.Data;

@Data
public class AccusedDto {
	private String acc_type;
	private String remark;
	private String acc_id;
	private String acc_name;
	private String acc_mobile_no;
	private String acc_father;
	private String acc_address;
	
	// not used fields
	private String permanent_address;
	private String acc_img;
	private String cctv_image_1;
	private String cctv_image_2;

}
