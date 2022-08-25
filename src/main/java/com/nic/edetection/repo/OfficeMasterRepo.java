package com.nic.edetection.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nic.edetection.model.OfficeMaster;

@Repository
public interface OfficeMasterRepo extends JpaRepository<OfficeMaster,Long>{

	List<OfficeMaster> findByDistId(Long districtId);

}
