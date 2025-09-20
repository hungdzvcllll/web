package com.web.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.AccessFolder;
import com.web.web.entity.embedId.AccessFolderId;

@Repository
public interface AccessFolderRepository extends JpaRepository<AccessFolder,AccessFolderId>{
    
}
