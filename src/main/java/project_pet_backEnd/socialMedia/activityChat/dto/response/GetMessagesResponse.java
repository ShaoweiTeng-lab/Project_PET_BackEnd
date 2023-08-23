package project_pet_backEnd.socialMedia.activityChat.dto.response;


import lombok.Data;
import project_pet_backEnd.socialMedia.activityChat.dto.PubSubMessage;

import java.util.List;

@Data
public class GetMessagesResponse {
    private List<PubSubMessage> messages;
}
