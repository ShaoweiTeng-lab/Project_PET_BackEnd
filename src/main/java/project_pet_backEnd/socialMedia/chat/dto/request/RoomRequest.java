package project_pet_backEnd.socialMedia.chat.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class RoomRequest {
    @NonNull
    String roomId;
}
