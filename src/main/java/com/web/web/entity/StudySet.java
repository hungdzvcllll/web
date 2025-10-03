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
public class StudySet {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne()
    private Folder folder;
    @OneToMany(mappedBy="studySet",cascade=CascadeType.ALL)
    private List<Test> tests=new ArrayList<Test>();
    @OneToMany(mappedBy="studySet",cascade=CascadeType.ALL)
    private List<StudySetItem> studySetItem=new ArrayList<StudySetItem>();
}
