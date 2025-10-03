package com.web.web.entity.embedId;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessFolderId implements Serializable {
    private Integer userId;
    private Integer folderId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessFolderId)) return false;
        AccessFolderId that = (AccessFolderId) o;
        return Objects.equals(that.userId,userId) &&
               Objects.equals(that.folderId, folderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, folderId);
    }
}
