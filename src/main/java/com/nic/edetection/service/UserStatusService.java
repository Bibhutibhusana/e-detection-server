package com.nic.edetection.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.UserStatusDto;
import com.nic.edetection.iservice.IUserStatusService;
import com.nic.edetection.model.UserStatus;
import com.nic.edetection.repo.UserStatusRepo;
@Service
public class UserStatusService implements IUserStatusService {
	@Autowired
	private UserStatusRepo userStatusRepo;
	@Autowired ModelMapper modelMapper;

	@Override
	public List<UserStatusDto> getUserStatusList() {
		// TODO Auto-generated method stub
		return userStatusRepo.findAll().stream().map(value -> modelMapper.map(value, UserStatusDto.class)).collect(Collectors.toList());
	}

	@Override
	public UserStatusDto getUserStatusByid(String id)  {
		// TODO Auto-generated method stub
		Optional<UserStatus> u = userStatusRepo.findById(id);
//				.orElseThrow(() -> new ResourceNotFoundException("UserStatu not found for this id::"+id));
		if(u.get() == null|| u.equals(null) || !u.isPresent()) {
			return new UserStatusDto();
		}
		return modelMapper.map(u.get(), UserStatusDto.class);
	}

	@Override
	public UserStatusDto createUserStatus(UserStatusDto userStatus) {
		// TODO Auto-generated method stub
		UserStatus u = modelMapper.map(userStatus,UserStatus.class);
		return modelMapper.map(userStatusRepo.save(u),UserStatusDto.class);
	}

	@Override
	public List<UserStatusDto> addAllStatus(List<UserStatusDto> statusList) {
		List<UserStatus> statuses = statusList.stream().map(value -> modelMapper.map(value, UserStatus.class)).collect(Collectors.toList());
		return userStatusRepo.saveAll(statuses).stream().map(value -> modelMapper.map(value, UserStatusDto.class)).collect(Collectors.toList());
	}

	public List<UserStatusDto> getUserStatusByVehicleNo(String vehicleNo) {
		// TODO Auto-generated method stub
		System.out.println(userStatusRepo.findByVehicleNo(vehicleNo).size());
		
		List<UserStatusDto> listOfUserStatus = userStatusRepo.findByVehicleNo(vehicleNo).stream().map(value -> modelMapper.map(value,UserStatusDto.class)).collect(Collectors.toList());
//		System.out.println(listOfUserStatus.size());
		return listOfUserStatus;
		
	}

}
