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
			+ "where o.state_cd='OR' and o.status in ('A','Y')) te " + "left join vt_insurance vi on te.reg=vi.regn_no "
			+ "left join vt_non_use_tax_exem vn on te.reg=vn.regn_no where vn.reg= ?1 and taxup='3098-01-01' "
			+ "group by te.reg, te.fitup, vi.ins_upto, vn.reg_no) ", nativeQuery = true)
	List<Map<String, String>> getVehicleDetailsListByVehicleNo(String vehicleNo);

	@Query(value = "select id,createdby,createddate,updatedby,hibversionno,updateddate,vehicle_no ,(Case when tax_upto is null then null else tax_upto end) tax_upto ,(case when fit_upto is null then null else fit_upto end) fit_upto,(case when ins_upto is null then null else ins_upto end) ins_upto, permit_upto, puc_upto,vh_class,vt_class,transaction_date,"
			+ " (case when nonusestat is null then 'OTT' else nonusestat end ) as nonusestat"
			+ "	from edetection.ed_vehicle_details where (tax_upto < transaction_date) or (fit_upto < transaction_date) or (ins_upto < transaction_date)", nativeQuery = true)
	List<VehicleDetails> findInvalidVehicleList();

	@Query(value = "select * from edetection.ed_vehicle_details where transaction_date > tax_upto or tax_upto is null;", nativeQuery = true)
	List<VehicleDetails> findInvalidVehiclesByTaxUpto(String offenseType, String offenseType1);

	@Query(value = "select * from edetection.ed_vehicle_details where transaction_date > fit_upto or fit_upto is null;", nativeQuery = true)
	List<VehicleDetails> findInvalidVehiclesByFitUpto(String offenseType, String offenseType1);

	@Query(value = "select * from edetection.ed_vehicle_details where transaction_date > ins_upto or ins_upto is null;", nativeQuery = true)
	List<VehicleDetails> findInvalidVehiclesByInsUpto(String offenseType, String offenseType1);
	
	@Query(value = "select * from edetection.ed_vehicle_details where transaction_date > puc_upto or puc_upto is null;", nativeQuery = true)
	List<VehicleDetails> findInvalidVehiclesByPucUpto(String offenseType, String offenseType1);
	
	@Query(value = "select * from edetection.ed_vehicle_details where transaction_date > permit_upto or permit_upto is null;", nativeQuery = true)
	List<VehicleDetails> findInvalidVehiclesByPermitUpto(String offenseType, String offenseType1);
 
	@Query(value = "select * from edetection.ed_vehicle_details where nonusestat='Non-Use' or nonusestat is null;", nativeQuery = true)
	List<VehicleDetails> findInvalidVehiclesByNonUse(String offenseType, String offenseType1);

	@Query(value = "select * from edetection.ed_vehicle_details v where v.vehicle_no not in (select e.vehicle_no from edetection.ed_echallan e where e.transaction_date between ?1 and ?2 ) and  (transaction_date between ?1 and ?2 ) and  ((tax_upto < transaction_date) or (fit_upto < transaction_date) or (ins_upto < transaction_date) or (puc_upto < transaction_date) or (permit_upto < transaction_date))", nativeQuery = true)
	List<VehicleDetails> findVehicleDetailsListByDate(java.sql.Date date, java.sql.Date date2);

	@Query(value = "select * from edetection.ed_vehicle_details v  where (v.transaction_date > v.tax_upto and v.tax_upto is not null) and (v.transaction_date between ?1 and ?2 )", nativeQuery = true)
	List<VehicleDetails> findVehicleDetailsListByDateAndTaxUpto(Date fromDt, Date toDt);

	@Query(value = "select * from edetection.ed_vehicle_details v  where  (v.transaction_date > v.fit_upto or v.fit_upto is null) and (v.transaction_date between ?1 and ?2 )", nativeQuery = true)
	List<VehicleDetails> findVehicleDetailsListByDateAndFitUpto(Date fromDt, Date toDt);

	@Query(value = "select * from edetection.ed_vehicle_details v  where (v.transaction_date > v.ins_upto or v.ins_upto is null) and (v.transaction_date between ?1 and ?2 )", nativeQuery = true)
	List<VehicleDetails> findVehicleDetailsListByDateAndInsUpto(Date fromDt, Date toDt);
	
	@Query(value = "select * from edetection.ed_vehicle_details v  where (v.transaction_date > v.puc_upto or v.puc_upto is null) and (v.transaction_date between ?1 and ?2 )", nativeQuery = true)
	List<VehicleDetails> findVehicleDetailsListByDateAndPucUpto(Date fromDt, Date toDt);
	
	@Query(value = "select * from edetection.ed_vehicle_details v  where (v.transaction_date > v.permit_upto or v.permit_upto is null) and (v.transaction_date between ?1 and ?2 )", nativeQuery = true)
	List<VehicleDetails> findVehicleDetailsListByDateAndPermitUpto(Date fromDt, Date toDt);


	@Query(value = "select * from edetection.ed_vehicle_details v  where (nonusestat='Non-Use' or nonusestat is null)  and (v.transaction_date between ?1 and ?2 )", nativeQuery = true)
	List<VehicleDetails> findVehicleDetailsListByDateAndNonUse(Date fromDt, Date toDt);

	@Query(value = "select *"
			+ "from edetection.ed_vehicle_details v "
			+ "where (vh_class not in ('Motor Car','Motor Cab','Maxi Cab') and vt_class='Car') "
			+ "or (vt_class='Bus 2 AXLE' and vh_class not in ('Educational Institution Bus','Bus','Omni Bus','Omin Bus(Private Use)')) "
			+ "or ((vt_class='MAV 4-Axle' or vt_class='Truck 2 axle' or vt_class='LCV') and vh_class not in ('Goods Carrier')) and "
			+ "(transaction_date between ?1 and ?2 ) ", nativeQuery = true)
	List<VehicleDetails> findAllByDateAndClassComparision(java.sql.Date date, java.sql.Date date2);

	
