package com.web.web.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.web.web.dto.request.VipUserRequest;
import com.web.web.dto.response.VipUserResponse;
import com.web.web.entity.VipUser;
import com.web.web.mapper.VipUserMapper;
import com.web.web.repository.VipUserRepository;

@Service
public class VipUserService {
    @Autowired
    VipUserRepository vipRepo;
    @Autowired
    UserService userService;
    @Autowired
    VipUserMapper mapper;
    
    public VipUser save(VipUserRequest request, String transactionId) {
        VipUser vip = new VipUser(null, LocalDateTime.now().plusDays(30), 300000, transactionId, "vn", "pending", userService.findCurrentUser(),
                request.getMetadata());
        return vipRepo.save(vip);
    }
    
    public Page<VipUserResponse> yourVipRegister(@NonNull Pageable pageable) {
        return vipRepo.findByUser(userService.findCurrentUser(), pageable).map(mapper::toDTO);
    }
    
    public Page<VipUserResponse> findAll(@NonNull Pageable pageable) {
        return vipRepo.findAll(pageable).map(mapper::toDTO);
    }
    
    public void checkIfYouAreVipNow() {
        Integer userId = userService.findCurrentUser().getId();
        List<VipUser> activeVips = vipRepo.findActiveVipByUserId(userId);
        
        if (activeVips == null || activeVips.isEmpty()) {
            throw new RuntimeException("your vip is expired");
        }
    }
}