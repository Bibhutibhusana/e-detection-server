package com.nic.edetection.repo;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nic.edetection.dto.VehicleTransactionHistoryDto;
import com.nic.edetection.model.VehicleTransactionHistory;

@Repository
public interface VehicleTransactionHistoryRepository extends JpaRepository<VehicleTransactionHistory,Long>{

	@Query(value="COPY edetection.ed_vehicle_transaction_history FROM  "+"?1"+" WITH CSV HEADER; -- must be superuser",nativeQuery = true)
	void importDataToTable( String path);

	@Query(value="select * from edetection.ed_vehicle_transaction_history where status is null and transaction_date >?1  and transaction_date < ?2 limit 10",nativeQuery = true)
	List<VehicleTransactionHistory> getVehicleTransactionHistoryListLimitBy1000(Date transactionDate,Date toDt);

	@Query(value="SELECT vh.id, vh.createdby, vh.createddate, vh.transaction_date, status, uniqueid, vehicle_no, vt_class, ul.toll_name "
			+ "	FROM edetection.ed_vehicle_transaction_history vh inner join edetection.ed_user_login ul on ul.id=vh.createdby where vehicle_no=?",nativeQuery=true)
	List<VehicleTransactionHistoryDto> findByVehicleNoAndUser(@Valid String vehicleNo);


	List<VehicleTransactionHistory> findByTransactionDateBetween(@Valid Date transactionDate, Date toDt);
	
//	@Query(value="select * from edetection.ed_vehicle_transaction_history where status is null limit 300",nativeQuery = true)
//	List<VehicleTransactionHistory> getVehicleTransactionHistoryListLimitBy1000(Date transactionDate,Date toDt);

}
          