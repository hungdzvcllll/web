package com.web.web.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String username;
    @Column(unique=true)
    private String email;
    private String role;
    private String loginBy;
    private String password;
    private Boolean registered;
    private String resetPasswordCode;
    private LocalDateTime resetPasswordExpired;
    private String confirmRegisterCode;
    private LocalDateTime confirmRegisterExpired;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<VipUser> vip_users=new ArrayList<VipUser>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<AccessFolder> folders=new ArrayList<AccessFolder>();
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    private List<UserInClass> userInClasss=new ArrayList<UserInClass>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Notification> notifications=new ArrayList<Notification>();
     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Test> tests=new ArrayList<Test>();
}
