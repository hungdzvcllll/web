package com.web.web.entity;

import com.web.web.entity.embedId.UserInClassId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInClass {
    @EmbeddedId
    private UserInClassId id;
    @ManyToOne()
    @MapsId("userId")
    private User user;
    @ManyToOne()
    @MapsId("classId")
    private Class classs;
    private String role;
}
