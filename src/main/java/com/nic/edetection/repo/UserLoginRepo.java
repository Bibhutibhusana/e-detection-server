package com.nic.edetection.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nic.edetection.model.UserLogin;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin, Long>{

	UserLogin findByUserNameAndPassword(String userId, String password);

}
