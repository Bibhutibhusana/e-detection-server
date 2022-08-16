package com.nic.edetection.model;

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
@Table(name="ed_vehicle_details")
public class VehicleDetails extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@Column(name="is_challan_issued")
	private Boolean isChallanIssued;
	
	@Column(name="uniqueid",unique=true)
	private String uniqueId;
	
	@Column(name="puc_upto")
	private Date pucUpto;
	
	@Column(name="permit_upto")
	private Date permitUpto;
	
	@Column(name="owner_name")
	private String ownerName;
	
	@Column(name="f_name")
	private String fName;
	
	@Column(name ="c_add1")
	private String cAdd1;
	
	@Column(name="c_add2")
	private String cAdd2;
	
	@Column(name="c_add3")
	private String cAdd3;
	
	@Column(name="c_district")
	private Integer cDistrict;
	
	@Column(name="c_state")
	private String cState;
	
	@Column(name="c_pincode")
	private Integer cPincode;
	
	@Column(name ="p_add1")
	private String pAdd1;
	
	@Column(name="p_add2")
	private String pAdd2;
	
	@Column(name="p_add3")
	private String pAdd3;
	
	@Column(name="p_district")
	private Integer pDistrict;
	
	@Column(name="p_state")
	private String pState;
	
	@Column(name="p_pincode")
	private Integer pPincode;
	
	
	@Column(name="mobile_no")
	private String mobileNo;
	


}
