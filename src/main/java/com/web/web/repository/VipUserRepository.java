package com.web.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.web.entity.User;
import com.web.web.entity.VipUser;

@Repository
public interface VipUserRepository extends JpaRepository<VipUser,Integer> {
    @Query(nativeQuery = true,value="select * from VipUser where user_id=?1 and expired>CURDATE() and status='success'")
    public VipUser findVipUserNotExpired(Integer id);
    public VipUser findByTransactionId(String transactionId);
    public Page<VipUser> findByUser(User u,Pageable pageable);
    public Page<VipUser> findAll(Pageable pageable);
}
