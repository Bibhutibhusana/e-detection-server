package com.nic.edetection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="offence_master")
@Entity
public class OffenceMaster {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="off_name")
	private String offenceName;
	
	@Column(name="off_desc")
	private String offenceDesc;
	
	@Column(name="off_code")
	private String offCode;
	
	@Column(name="off_id")
	private int offId;
	
	@Column(name="penalty")
	private float penalty;
	

}
