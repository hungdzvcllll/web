package com.web.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.web.web.entity.User;
import com.web.web.entity.Class;
import com.web.web.entity.UserInClass;
import com.web.web.entity.embedId.UserInClassId;

import jakarta.transaction.Transactional;

import java.util.List;

public interface UserInClassRepository extends JpaRepository<UserInClass,UserInClassId> {
    public UserInClass findByUserAndClasss(User u,Class c);
    public UserInClass findByUserAndClasssAndRole(User u,Class c,String role);
    public Page<UserInClass> findByUser(User user,Pageable pageable);
    public Page<UserInClass> findByClasss(Class c,Pageable pageable);
    @Modifying
    @Transactional
    public void deleteByUserAndClasss(User u,Class c);
}
