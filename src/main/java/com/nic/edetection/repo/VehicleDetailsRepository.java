package com.nic.edetection.repo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nic.edetection.model.VehicleDetails;

@Repository
public interface VehicleDetailsRepository extends JpaRepository<VehicleDetails, Long> {
	
	@Query(value = "select reg as vehicle_no, fitup as fit_upto, ins_upto, (case when tax == '3098-01-01' then 'OTT' else tax end) as tax_upto,nonusestat,vclass as vh_class from (select te.reg ,max(taxup) as tax,te.fitup,vi.ins_upto,te.vclass,"
			+ "(case when vn.regn_no is null then 'No-Nonuse' else 'Non-Use' end ) as nonusestat "
			+ "from (select o.regn_no reg ,vc.descr vclass ,(case when t.tax_upto is null then '3098-01-01' else t.tax_upto end) taxup,o.fit_upto fitup  from vt_owner o "
			+ "inner join vt_tax t on o.regn_no=t.regn_no  left join vm_vh_class vc on o.vh_class = vc.vh_class "
			+ "where o.state_cd='OR' and o.status in ('A','Y')) te "
			+ "left join vt_insurance vi on te.reg=vi.regn_no "
			+ "left join vt_non_use_tax_exem vn on te.reg=vn.regn_no where vn.reg= ?1 and taxup='3098-01-01' "
			+ "group by te.reg, te.fitup, vi.ins_upto, vn.reg_no) ",nativeQuery = true)
	List<Map<String, String>> getVehicleDetailsListByVehicleNo(String vehicleNo);

	
	@Query(value="select id,createdby,createddate,updatedby,hibversionno,updateddate,vehicle_no ,(Case when tax_upto is null then null else tax_upto end) tax_upto ,(case when fit_upto is null then null else fit_upto end) fit_upto,(case when ins_upto is null then null else ins_upto end) ins_upto,vh_class,vt_class,transaction_date,"
			+ " (case when nonusestat is null then 'OTT' else nonusestat end ) as nonusestat"
			+ "	from edetection.vehicle_details where (tax_upto < transaction_date) or (fit_upto < transaction_date) or (ins_upto < transaction_date)",nativeQuery=true)
	List<VehicleDetails> findInvalidVehicleList();
	
	
	@Query(value="select * from edetection.vehicle_details where transaction_date > tax_upto or tax_upto is null;",nativeQuery=true)
	List<VehicleDetails> findInvalidVehiclesByTaxUpto(String offenseType,String offenseType1);
	
	@Query(value="select * from edetection.vehicle_details where transaction_date > fit_upto or fit_upto is null;",nativeQuery=true)
	List<VehicleDetails> findInvalidVehiclesByFitUpto(String offenseType,String offenseType1);
	
	@Query(value="select * from edetection.vehicle_details where transaction_date > ins_upto or ins_upto is null;",nativeQuery=true)
	List<VehicleDetails> findInvalidVehiclesByInsUpto(String offenseType,String offenseType1);
	
	@Query(value="select * from edetection.vehicle_details where nonusestat='Non-Use' or nonusestat is null;",nativeQuery=true)
	List<VehicleDetails> findInvalidVehiclesByNonUse(String offenseType,String offenseType1);
	
	@Query(value="select * from edetection.vehicle_details v where v.vehicle_no not in (select e.vehicle_no from edetection.echallan e where e.transaction_date between ?1 and ?2 ) and  (transaction_date between ?1 and ?2 ) and  ((tax_upto < transaction_date) or (fit_upto < transaction_date) or (ins_upto < transaction_date))",nativeQuery=true)
	List<VehicleDetails> findVehicleDetailsListByDate(java.sql.Date date,java.sql.Date date2);

	
	
	@Query(value="select * from edetection.vehicle_details v  where (v.transaction_date > v.tax_upto and v.tax_upto is not null) and (v.transaction_date between ?1 and ?2 )",nativeQuery=true)
	List<VehicleDetails> findVehicleDetailsListByDateAndTaxUpto( Date fromDt, Date toDt);

	@Query(value="select * from edetection.vehicle_details v  where  (v.transaction_date > v.fit_upto or v.fit_upto is null) and (v.transaction_date between ?1 and ?2 )",nativeQuery=true)
	List<VehicleDetails> findVehicleDetailsListByDateAndFitUpto( Date fromDt, Date toDt);
	
	@Query(value="select * from edetection.vehicle_details v  where (v.transaction_date > v.ins_upto or v.ins_upto is null) and (v.transaction_date between ?1 and ?2 )",nativeQuery=true)
	List<VehicleDetails> findVehicleDetailsListByDateAndInsUpto( Date fromDt, Date toDt);
	
	@Query(value="select * from edetection.vehicle_details v  where (nonusestat='Non-Use' or nonusestat is null)  and (v.transaction_date between ?1 and ?2 )",nativeQuery=true)
	List<VehicleDetails> findVehicleDetailsListByDateAndNonUse(Date fromDt, Date toDt);
 


 
	

}
 