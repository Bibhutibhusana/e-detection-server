package com.nic.edetection.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nic.edetection.model.UserLogin;


public interface UserLoginRepo extends JpaRepository<UserLogin, Long>{

	UserLogin findByUserNameAndPassword(String userId, String password);

	UserLogin findByUserName(String username);

	@Query(value="select sub.toll_name,sub.transaction_date,count(*),sub.op_date from (select ul.toll_name,to_char(th.transaction_date,'yyyy-MM-dd') as "
			+ "	transaction_date,to_char(th.createddate, 'yyyy-MM-dd')	as op_date	from edetection.ed_user_login ul left join  edetection.ed_vehicle_transaction_history th "
			+ " on th.createdby = ul.id where th.transaction_date is not null and ul.toll_name is not null group by th.transaction_date, ul.toll_name,th.createddate ) as sub "
			+ "	group by sub.transaction_date, sub.toll_name,sub.op_date order by sub.transaction_date desc,sub.toll_name desc",nativeQuery=true)
	List<Map<Object, Object>> getTollWiseDataUploadStatus();

	@Query(value="SELECT id, role, username,toll_name FROM edetection.ed_user_login where active='y' and role=\'TollUser\'",nativeQuery=true)
	List<Map<Object, Object>> getAllTollgates();

	@Query(value="select * from (select sub.toll_name,sub.transaction_date,count(*),sub.op_date from (select ul.toll_name,to_char(th.transaction_date,'yyyy-MM-dd') as "
			+ "	transaction_date,to_char(th.createddate, 'yyyy-MM-dd')	as op_date	from edetection.ed_user_login ul left join  edetection.ed_vehicle_transaction_history th "
			+ " on th.createdby = ul.id where th.transaction_date is not null and ul.toll_name is not null group by th.transaction_date, ul.toll_name,th.createddate ) as sub "
			+ "	group by sub.transaction_date, sub.toll_name,sub.op_date order by sub.transaction_date desc,sub.toll_name desc) as al where al.transaction_date = ?",nativeQuery=true)
	List<Map<Object, Object>> getTollWiseTransactionDateDataUploadStatus(String transactionDate);

}
