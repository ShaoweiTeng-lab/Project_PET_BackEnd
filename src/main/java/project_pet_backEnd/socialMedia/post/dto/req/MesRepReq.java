package project_pet_backEnd.socialMedia.post.dto.req;

import lombok.Data;

@Data
public class MesRepReq {
    int messageId;
    int userId;
    String MesRepContent;
}
