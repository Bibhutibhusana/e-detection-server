package com.nic.edetection.repo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nic.edetection.dto.UserStatusDto;
import com.nic.edetection.model.UserStatus;

@Repository
public interface UserStatusRepo extends JpaRepository<UserStatus,String> {

	@Query(value="SELECT o.id, o.createdby, o.createddate, o.updatedby, o.updateddate, o.hibversionno, o.challan_issue_status as challanIssueStatus,"
			+ " o.challan_issue_status_date as challanIssueStatusDate, o.export_challan_list_statu_date as exportChallanListStatuDate, "
			+ "	o.export_challan_list_status as exportChallanListStatus, o.invalid_vehicle_filter_statu_date as invalidVehicleFilterStatuDate, "
			+ " o.invalid_vehicle_filter_status as invalidVehicleFilterStatus, o.inward_status as inwardStatus, o.inward_status_date as inwardStatusDate, "
			+ "	o.upload_status as uploadStatus, o.upload_status_date as uploadStatusDate,  o.vehicle_no as vehicleNo , o.transaction_date as transactionDate,ul.toll_name as"
			+ " tollName  from (SELECT us.id, us.createdby, us.createddate, us.updatedby, us.updateddate, us.hibversionno, challan_issue_status, "
			+ " challan_issue_status_date, export_challan_list_statu_date, export_challan_list_status ,invalid_vehicle_filter_status, invalid_vehicle_filter_statu_date ,"
			+ "	upload_status, upload_status_date,  vh.vehicle_no , vh.transaction_date ,inward_status, inward_status_date"
			+ "	FROM edetection.ed_userstatus us  left join edetection.ed_vehicle_transaction_history vh on vh.uniqueid = us.id) as o "
			+ "	inner join edetection.ed_user_login ul on ul.id = o.createdby where o.vehicle_no=? order by o.transaction_date desc;",nativeQuery=true)
	Collection<Map<String,Object>> findByVehicleNo(String vehicleNo);

	

}
