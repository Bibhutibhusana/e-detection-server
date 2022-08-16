package com.nic.edetection.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nic.edetection.model.UserActionHistory;

@Repository
public interface UserActionHistoryRepo extends JpaRepository<UserActionHistory,Long>{

}
