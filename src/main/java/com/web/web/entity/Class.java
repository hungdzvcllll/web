package com.web.web.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    Integer id;
    String name;
    String link;
    Boolean isPrivate;
    @OneToMany(mappedBy="belongToClass",cascade=CascadeType.ALL)
    private List<Folder> folders=new ArrayList<Folder>();
    @OneToMany(mappedBy="classs",cascade=CascadeType.ALL)
    private List<UserInClass> userInClasss=new ArrayList<UserInClass>();
}
