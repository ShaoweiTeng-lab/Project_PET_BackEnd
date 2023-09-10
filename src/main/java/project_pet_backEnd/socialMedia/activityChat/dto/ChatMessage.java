package project_pet_backEnd.socialMedia.activityChat.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private String roomId;
    private String userId;
    private String username;
    //base64
    private String userPic;
    private String date;
    private String message;
}
