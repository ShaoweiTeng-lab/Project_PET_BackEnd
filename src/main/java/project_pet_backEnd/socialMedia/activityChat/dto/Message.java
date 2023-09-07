package project_pet_backEnd.socialMedia.activityChat.dto;

import lombok.Data;

@Data
public class Message {
    private String roomId;
    private String userId;
    private String username;
    private int date;
    private String message;
}
