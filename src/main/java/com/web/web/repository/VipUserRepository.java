package com.web.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.web.entity.User;
import com.web.web.entity.VipUser;

@Repository
public interface VipUserRepository extends JpaRepository<VipUser, Integer> {
    @Query("SELECT v FROM VipUser v WHERE v.user.id = :userId AND v.expired > CURRENT_TIMESTAMP AND v.status = 'success' ORDER BY v.expired DESC")
    public List<VipUser> findActiveVipByUserId(@Param("userId") Integer userId);
    public VipUser findByTransactionId(String transactionId);
    public Page<VipUser> findByUser(User u, Pageable pageable);}