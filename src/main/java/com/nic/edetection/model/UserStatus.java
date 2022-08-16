package com.nic.edetection.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ed_userstatus")
public class UserStatus extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id") 
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private String uniqueId;

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
	public String tollName;
	
	@Transient
	public String vehicleNo;
	
	@Transient 
	public Date transactionDate;
}
