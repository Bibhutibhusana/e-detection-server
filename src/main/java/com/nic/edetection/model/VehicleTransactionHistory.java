package com.nic.edetection.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle_transaction_history1")
public class VehicleTransactionHistory extends BaseEntity implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name ="id")
	private Long id;
	 
	@Column(name="vehicle_no")
	private String vehicleNo;
	 
	@Column(name="vt_class")
	private String vtClass;
	
	
	@Column(name ="transaction_date")
	private Date TransactionDate;


	@Column(name="uniqueid",unique=true,nullable=false)
	private String uniqueId;
	
	@Column(name="status")
	private String status;
	
	
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


	public String getVtClass() {
		return vtClass;
	}


	public void setVtClass(String vtClass) {
		this.vtClass = vtClass;
	}


	public Date getTransactionDate() {
		return TransactionDate;
	}


	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}

	

	public String getUniqueId() {
		return uniqueId;
	}


	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public VehicleTransactionHistory() {
		super();
	}


	@Override
	public String toString() {
		return "VehicleTransactionHistory [id=" + id + ", vehicleNo=" + vehicleNo + ", vtClass=" + vtClass
				+ ", TransactionDate=" + TransactionDate + ", uniqueId=" + uniqueId + ", status=" + status + "]";
	}

	

}
