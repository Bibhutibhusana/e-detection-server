package com.nic.edetection.dto;

import com.nic.edetection.model.BaseEntity;

import lombok.Data;

@Data
public class UserLoginDto extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String userName;
	private String password;
	private String fullName;
	private String role;
	private String email;
	private String phone;
	private Boolean active;
	private String tollName;
	private String piu;
	private String offCd;
	private String stateCd;
	private String orgPass;
	private String latitude;
	private String longitude;
	private Integer districtId;
	
}
