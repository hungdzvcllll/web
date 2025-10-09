package com.web.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.Notification;
import com.web.web.entity.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer>{
    public Page<Notification> findByUser(User u,Pageable pageable);
}
