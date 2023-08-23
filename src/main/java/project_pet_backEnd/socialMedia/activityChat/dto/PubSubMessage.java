package project_pet_backEnd.socialMedia.activityChat.dto;


import lombok.Data;

@Data
public class PubSubMessage {
    private String roomId;
    private String userId;
    private String username;
    private String content;
    private int date;

}
