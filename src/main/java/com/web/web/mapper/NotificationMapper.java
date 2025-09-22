package com.web.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.web.web.dto.request.NotificationRequest;
import com.web.web.dto.response.NotificationResponse;
import com.web.web.entity.Notification;
import com.web.web.entity.User;

@Mapper
public interface NotificationMapper {
    @Mapping(target="user",source="userId")
    public Notification toEntity(NotificationRequest nr);
    default User mapUser(Integer id){
        if(id==null)
            return null;
        User u=new User();
        u.setId(id);
        return u; 
    }
    @Mapping(target="userId",source="user.id")
    public NotificationResponse toDTO(Notification n);
}
