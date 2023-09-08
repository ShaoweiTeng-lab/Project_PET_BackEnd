package project_pet_backEnd.socialMedia.activityChat.dto;

import lombok.Data;

@Data
public class NotifyMessage {
    private String userId;
    private String username;
    private String date;
    private String message;
}
