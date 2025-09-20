package com.web.web.entity.embedId;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInClassId {
    private Integer classId;
    private Integer userId;
     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInClassId)) return false;
        UserInClassId that = (UserInClassId) o;
        return Objects.equals(that.userId,userId) &&
               Objects.equals(that.classId, classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, classId);
    }
}
