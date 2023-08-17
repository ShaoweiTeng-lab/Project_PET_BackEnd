package project_pet_backEnd.socialMedia.post.dto.req;

import lombok.Data;

@Data
public class UpPostReq {
    int userId;
    String postContent;
}
