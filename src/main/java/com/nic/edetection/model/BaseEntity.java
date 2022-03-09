package com.nic.edetection.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 6950438514663523722L;

	@Column(name="CREATEDBY")
	private Long createdBy;
	
	@Column(name="CREATEDDATE")
	private Date createdDate;
	
	@Column(name="UPDATEDBY")
	private Long updatedBy;
	
	@Column(name="UPDATEDDATE")
	private Date updatedDate = new Date();
	
	@Column(name="HIBVERSIONNO")
	@Version
	private Long versionNo;
	
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	
	public BaseEntity() {
		
	}
	
	public BaseEntity(Long createdBy, Date createdDate) {
		this.createdBy = createdBy;
		this.createdDate = createdDate == null ? null : new Timestamp(
				createdDate.getTime());
	}
	
	public BaseEntity(Long createdBy, Date createdDate,Long versionNo) {
		this.createdBy = createdBy;
		this.createdDate = createdDate == null ? null : new Timestamp(
				createdDate.getTime());
		this.versionNo = versionNo;
	}

	public BaseEntity(Long createdBy, Date createdDate, Long updatedBy,
			Date updatedDate,Long versionNo) {
		this.createdBy = createdBy;
		this.createdDate = createdDate == null ? null : new Timestamp(
				createdDate.getTime());
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate == null ? null : new Timestamp(
				updatedDate.getTime());
		this.versionNo = versionNo;
	}

}