//	@Query(value = "select count(*) from edetection.ed_vehicle_details v where v.vehicle_no not in (select e.vehicle_no from edetection.ed_echallan e  ) and ((tax_upto < transaction_date) or (fit_upto < transaction_date) or (ins_upto < transaction_date))", nativeQuery = true)
//	Long countInvalidVehicles();
//	@Query(value = "select count(id) from edetection.ed_userstatus where inward_status is  null and invalid_vehicle_filter_status is not null", nativeQuery = true)
	@Query(value="select count(vd.uniqueid) from edetection.ed_vehicle_details vd  inner join edetection.ed_userstatus  us on vd.uniqueid = us.id "
			+ "where ((vd.tax_upto < vd.transaction_date) or (vd.fit_upto < vd.transaction_date) or (vd.ins_upto < vd.transaction_date) or"
			+ " (vd.puc_upto < vd.transaction_date) or (vd.permit_upto < vd.transaction_date)) and us.inward_status is null",nativeQuery=true)
	Long countInvalidVehicles();

	@Query(value="SELECT  ec.challan_amt,ec.offence_id as offence_id, ul.district_id,vd.transaction_date as challan_time,ul.toll_name as challan_address,ul.lat,ul.long, "
			+ "vd.vehicle_no as challan_doc_no, vd.vh_class as acc_vehicle_class, vd.owner_name as acc_name,vd.mobile_no as acc_mob_no, vd.f_name as acc_father, "
			+ "vd.c_add1,vd.c_add2,vd.c_add3,dc.district_name as c_district, sc.descr as c_state,cast(vd.c_pincode as varchar(6)),vd.p_add1,vd.p_add2,vd.p_add3,dp.district_name as p_district, sp.descr as p_state,cast(vd.p_pincode as varchar(6)), "
			+ "ec.id as violation_id,ec.challan_track_id "
			+ "FROM edetection.ed_vehicle_details vd "
			+ "inner join edetection.ed_echallan ec on vd.uniqueid = ec.challan_track_id "
			+ "inner join edetection.ed_vehicle_transaction_history vh on vd.uniqueid = vh.uniqueid "
			+ "inner join edetection.ed_user_login ul on vh.createdby = ul.id "
			+ "left join edetection.district_master dc on dc.district_id = vd.c_district "
			+ "left join edetection.district_master dp on dp.district_id = vd.p_district "
			+ "left join edetection.state_master sc on sc.state_code = vd.c_state "
			+ "left join edetection.state_master sp on sp.state_code = vd.p_state "
			+ "where ec.challan_track_id IN  ?1",nativeQuery=true)
	List<Map<String, Object>> findITMSObjectListByUniqueId(List<String> uniqueIds);
	
}
