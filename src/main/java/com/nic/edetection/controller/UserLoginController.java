package com.nic.edetection.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.model.UserLogin;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("api/v1")
public class UserLoginController {
	@Autowired IUserLoginService userLoginService;
	
	@GetMapping("/users")
	private List<UserLogin> getUsersList(){
		return userLoginService.getUserLoginList();
	}
	
	@GetMapping("/users/{id}")
	private UserLogin getUserById(@PathVariable(value="id")Long id){
		return userLoginService.getUserLoginByid(id);
	}
	
	@PostMapping("/user")
	private UserLogin addUserLogin(@RequestBody UserLogin user){
		return userLoginService.createUserLogin(user);
	}
	
	@PostMapping("/user/getByUserIdAndPassword")
	private UserLogin getUserByUserIdAndPassword(@RequestBody Map<String,String> user){
		return userLoginService.getUserLoginByUserIdAndPassword(user.get("username"),user.get("password"));
	}

}
