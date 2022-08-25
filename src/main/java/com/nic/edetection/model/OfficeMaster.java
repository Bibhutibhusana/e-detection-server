package com.nic.edetection.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
 @Entity
 @Table(name="office_master")
public class OfficeMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String offCd;
	private String descr;
	private String piu;
	@ManyToOne
	private DistrictMaster dist;
}
