package com.web.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUsernameAndRegistered(String username,Boolean registered);
    public User findByUsernameAndRegisteredAndConfirmRegisterCode(String email,Boolean registered,String code);
    public User findByEmailAndRegistered(String email,Boolean registered);
    public User findByUsername(String username);
    public User findByEmail(String email);
    public Page<User> findAll(Pageable pageable);
}
