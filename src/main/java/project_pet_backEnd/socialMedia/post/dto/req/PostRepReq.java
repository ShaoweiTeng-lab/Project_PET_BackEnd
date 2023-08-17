package project_pet_backEnd.socialMedia.post.dto.req;


import lombok.Data;

@Data
public class PostRepReq {
    int postId;
    int userId;
    String reportContent;

}
