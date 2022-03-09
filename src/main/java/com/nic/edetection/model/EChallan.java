package com.nic.edetection.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="echallan")
public class EChallan extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="challan_track_id",unique=true,nullable=false)
	private String challanTrackId;
	
	@Column(name="vehicle_no")
	private String vehicleNo;
	
	@Column(name="transaction_date")
	private Date transactionDate;
	
	@Column(name="vt_class")
	private String vtClass;
	 
	@Column(name="vh_class")
	private String vhClass;
	
	@Column(name="purpose_code")
	private String purposeCode;
	
	@Column(name="op_date")
	private Date opDate = new Date(); 
	
	@Column(name="challan_no",unique= true)
	private String challanNo;
	
	@Column(name="challan_issue_date")
	private Date challanIssueDate;
	
	@Column(name="tax_upto")
	private Boolean taxUpto;
	
	@Column(name="fit_upto")
	private Boolean fitUpto;
	
	@Column(name="ins_upto")
	private Boolean insUpto;
	
	@Column(name="nonusestat")
	private Boolean nonUseStat;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getChallanTrackId() {
		return challanTrackId;
	}

	public void setChallanTrackId(String challanTrackId) {
		this.challanTrackId = challanTrackId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getVtClass() {
		return vtClass;
	}

	public void setVtClass(String vtClass) {
		this.vtClass = vtClass;
	}

	public String getVhClass() {
		return vhClass;
	}

	public void setVhClass(String vhClass) {
		this.vhClass = vhClass;
	}

	public String getPurposeCode() {
		return purposeCode;
	}

	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public Date getChallanIssueDate() {
		return challanIssueDate;
	}

	public void setChallanIssueDate(Date challanIssueDate) {
		this.challanIssueDate = challanIssueDate;
	}

	public Boolean getTaxUpto() {
		return taxUpto;
	}

	public void setTaxUpto(Boolean taxUpto) {
		this.taxUpto = taxUpto;
	}

	public Boolean getFitUpto() {
		return fitUpto;
	}

	public void setFitUpto(Boolean fitUpto) {
		this.fitUpto = fitUpto;
	}

	public Boolean getInsUpto() {
		return insUpto;
	}

	public void setInsUpto(Boolean insUpto) {
		this.insUpto = insUpto;
	}

	public Boolean getNonUseStat() {
		return nonUseStat;
	}

	public void setNonUseStat(Boolean nonUseStat) {
		this.nonUseStat = nonUseStat;
	}
	
	
	

}
