package project_pet_backEnd.socialMedia.activityChat.dto.response;

import lombok.Data;
import project_pet_backEnd.socialMedia.activityChat.dto.Room;
import java.util.List;


@Data
public class GetUserRoomIdsSetResponse {

    List<Room> roomList;
}
