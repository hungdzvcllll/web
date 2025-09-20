package com.web.web.entity;

import com.web.web.entity.embedId.AccessFolderId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessFolder {
    @EmbeddedId
    private AccessFolderId id;
    @ManyToOne()
    @MapsId("userId")
    private User user;
    @ManyToOne()
    @MapsId("folderId")
    private Folder folder;
    private String role;
    
}
