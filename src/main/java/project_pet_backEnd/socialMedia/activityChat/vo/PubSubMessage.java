package project_pet_backEnd.socialMedia.activityChat.vo;

import lombok.Data;

@Data
public class PubSubMessage {

    private String roomId;
    private String userId;
    private String username;
    private String content;
    private String userPic;
    private long date;


}
