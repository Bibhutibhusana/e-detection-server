package com.nic.edetection.iservice;

import java.util.List;

import com.nic.edetection.dto.UserStatusDto;

public interface IUserStatusService {
	
	public List<UserStatusDto> getUserStatusList();
	public UserStatusDto getUserStatusByid(String id);
	public UserStatusDto createUserStatus(UserStatusDto userStatus);
	public List<UserStatusDto> addAllStatus(List<UserStatusDto> statusList);
	public List<UserStatusDto> getUserStatusByVehicleNo(String vehicleNo);

}
