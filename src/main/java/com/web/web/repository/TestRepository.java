package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer>{
    
}
