package com.nic.edetection.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nic.edetection.dto.UserLoginDto;
import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.model.UserLogin;
import com.nic.edetection.repo.UserLoginRepo;
import com.nic.edetection.security.service.JwtUserDetailsService;

@Service
@Transactional
public class UserLoginService implements IUserLoginService ,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	UserLoginRepo userLoginRepo;
	@Autowired JwtUserDetailsService userService;
	@Autowired ModelMapper modelMapper;
	
	@Lazy
	@Autowired
	private PasswordEncoder bcryptEncoder;

	public List<UserLoginDto> getUserLoginList() {
		// TODO Auto-generated method stub
		return  userLoginRepo.findAll().stream().map(value -> modelMapper.map(value, UserLoginDto.class)).collect(Collectors.toList());
	}

	@Override
	public UserLoginDto getUserLoginByid(Long id) {
		// TODO Auto-generated method stub
		return modelMapper.map(userLoginRepo.getById(id),UserLoginDto.class);
	}

	@Override
	public UserLoginDto getUserLoginByUserIdAndPassword(String userId, String password) {
		// TODO Auto-generated method stub
		//System.out.println(userId+"   "+password);
		//System.out.println(userId +"  "+"password"+" "+password+"  "+bcryptEncoder.encode(password));
		return modelMapper.map(userLoginRepo.findByUserNameAndPassword(userId,password),UserLoginDto.class);
		
	}

	@Override
	public UserLoginDto createUserLogin(UserLoginDto userLogin) {
		// TODO Auto-generated method stub
		UserLogin user = modelMapper.map(userLogin, UserLogin.class);
		return modelMapper.map(userLoginRepo.save(user),UserLoginDto.class);
	}
	public UserLoginDto getUserByUserName(String username) {
		return modelMapper.map(userLoginRepo.findByUserName(username),UserLoginDto.class);
	}

	@Override
	public List<Map<Object, Object>> getTollWiseDataUploadStatus() {
		// TODO Auto-generated method stub
		return userLoginRepo.getTollWiseDataUploadStatus();
	}

	@Override
	public List<Map<Object, Object>> getTollNames() {
		// TODO Auto-generated method stub
		return userLoginRepo.getAllTollgates();
	}

	@Override
	public List<Map<Object, Object>> getTollWiseTransactionWiseDataUploadStatus(String userid, String transactionDate) {
		// TODO Auto-generated method stub
		System.out.println(transactionDate);
		return userLoginRepo.getTollWiseTransactionDateDataUploadStatus(transactionDate);
	}
	

}
