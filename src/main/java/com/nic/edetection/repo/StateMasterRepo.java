package com.nic.edetection.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nic.edetection.dto.StateMasterDto;
import com.nic.edetection.model.StateMaster;

public interface StateMasterRepo extends JpaRepository<StateMaster,Long>{

	StateMasterDto findByStateCode(String stateCode);

}
