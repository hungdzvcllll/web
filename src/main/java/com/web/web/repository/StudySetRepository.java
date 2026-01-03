package com.web.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.Folder;
import com.web.web.entity.StudySet;

@Repository
public interface StudySetRepository extends JpaRepository<StudySet,Integer> {
    public Page<StudySet> findByFolder(Folder f,Pageable pageable);
    List<StudySet> findByNameContaining(String name); // Tìm bộ học theo tên gần đúng
}
