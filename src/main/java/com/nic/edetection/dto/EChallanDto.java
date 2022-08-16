package com.nic.edetection.dto;

import java.io.Serializable;
import java.util.Date;

import com.nic.edetection.model.BaseEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EChallanDto  extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String challanTrackId;
	private String vehicleNo;
	private Date transactionDate;
	private String vtClass;
	private String vhClass;
	private String purposeCode;
	private String offenceId;
	private Integer challanAmt;
	private Date opDate = new Date(); 
	private String challanNo;
	private Date challanIssueDate;
	private Boolean taxUpto;
	private Boolean fitUpto;
	private Boolean insUpto;
	private Boolean pucUpto;
	private Boolean permitUpto;
	private Boolean nonUseStat;
	private String toll_name;
	

}
