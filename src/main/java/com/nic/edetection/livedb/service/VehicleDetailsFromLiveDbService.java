package com.nic.edetection.livedb.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.edetection.database.LiveDbConnect;
import com.nic.edetection.dto.UserLoginDto;
import com.nic.edetection.dto.UserStatusDto;
import com.nic.edetection.dto.VehicleDetailsDto;
import com.nic.edetection.dto.VehicleTransactionHistoryDto;
import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.iservice.IUserStatusService;
import com.nic.edetection.iservice.IVehicleDetailsService;
import com.nic.edetection.iservice.IVehicleTransactionHistoryService;
import com.nic.edetection.model.UserStatus;
import com.nic.edetection.model.VehicleDetails;
import com.nic.edetection.model.VehicleTransactionHistory;

@Service
public class VehicleDetailsFromLiveDbService {

	@Autowired
	public IVehicleTransactionHistoryService vehicleTransactionHistoryService;

	@Autowired
	public IVehicleDetailsService vehicleDetailsService;

	@Autowired
	IUserLoginService userLoginService;

	@Autowired
	IUserStatusService userStatusService;

	@Autowired
	LiveDbConnect liveDbConnect;

	public boolean getVehicleDetails(Long userId, String date) throws SQLException, ParseException {

		UserLoginDto u = userLoginService.getUserLoginByid(userId);

		// LiveDbConnect liveDb = new LiveDbConnect();
		Connection con = liveDbConnect.getConnection();
		List<VehicleDetails> vehicleDetailsList = new ArrayList<VehicleDetails>();
		List<UserStatus> statusList = new ArrayList<UserStatus>();
		List<VehicleTransactionHistory> vehicleTransactionHistoryListForUpdate = new ArrayList<VehicleTransactionHistory>();
		// System.out.println(date);
		Date transactionDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		// System.out.println(transactionDate);
		List<VehicleTransactionHistoryDto> vehicleTransactionHistoryList = vehicleTransactionHistoryService
				.getVehicleTransactionHistoryListLimitBy1000(transactionDate);
		// System.out.println("method getVehicleDetails called");
		PreparedStatement pst;
		String liveDbQuery = "select ts.reg as vehicle_no, ts.tax as tax_upto,ts.fitup fit_upto,vi.ins_upto as ins_upto,ts.vclass as vh_class,vp.pucc_upto,  "
				+ "to_char(pmt.valid_upto,'yyyy-mm-dd') as permit_upto, (case when vn.regn_no is null then 'No-Nonuse' else 'Non-Use' end ) as nonusestat ,  "
				+ "ts.owner_name, ts.f_name,ts.c_add1,ts.c_add2,ts.c_add3,ts.c_district,ts.c_state,ts.c_pincode,ts.p_add1,ts.p_add2,ts.p_add3,ts.p_district,ts.p_state,ts.p_pincode,voi.mobile_no  "
				+ "from (SELECT reg,vclass,max(tax) as tax ,fitup,te.owner_name,te.f_name,te.c_add1,te.c_add2,te.c_add3,te.c_district,te.c_state,te.c_pincode,te.p_add1,te.p_add2, te.p_add3,te.p_district,te.p_state,te.p_pincode from (select o.regn_no reg ,vc.descr vclass ,  "
				+ "(CASE WHEN tax_upto IS NULL THEN '31-DEC-3030' else tax_upto end )  as tax,o.fit_upto fitup, o.owner_name, o.f_name,o.c_add1,o.c_add2,o.c_add3, o.c_district,o.c_state,o.c_pincode,o.p_add1,o.p_add2,o.p_add3, o.p_district,o.p_state,o.p_pincode "
				+ "from vt_owner o inner join vt_tax t on o.regn_no=t.regn_no  left join vm_vh_class vc on o.vh_class = vc.vh_class    "
				+ "where o.state_cd='OR' and o.status in ('A','Y')  ) te  group by reg,vclass,fitup, te.owner_name,te.f_name,te.c_add1,te.c_add2,te.c_add3,te.c_district,te.c_state,te.c_pincode,te.p_add1,te.p_add2,te.p_add3,te.p_district,te.p_state, te.p_pincode) ts  left join vt_insurance vi on ts.reg=vi.regn_no  "
				+ "left join vt_pucc as vp on vp.regn_no = ts.reg  left join vt_permit as pmt on pmt.regn_no = ts.reg   "
				+ "left join vt_non_use_tax_exem vn on ts.reg=vn.regn_no  left join vt_owner_identification voi on ts.reg=voi.regn_no where ts.reg= ?; "
				+ "";

		try {
			// for(VehicleTransactionHistory vth : vehicleTransactionHistoryList) {
			// vehicleTransactionHistoryList.size()
			if(con != null) {
			int rowIndex = 0;

			pst = con.prepareStatement(liveDbQuery);
			for (int i = 0; i < vehicleTransactionHistoryList.size(); i++) {

				rowIndex = rowIndex + 1;
				// System.out.println(rowIndex+" "+i);
				VehicleTransactionHistoryDto v = vehicleTransactionHistoryList.get(i);
				pst.setString(1, v.getVehicleNo());
				ResultSet liveDbRes = pst.executeQuery();
				UserStatusDto uStatus = new UserStatusDto();
				uStatus.setUpdatedBy(userId);
				uStatus.setUpdatedDate(new Date());
				uStatus.setInvalidVehicleFilterStatuDate(new Date());
				uStatus.setInvalidVehicleFilterStatus("y");
				uStatus.setUniqueId(v.getUniqueId());

				while (liveDbRes.next()) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					String strDate = formatter.format(v.getTransactionDate());
					// System.out.println(liveDbRes.getDate("tax")+ " " +
					// vehicleTransactionHistoryList.get(i).getVehicleNo());

					try {
						uStatus = userStatusService.getUserStatusByid(v.getUniqueId());
					} catch (Exception e) {
						// System.out.println("Erorr is "+e);
					}

//					statusList.add(uStatus);
					VehicleDetailsDto r = new VehicleDetailsDto();
					// r.setRowIndex(Long.valueOf(rowIndex));
					// r.setId(Long.valueOf(rowIndex));
					r.setTaxUpto(liveDbRes.getDate("tax_upto"));
					r.setFitUpto(liveDbRes.getDate("fit_upto"));
					r.setInsUpto(liveDbRes.getDate("ins_upto"));
					r.setPucUpto(liveDbRes.getDate("pucc_upto"));
					r.setPermitUpto(liveDbRes.getDate("permit_upto"));
					r.setNonUseStat(liveDbRes.getString("nonusestat"));
					r.setVhClass(liveDbRes.getString("vh_class"));
					r.setOwnerName(liveDbRes.getString("owner_name"));
					r.setFName(liveDbRes.getString("f_name"));
					r.setCAdd1(liveDbRes.getString("c_add1"));
					r.setCAdd2(liveDbRes.getString("c_add2"));
					r.setCAdd3(liveDbRes.getString("c_add3"));
					r.setCDistrict(liveDbRes.getInt("c_district"));
					r.setCState(liveDbRes.getString("c_state"));
					r.setCPincode(liveDbRes.getInt("c_pincode"));
					r.setPAdd1(liveDbRes.getString("p_add1"));
					r.setPAdd2(liveDbRes.getString("p_add2"));
					r.setPAdd3(liveDbRes.getString("p_add3"));
					r.setPDistrict(liveDbRes.getInt("p_district"));
					r.setPState(liveDbRes.getString("p_state"));
					r.setPPincode(liveDbRes.getInt("p_pincode"));
					r.setMobileNo(liveDbRes.getString("mobile_no"));
					r.setUniqueId(v.getVehicleNo() + "/" + strDate);

					r.setVehicleNo(v.getVehicleNo());
					r.setTransactionDate(v.getTransactionDate());
					// System.out.println(v.getTransactionDate());
					// System.out.println(r.getTransactionDate());
					r.setVtClass(v.getVtClass());
					r.setCreatedBy(userId);
					r.setCreatedDate(new Date());
//					if ((r.getFitUpto() == null) || (r.getInsUpto() == null) || (r.getTaxUpto() == null)
//							|| (r.getNonUseStat() == null)) {
//						invalidVehicleDetailsList.add(r);
//					} else if ((v.getTransactionDate().compareTo(r.getFitUpto()) > 0)
//							|| (v.getTransactionDate().compareTo(r.getInsUpto()) > 0)
//							|| (v.getTransactionDate().compareTo(r.getTaxUpto()) > 0)
//							|| (r.getNonUseStat() == "Nonuse")) {
//						invalidVehicleDetailsList.add(r);
//					}
					try {
						vehicleDetailsService.createVehicleDetails(r);
					} catch (Exception e) {
						continue;
					}

//					vehicleTransactionHistoryListForUpdate.add(v);
				}
				try {
					userStatusService.createUserStatus(uStatus);
				} catch (Exception e) {
					continue;
				}
				// vehicleDetailsList.add(r);
				v.setStatus("y");
				v.setUpdatedBy(u.getId());
				v.setUpdatedDate(new Date());
				try {
					this.vehicleTransactionHistoryService.saveVehicleTransactionHistory(v);
				} catch (Exception e) {
					continue;
				}

			}

			// this.saveVehicleDetails(vehicleDetailsList,
			// statusList,vehicleTransactionHistoryListForUpdate);

			pst.close();
			return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
//	            e.printStackTrace();
			con.close();
			return (false);
		} finally {
			con.close();
			// localDb.connection.close();

		}
	}
//	public void saveVehicleDetails(List<VehicleDetails> vehicleDetailsList,List<UserStatus> statusList,List<VehicleTransactionHistory> vehicleTransactionHistoryListForUpdate) {
//		try {
//		this.vehicleDetailsService.addAllVehicles(vehicleDetailsList);
//		}
//		catch(Exception e) {
//			System.out.println(e);
////		}try {
////		 this.userStatusService.addAllStatus(statusList);
////		}
////		 catch(Exception e) {
////				System.out.println(e);
////			}
////		 try {
////		 this.vehicleTransactionHistoryService.saveAllVehicleTransactionHistory(vehicleTransactionHistoryListForUpdate);
////		}
////		catch(Exception e) {
////			System.out.println(e);
////		}
//	}

}
