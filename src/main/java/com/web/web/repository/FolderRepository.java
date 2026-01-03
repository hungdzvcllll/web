package com.web.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.Class;
import com.web.web.entity.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Integer> {
    Page<Folder> findByBelongToClass(Class c,Pageable pageable);
    Page<Folder> findByIsPrivateAndName(Boolean isPrivate,String name,Pageable pageable);
    List<Folder> findByNameContaining(String name); // Tìm thư mục theo tên gần đúng
}
