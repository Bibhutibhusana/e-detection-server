package com.nic.edetection.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name = "ed_echallan")
public class EChallan extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "challan_track_id", unique = true, nullable = false)
	private String challanTrackId;

	@Column(name = "vehicle_no")
	private String vehicleNo;

	@Column(name = "transaction_date")
	private Date transactionDate;

	@Column(name = "vt_class")
	private String vtClass;

	@Column(name = "vh_class")
	private String vhClass;

	@Column(name = "purpose_code")
	private String purposeCode;
	
	@Column(name="offence_id")
	private String offenceId;
	
	@Column(name="challan_amt")
	private Integer challanAmt;

	@Column(name = "op_date")
	private Date opDate = new Date();

	@Column(name = "challan_no", unique = true)
	private String challanNo;

	@Column(name = "challan_issue_date")
	private Date challanIssueDate;

	@Column(name = "tax_upto")
	private Boolean taxUpto;

	@Column(name = "fit_upto")
	private Boolean fitUpto;

	@Column(name = "ins_upto")
	private Boolean insUpto;

	@Column(name = "puc_upto")
	private Boolean pucUpto;

	@Column(name = "permit_upto")
	private Boolean permitUpto;

	@Column(name = "nonusestat")
	private Boolean nonUseStat;

	private String toll_name;

	public Long getId() {
		return id;
	}


	@Transient
	public String getToll_name() {
		return toll_name;
	}

	public void setToll_name(String toll_name) {
		this.toll_name = toll_name;
	}

	

}
