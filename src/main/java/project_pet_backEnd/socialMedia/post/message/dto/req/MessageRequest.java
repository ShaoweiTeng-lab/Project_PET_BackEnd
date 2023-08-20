package project_pet_backEnd.socialMedia.post.message.dto.req;

import lombok.Data;

@Data
public class MessageRequest {
    int userId;
    int postId;
    String mesContent;
}
