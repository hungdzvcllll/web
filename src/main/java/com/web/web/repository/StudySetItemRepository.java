package com.web.web.repository;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.web.web.entity.StudySet;
import com.web.web.entity.StudySetItem;

import jakarta.transaction.Transactional;

@Repository
public interface StudySetItemRepository extends JpaRepository<StudySetItem,Integer> {
    public Page<StudySetItem> findByStudySet(StudySet set,Pageable pageable);
    public ArrayList<StudySetItem> findByStudySet(StudySet set);
}
