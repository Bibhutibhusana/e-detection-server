package com.nic.edetection.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.edetection.dto.UserLoginDto;
import com.nic.edetection.exception.ResourceNotFoundException;
import com.nic.edetection.ip.IRequestService;
import com.nic.edetection.iservice.IUserLoginService;
import com.nic.edetection.security.JwtRequestFilter;
import com.nic.edetection.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1")
public class UserLoginController {
	@Autowired IUserLoginService userLoginService;
	
	@Autowired JwtTokenUtil jwtTokenUtils;
	
	@Autowired JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private IRequestService requestService;
	
	@GetMapping("/userscanOnlyFindByme")
	private List<UserLoginDto> getUsersList(){
		return userLoginService.getUserLoginList();
	}
	
	@GetMapping("/users/{id}")
	private UserLoginDto getUserById(@PathVariable(value="id")Long id){
		return userLoginService.getUserLoginByid(id);
	}
	
	@PostMapping("/user")
	private UserLoginDto addUserLogin(@RequestBody UserLoginDto user){
//		String requestTokenHeader="";
//		requestTokenHeader = jwtRequestFilter.getJwtToken();
		
		String username = jwtRequestFilter.getUsername();
		UserLoginDto us = userLoginService.getUserByUserName(username);
		long userId = user.getId();
		//UserLogin us = userLoginService.getUserLoginByid(userId);

		UserLoginDto loginUser = user;
		if(userId != 0) {
			
			loginUser = userLoginService.getUserLoginByid(userId);
			loginUser.setPassword(user.getPassword());
			loginUser.setUpdatedBy(us.getId());
			loginUser.setUpdatedDate(new Date());
			loginUser.setOrgPass(user.getOrgPass());
		}else {
		
		loginUser.setCreatedDate(new Date());
		loginUser.setCreatedBy(us.getId());
		}
		//System.out.println(user.getUserName()+"  "+user.getPassword()+" "+user.getId());
		return userLoginService.createUserLogin(loginUser);
	}
	
	@PostMapping("/user/getByUserIdAndPassword")
	private UserLoginDto getUserByUserIdAndPassword(@RequestBody Map<String,String> user){
		return userLoginService.getUserLoginByUserIdAndPassword(user.get("username"),user.get("password"));
	}


//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		requestTokenHeader = (String) request.getAttribute("authorization");
//		// TODO Auto-generated method stub
//		
//	}
	@GetMapping("/getip")
	public Map<String,Object> index(HttpServletRequest request) {
		Map<String,Object> model = new HashMap<String,Object>();
		String clientIp = requestService.getClientIp(request);
		model.put("clientIp", clientIp);
		System.out.print(clientIp);
		return model;
	}
	
	@GetMapping("/getTollNames")
	private List<Map<Object, Object>> getTollName(){
		return userLoginService.getTollNames();
	}
	
	@DeleteMapping("/user/{id}")
	private Map<String,Boolean> deleteUser(@PathVariable(value="id")Long id) throws ResourceNotFoundException{
		return userLoginService.deleteUserById(id);
	}

}
