package com.nic.edetection.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nic.edetection.model.EChallan;

@Repository
public interface EChallanRepo extends JpaRepository<EChallan,Long>{

	List<EChallan> findAllByOpDateBetween(Date fd, Date td);

	@Query(value = "select * from edetection.echallan where op_date between ?1 and ?2 group by id,transaction_date",nativeQuery = true)
	List<EChallan> findAllByOpDateBetweenGroupByIdAndTransactionDate(Date fd, Date td);

	@Query(value = "select * from edetection.echallan where (op_date between ?1 and ?2) and challan_no is not null group by id,transaction_date ",nativeQuery = true)
	List<EChallan> findAllIssuedChallanByDate(Date fd, Date td);


}
