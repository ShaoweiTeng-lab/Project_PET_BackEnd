package project_pet_backEnd.socialMedia.post.dto.req;

import lombok.Data;

@Data
public class MesReq {
    int userId;
    int postId;
    String mesContent;
}
