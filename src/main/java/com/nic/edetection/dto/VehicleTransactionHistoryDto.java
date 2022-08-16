package com.nic.edetection.dto;

import java.io.Serializable;
import java.util.Date;

import com.nic.edetection.model.BaseEntity;

import lombok.Data;

@Data
public class VehicleTransactionHistoryDto extends BaseEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	private Long id;
	private String vehicleNo;
	private String vtClass;
	private Date TransactionDate;
	private String uniqueId;
	private String status;
	private String toll_name;

}
