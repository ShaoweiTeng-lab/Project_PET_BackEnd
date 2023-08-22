package project_pet_backEnd.socialMedia.post.post.dto.res;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostRes {
    Integer postId;
    Integer userId;
    String postContent;
    Timestamp updateTime;
    Integer postStatus;
}
