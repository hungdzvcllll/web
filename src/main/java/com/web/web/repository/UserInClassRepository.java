package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.web.entity.UserInClass;
import com.web.web.entity.embedId.UserInClassId;

public interface UserInClassRepository extends JpaRepository<UserInClass,UserInClassId> {
    
}
