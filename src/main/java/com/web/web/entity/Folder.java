package com.web.web.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Boolean isPrivate;
    @ManyToOne()
    @JoinColumn(nullable = true)
    private Class belongToClass;
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    List<AccessFolder> accessFolders=new ArrayList<AccessFolder>();
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    List<StudySet> studySets=new ArrayList<StudySet>();
}
