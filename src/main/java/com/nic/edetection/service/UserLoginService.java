package com.nic.edetection.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.model.UserLogin;
import com.nic.edetection.repo.UserLoginRepo;

@Service
@Transactional
public class UserLoginService implements IUserLoginService {
	@Autowired
	UserLoginRepo userLoginRepo;

	@Override
	public List<UserLogin> getUserLoginList() {
		// TODO Auto-generated method stub
		return userLoginRepo.findAll();
	}

	@Override
	public UserLogin getUserLoginByid(Long id) {
		// TODO Auto-generated method stub
		return userLoginRepo.getById(id);
	}

	@Override
	public UserLogin getUserLoginByUserIdAndPassword(String userId, String password) {
		// TODO Auto-generated method stub
		return userLoginRepo.findByUserNameAndPassword(userId,password);
	}

	@Override
	public UserLogin createUserLogin(UserLogin userLogin) {
		// TODO Auto-generated method stub
		return userLoginRepo.save(userLogin);
	}
	

}
