package com.nic.edetection.dto;

import java.util.Date;

import javax.persistence.Transient;

import com.nic.edetection.model.BaseEntity;

import lombok.Data;

@Data
public class UserStatusDto extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uniqueId;
	@Transient
	private String vehicleNo;
	@Transient
	private Date transactionDate;
	private String uploadStatus;
	private Date uploadStatusDate;
	private String invalidVehicleFilterStatus;
	private Date invalidVehicleFilterStatuDate;
	private String inwardStatus;
	private Date inwardStatusDate;
	private String exportChallanListStatus;
	private Date exportChallanListStatuDate;
	private String challanIssueStatus;
	private Date challanIssueStatusDate;
	@Transient
	private String tollName;
	

}
