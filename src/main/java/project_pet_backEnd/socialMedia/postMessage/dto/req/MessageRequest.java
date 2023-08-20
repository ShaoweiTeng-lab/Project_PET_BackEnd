package project_pet_backEnd.socialMedia.postMessage.dto.req;

import lombok.Data;

@Data
public class MessageRequest {
    int userId;
    int postId;
    String mesContent;
}
