package com.nic.edetection.repo;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nic.edetection.model.VehicleTransactionHistory;

@Repository
public interface VehicleTransactionHistoryRepository extends JpaRepository<VehicleTransactionHistory,Long>{

	@Query(value="COPY edetection.vehicle_transaction_history1 FROM  "+"?1"+" WITH CSV HEADER; -- must be superuser",nativeQuery = true)
	void importDataToTable( String path);

}
