package com.web.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.web.entity.Folder;
import com.web.web.entity.StudySet;

@Repository
public interface StudySetRepository extends JpaRepository<StudySet,Integer> {
    public Page<StudySet> findByFolder(Folder f,Pageable pageable);
    List<StudySet> findByNameContaining(String name); // Tìm bộ học theo tên gần đúng
    @Query("SELECT s FROM StudySet s WHERE s.folder.id IN (SELECT a.folder.id FROM AccessFolder a WHERE a.user.id = :userId)")
    Page<StudySet> findByUserId(@Param("userId") Integer userId, Pageable pageable);
}
