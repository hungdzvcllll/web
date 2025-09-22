package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.StudySetItem;

@Repository
public interface StudySetItemRepository extends JpaRepository<StudySetItem,Integer> {
    
}
