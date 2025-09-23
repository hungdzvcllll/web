package com.web.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.web.web.dto.request.UserInClassRequest;
import com.web.web.dto.response.UserInClassResponse;
import com.web.web.entity.UserInClass;
import com.web.web.entity.Class;
import com.web.web.entity.User;
@Mapper
public interface UserInClassMapper{
    @Mapping(source="classId",target="classs")
    @Mapping(source="userId",target="user")
    public UserInClass toEntity(UserInClassRequest uicr);
    default Class mapClass(Integer id){
        if(id==null)
            return null;
        Class c=new Class();
        c.setId(id);
        return c; 
    }
    default User mapUser(Integer id){
        if(id==null)
            return null;
        User u=new User();
        u.setId(id);
        return u; 
    }
    @Mapping(source="classs.id",target="classId")
    @Mapping(source="user.id",target="userId")
    public UserInClassResponse toDTO(UserInClass uic);
}
