package project_pet_backEnd.socialMedia.chat.dto;


import lombok.Data;

@Data
public class PubSubMessage {
    private String roomId;
    private String userId;
    private String username;
    private String content;
    private int date;

}
