package com.nic.edetection.livedb.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.edetection.database.LiveDbConnect;
import com.nic.edetection.iservice.IVehicleDetailsService;
import com.nic.edetection.iservice.IVehicleTransactionHistoryService;
import com.nic.edetection.model.VehicleDetails;
import com.nic.edetection.model.VehicleTransactionHistory;
import com.nic.edetection.repo.VehicleTransactionHistoryRepository;

@Service
@Transactional
public class VehicleDetailsFromLiveDbService {
	@Autowired 
	private IVehicleTransactionHistoryService vehicleTransactionHistoryService;
	
	@Autowired
	private IVehicleDetailsService vehicleDetailsService;
	public List<VehicleDetails> getVehicleDetails() throws SQLException {
		LiveDbConnect liveDb = new LiveDbConnect();
		List<VehicleDetails> vehicleDetailsList = new ArrayList<VehicleDetails>();
		List<VehicleDetails> invalidVehicleDetailsList = new ArrayList<VehicleDetails>();
		List<VehicleTransactionHistory> vehicleTransactionHistoryList = vehicleTransactionHistoryService.getVehicleTransactionHistoryList();
		
		PreparedStatement pst ;
		String liveDbQuery = "select te.reg as vehicle_no, te.tax as tax_upto,te.fitup fit_upto,vi.ins_upto as ins_upto,te.vclass as vh_class,"
				+ "(case when vn.regn_no is null then 'No-Nonuse' else 'Non-Use' end ) as nonusestat "
				+ "from (select o.regn_no reg ,vc.descr vclass ,max(tax_upto) as tax,o.fit_upto fitup  from vt_owner o "
				+ "inner join vt_tax t on o.regn_no=t.regn_no  left join vm_vh_class vc on o.vh_class = vc.vh_class "
				+ "where o.state_cd='OR' and o.status in ('A','Y') group by o.regn_no,o.fit_upto,vc.descr ) te "
				+ "left join vt_insurance vi on te.reg=vi.regn_no "
				+ "left join vt_non_use_tax_exem vn on te.reg=vn.regn_no where te.reg= ? ;";
		
		 try {
			 //for(VehicleTransactionHistory vth : vehicleTransactionHistoryList) {   vehicleTransactionHistoryList.size()
			 int rowIndex = 0;
			 for(int i =0; i< vehicleTransactionHistoryList.size(); i++) {
				 
				 rowIndex =rowIndex + 1;
				//System.out.println(rowIndex+" "+i);
				
	            
	            pst = liveDb.getConnection().prepareStatement(liveDbQuery);
	            pst.setString(1,vehicleTransactionHistoryList.get(i).getVehicleNo());
	            ResultSet liveDbRes = pst.executeQuery();
	            VehicleTransactionHistory v = vehicleTransactionHistoryList.get(i);
	            v.setStatus("y");
	            this.vehicleTransactionHistoryService.saveVehicleTransactionHistory(v);
	            while (liveDbRes.next()) {
	            	//System.out.println(liveDbRes.getDate("tax")+ " " + vehicleTransactionHistoryList.get(i).getVehicleNo());
	            	VehicleDetails r = new VehicleDetails();
	                //r.setRowIndex(Long.valueOf(rowIndex));
	            	//r.setId(Long.valueOf(rowIndex));
	                r.setTaxUpto(liveDbRes.getDate("tax_upto"));
                    r.setFitUpto(liveDbRes.getDate("fit_upto"));
                    r.setInsUpto(liveDbRes.getDate("ins_upto"));
                    r.setNonUseStat(liveDbRes.getString("nonusestat")); 
                    r.setVhClass(liveDbRes.getString("vh_class"));
                    
                    r.setVehicleNo(v.getVehicleNo());
                    r.setTransactionDate(v.getTransactionDate());
                    r.setVtClass(v.getVtClass());
                    
                    if ((r.getFitUpto() == null) || (r.getInsUpto() == null) || (r.getTaxUpto() == null) || (r.getNonUseStat() == null)) {
                    	invalidVehicleDetailsList.add(r);
                    } else if ((v.getTransactionDate().compareTo(r.getFitUpto()) > 0) || (v.getTransactionDate().compareTo(r.getInsUpto()) > 0) || (v.getTransactionDate().compareTo(r.getTaxUpto()) > 0) || (r.getNonUseStat() == "Nonuse")) {
                    	invalidVehicleDetailsList.add(r);
                    }
                    
                    vehicleDetailsService.createVehicleDetails(r);
                    vehicleDetailsList.add(r);
                    
                  
                    
                   
//	                ps.setString(1, v.r.getVehicleNo());
//	                ResultSet liveDbRes = ps.executeQuery();
//	                int count = 0;
//	                while (liveDbRes.next()) {
//	                    count = count + 1;
//
//	                    //v.r.setVhClass(liveDbRes.getString("vclass"));
//	                    //System.out.println(liveDbRes.getString("tax"));
//	                    if ((v.r.getFitup() == null) || (v.r.getInsUpto() == null) || (v.r.getTax() == null) || (v.r.getNonUseStat() == null)) {
//	                        tlList.add(v);
//	                    } else if ((v.r.getTransactionDate().compareTo(v.r.getFitup()) > 0) || (v.r.getTransactionDate().compareTo(v.r.getInsUpto()) > 0) || (v.r.getTransactionDate().compareTo(v.r.getTax()) > 0) || (v.r.getNonUseStat() == "Nonuse")) {
//	                        tlList.add(v);
//	                    }
//	                    pst.setString(1, v.r.getVehicleNo());
//	                    //System.out.println(rs.getTimestamp("transaction_date"));
//	                    pst.setTimestamp(2, rs.getTimestamp("transaction_date"));
//	                    pst.setString(3,v.r.getVoClass());
//	                    pst.setString(4,v.r.getVhClass());
//	                    pst.setDate(5, v.r.getTax());
//	                    pst.setDate(6, v.r.getFitup());
//	                    pst.setDate(7, v.r.getInsUpto());
//	                    pst.setString(8, v.r.getNonUseStat());
////	                pst.setInt(7,rowIndex);
//	                    int status = pst.executeUpdate();
//	                    if (status > 0) {
//	                        System.out.println("Record Inserted" + count + " " + rowIndex);
//
//	                    } else {
//	                        System.out.println("Error in insertinon");
//	                    }
//	                    //tlList.add(r);
//	                    tl.add(v);
//
//	                }
////	                if(count > 0){
////	                   
////	                }
//
	                //rowIndex += 1;
//	                found = true;
//
//	            }
//	            pst.close();
//	            ps.close();
//	            rowIndex = 0;
//	            rs.close();
//	            st.close();
//
////	            if (found) {
////	                //return tlList;
////	               
////	            } else {
////	                return null;
////	            }
//	            transactionList = tlList;
                   
	            }
	           
			 }
			 
	            return this.vehicleDetailsService.addAllVehicles(invalidVehicleDetailsList);
	        } catch (Exception e) {
//	            e.printStackTrace();
            return (null);
	        } finally {
	            //localDb.connection.close();
	            liveDb.connection.close();
	        }
	    }
		
	}

