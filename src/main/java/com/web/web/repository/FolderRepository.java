package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Integer> {
}
