package com.nic.edetection.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nic.edetection.dto.DistrictMasterDto;
import com.nic.edetection.model.DistrictMaster;

public interface DistrictMasterRepo extends JpaRepository<DistrictMaster,Long>{

	DistrictMasterDto findByDistrictId(int districtId);

}
