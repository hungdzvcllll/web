package com.web.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.web.web.dto.response.VipUserResponse;
import com.web.web.entity.VipUser;
@Mapper
public interface VipUserMapper {
    @Mapping(target="userId",source="user.id")
    public VipUserResponse toDTO(VipUser vu);
}
