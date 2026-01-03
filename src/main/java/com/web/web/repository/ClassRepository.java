package com.web.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.web.web.entity.Class;
@Repository
public interface ClassRepository extends JpaRepository<Class,Integer>{
    List<Class> findByNameContaining(String name);    
}
