package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.StudySet;

@Repository
public interface StudySetRepository extends JpaRepository<StudySet,Integer> {
    
}
