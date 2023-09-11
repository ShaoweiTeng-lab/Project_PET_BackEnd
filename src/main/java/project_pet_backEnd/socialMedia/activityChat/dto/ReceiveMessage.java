package project_pet_backEnd.socialMedia.activityChat.dto;

import lombok.Data;

@Data
public class ReceiveMessage {
    private String roomId;
    private String content;
}
