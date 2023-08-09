package project_pet_backEnd.socialMedia.chat.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class AddUserToRoomRequest {
    @NonNull
    int userId;
    @NonNull
    int activityId;
}
