package com.nic.edetection.dto;

import java.util.Date;

import javax.persistence.Column;

import com.nic.edetection.model.BaseEntity;

import lombok.Data;

@Data
public class VehicleDetailsDto extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String vehicleNo;
	private Date transactionDate;
	private String vtClass;
	private String vhClass;
	private Date taxUpto;
	private Date fitUpto;
	private Date insUpto;
	private String nonUseStat; 
	private Boolean isChallanIssued;
	private String uniqueId;
	private Date pucUpto;
	private Date permitUpto;
	@Column(name="owner_name")
	private String ownerName;
	private String fName;
	private String cAdd1;
	private String cAdd2; 
	private String cAdd3;
	private Integer cDistrict;
	private String cState;
	private int cPincode;
	private String pAdd1;
	private String pAdd2;
	private String pAdd3;
	private Integer pDistrict;
	private String pState;
	private int pPincode;
	private String mobileNo;
	

}
