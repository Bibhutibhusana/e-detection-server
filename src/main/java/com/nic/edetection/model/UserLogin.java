package com.nic.edetection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Entity
@Table(name = "ed_user_login")
public class UserLogin extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "role")
	private String role;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "toll_name")
	private String tollName;

	@Column(name = "piu")
	private String piu;

	@Column(name = "off_cd")
	private String offCd;

	@Column(name = "st_cd")
	private String stateCd;

	private String orgPass;
	
	@Column(name="lat")
	private String latitude;
	@Column(name="long")
	private String longitude;
	@Column(name="district_id")
	private Integer districtId;


}
