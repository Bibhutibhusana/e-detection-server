package com.nic.edetection.security.service;

import java.util.ArrayList;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nic.edetection.model.UserLogin;
import com.nic.edetection.repo.UserLoginRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired UserLoginRepo userRepo;
	@Lazy
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLogin users = userRepo.findByUserName(username);
		if (users == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
			
		}
		//System.out.println(users.getPassword());
		return new User(users.getUserName(), bcryptEncoder.encode(users.getPassword()),
				new ArrayList<>());
	}
	
	public UserLogin save(UserLogin user) {
		//String password = bcryptEncoder.encode(user.getPassword());
		//user.setPassword(password);
		
		return userRepo.save(user);
		
	}

	public UserLogin findByUserNameAndPassword(String userId, String password) {
		// TODO Auto-generated method stub
		//String encryptPass = bcryptEncoder.encode(password);
		//System.out.print(encryptPass);
		//System.out.println("password"+" "+password+"  "+bcryptEncoder.encode(password));
		return userRepo.findByUserNameAndPassword(userId,password);
	}

}