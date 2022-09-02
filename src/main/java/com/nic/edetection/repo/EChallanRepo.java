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

//	@Query(value = "select id, createdby, createddate, updatedby, updateddate, hibversionno, challan_issue_date, challan_no, challan_track_id, "
//			+ "fit_upto, ins_upto, nonusestat, op_date, purpose_code, tax_upto, transaction_date, vehicle_no, vh_class, vt_class from edetection.ed_echallan where op_date between ?1 and ?2 group by id,transaction_date",nativeQuery = true)
	
	@Query(value="select e.id, e.createdby, e.createddate, e.updatedby, e.updateddate, e.hibversionno, challan_issue_date, challan_no, challan_track_id,"
			+ "	fit_upto, ins_upto, nonusestat, op_date, purpose_code, tax_upto,permit_upto, puc_upto, e.transaction_date, e.vehicle_no, e.vh_class, e.vt_class,ul.toll_name, e.offence_id, e.challan_amt "
			+ "	from edetection.ed_echallan e left join edetection.ed_vehicle_transaction_history vh on  vh.uniqueid = e.challan_track_id"
			+ "	left join edetection.ed_user_login ul on vh.createdby = ul.id where (op_date"
			+ "	between ?1 and ?2) and ul.active = true and e.challan_no is null group by e.id,e.transaction_date,ul.toll_name",nativeQuery = true)
	List<EChallan> findAllByOpDateBetweenGroupByIdAndTransactionDate(Date fd, Date td);
	
	@Query(value="select e.id, e.createdby, e.createddate, e.updatedby, e.updateddate, e.hibversionno, challan_issue_date, challan_no, challan_track_id,"
			+ "	fit_upto, ins_upto, nonusestat, op_date, purpose_code, tax_upto,permit_upto, puc_upto, e.transaction_date, e.vehicle_no, e.vh_class, e.vt_class,ul.toll_name ,e.offence_id, e.challan_amt "
			+ "	from edetection.ed_echallan e left join edetection.ed_vehicle_transaction_history vh on  vh.uniqueid = e.challan_track_id"
			+ "	left join edetection.ed_user_login ul on vh.createdby = ul.id where (op_date"
			+ "	between ?1 and ?2 ) and ul.active and vh.createdby = ?3 and e.challan_no is null group by e.id,e.transaction_date,ul.toll_name",nativeQuery = true)
	List<EChallan> findAllByOpDateBetweenAndUserId(Date fd, Date td,Long id);
	
	@Query(value="select e.id, e.createdby, e.createddate, e.updatedby, e.updateddate, e.hibversionno, challan_issue_date, challan_no, challan_track_id,"
			+ "	fit_upto, ins_upto, nonusestat, op_date, purpose_code, tax_upto,permit_upto, puc_upto, e.transaction_date, e.vehicle_no, e.vh_class, e.vt_class,ul.toll_name ,e.offence_id, e.challan_amt"
			+ "	from edetection.ed_echallan e left join edetection.ed_vehicle_transaction_history vh on  vh.uniqueid = e.challan_track_id"
			+ "	left join edetection.ed_user_login ul on vh.createdby = ul.id where (vh.transaction_date"
			+ "	between ?1 and ?2) and ul.active and vh.createdby = ?3 and e.challan_no is null group by e.id,e.transaction_date,ul.toll_name",nativeQuery = true)
	List<EChallan> findAllByTransactionDateAndUserId(Date fd, Date td, Long id);
	
	

	@Query(value = "select * from edetection.ed_echallan where (op_date between ?1 and ?2) and challan_no is not null group by id,transaction_date ",nativeQuery = true)
	List<EChallan> findAllIssuedChallanByDate(Date fd, Date td);

	
	@Query(value = "select count(*) from edetection.ed_echallan e where e.challan_no is  null ",nativeQuery=true)
	Long getTotalInwardChallans();
	
	@Query(value = "select count(*) from edetection.ed_echallan e where e.challan_no is not null ",nativeQuery=true)
	Long getTotalIssuedChallans();

	@Query(value="select e.id, e.createdby, e.createddate, e.updatedby, e.updateddate, e.hibversionno, challan_issue_date, challan_no, challan_track_id,"
			+ "	fit_upto, ins_upto, nonusestat, op_date, purpose_code, tax_upto,permit_upto, puc_upto, e.transaction_date, e.vehicle_no, e.vh_class, e.vt_class,ul.toll_name,e.offence_id, e.challan_amt "
			+ "	from edetection.ed_echallan e left join edetection.ed_vehicle_transaction_history vh on  vh.uniqueid = e.challan_track_id"
			+ "	left join edetection.ed_user_login ul on vh.createdby = ul.id where (vh.transaction_date"
			+ "	between ?1 and ?2) and ul.active  and e.challan_no is null group by e.id,e.transaction_date,ul.toll_name",nativeQuery = true)
	List<EChallan> findAllByTransactionDateBetweenAndChallanNoIsNull(Date fd, Date td);

	@Query(value="update edetection.ed_echallan  set challan_issue_date = now(), challan_no=?1 where id=?2;" , nativeQuery=true)
	EChallan updateChallanDetailsById(String challan_no,Long valueOf);


}
 