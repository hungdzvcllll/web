package com.web.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.AccessFolder;
import com.web.web.entity.Folder;
import com.web.web.entity.User;
import com.web.web.entity.embedId.AccessFolderId;

@Repository
public interface AccessFolderRepository extends JpaRepository<AccessFolder,AccessFolderId>{
    public AccessFolder findByUserAndFolderAndRole(User u,Folder folder,String role);
    public Page<AccessFolder> findByUser(User u,Pageable pageable);
    public AccessFolder findByUserAndFolder(User u,Folder folder);
}

