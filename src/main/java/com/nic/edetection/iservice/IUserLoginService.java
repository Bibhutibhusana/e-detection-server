package com.nic.edetection.iservice;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.nic.edetection.dto.UserLoginDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.model.UserLogin;

@Service
@Transactional

public interface IUserLoginService {
	
	public List<UserLoginDto> getUserLoginList();
	public UserLoginDto getUserLoginByid(Long id);
	public UserLoginDto getUserLoginByUserIdAndPassword(String userId,String password);
	public UserLoginDto createUserLogin(UserLoginDto userLogin);
	public UserLoginDto getUserByUserName(String username);
	public List<Map<Object, Object>> getTollWiseDataUploadStatus();
	public List<Map<Object, Object>> getTollNames();
	public List<Map<Object, Object>> getTollWiseTransactionWiseDataUploadStatus(String userid,String transactioinDate);
	public Map<String, Boolean> deleteUserById(Long id) throws ResourceNotFoundException;

}
