package project_pet_backEnd.socialMedia.chat.dto.response;

import lombok.Data;
import project_pet_backEnd.socialMedia.chat.dto.Room;
import java.util.List;


@Data
public class GetUserRoomIdsSetResponse {

    List<Room> roomList;
}
