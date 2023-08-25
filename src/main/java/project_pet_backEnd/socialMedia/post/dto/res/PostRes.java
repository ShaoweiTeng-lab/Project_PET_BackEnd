package project_pet_backEnd.socialMedia.post.dto.res;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostRes {
    Integer postId;
    Integer userId;
    String userName;
    //base64
    String userPic;
    String postContent;
    String createTime;
    String updateTime;
    Integer postStatus;
}
