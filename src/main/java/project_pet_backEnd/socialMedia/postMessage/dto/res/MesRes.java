package project_pet_backEnd.socialMedia.postMessage.dto.res;

import lombok.Data;

@Data
public class MesRes {
    Integer userId;
    Integer messageId;
    Integer postId;
    String userName;
    String messageContent;
    String updateTime;
    Integer messageStatus;
}
