package com.nic.edetection.dto;

import com.nic.edetection.model.BaseEntity;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UserActionHistoryDto extends BaseEntity {

	private Long id;
	private String eventCode;
	private String eventStatus;
	private String ip;

}
