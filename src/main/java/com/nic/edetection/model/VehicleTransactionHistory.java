package com.nic.edetection.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ed_vehicle_transaction_history")
public class VehicleTransactionHistory extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "vehicle_no")
	private String vehicleNo;

	@Column(name = "vt_class")
	private String vtClass;

	@Column(name = "transaction_date")
	private Date transactionDate;

	@Column(name = "uniqueid", unique = true, nullable = false)
	private String uniqueId;

	@Column(name = "status")
	private String status;

	

}
