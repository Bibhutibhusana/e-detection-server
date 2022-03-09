package com.nic.edetection.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle_details")
public class VehicleDetails extends BaseEntity{
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name ="id")
	private Long id;
	
	@Column(name = "vehicle_no")
	private String vehicleNo;
	
	@Column(name="transaction_date")
	private Date transactionDate;
	
	@Column(name="vt_class")
	private String vtClass;
	
	@Column(name="vh_class")
	private String vhClass;
	
	@Column(name="tax_upto")
	private Date taxUpto;
	
	@Column(name="fit_upto")
	private Date fitUpto;
	
	@Column(name="ins_upto")
	private Date insUpto;
	
	@Column(name="nonusestat")
	private String nonUseStat; 

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) { 
		this.id = id;
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

	public Date getTaxUpto() {
		return taxUpto;
	}

	public void setTaxUpto(Date taxUpto) {
		this.taxUpto = taxUpto;
	}

	public Date getFitUpto() {
		return fitUpto;
	}

	public void setFitUpto(Date fitUpto) {
		this.fitUpto = fitUpto;
	}

	public Date getInsUpto() {
		return insUpto;
	}

	public void setInsUpto(Date insUpto) {
		this.insUpto = insUpto;
	}

	public String getNonUseStat() {
		return nonUseStat;
	}

	public void setNonUseStat(String nonUseStat) {
		this.nonUseStat = nonUseStat;
	}
	
	
	

}
