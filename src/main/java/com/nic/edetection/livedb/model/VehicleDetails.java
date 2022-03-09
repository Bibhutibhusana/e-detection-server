package com.nic.edetection.livedb.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class VehicleDetails {
	private Long rowIndex;
	private String vehicleNo;
	private String vhClass;
	private Date taxUpto;
	private Date fitUpto;
	private Date insUpto;
	private String nonUseStat;
	
	

	public Long getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Long rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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
