package project_pet_backEnd.socialMedia.activityChat.service;




import project_pet_backEnd.socialMedia.activityChat.dto.ChatMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;
import project_pet_backEnd.socialMedia.activityChat.dto.UserRoom;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface RoomService {
    ResultResponse<List<ChatMessage>> getMessageByRoomId(int roomId, int page, int size);

    ResultResponse<List<UserRoom>> getUserRoomList(int userId);

    ResultResponse<List<UserActivity>> getOnlineUserList(int roomId);

    ResultResponse<List<UserRoom>> getAllRoomList();

}
