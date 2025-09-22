package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.VipUser;

@Repository
public interface VipUserRepository extends JpaRepository<VipUser,Integer> {
    
}
