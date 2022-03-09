package com.nic.edetection.iservice;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.nic.edetection.model.UserLogin;

@Service
@Transactional

public interface IUserLoginService {
	
	public List<UserLogin> getUserLoginList();
	public UserLogin getUserLoginByid(Long id);
	public UserLogin getUserLoginByUserIdAndPassword(String userId,String password);
	public UserLogin createUserLogin(UserLogin userLogin);

}
