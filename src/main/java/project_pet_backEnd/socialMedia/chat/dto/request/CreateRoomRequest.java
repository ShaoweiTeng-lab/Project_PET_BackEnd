package project_pet_backEnd.socialMedia.chat.dto.request;

import lombok.Data;

@Data
public class CreateRoomRequest {
    int activityId;
    String activityName;
}
