package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUserNameAndRegistered(String username,Boolean registered);
}
