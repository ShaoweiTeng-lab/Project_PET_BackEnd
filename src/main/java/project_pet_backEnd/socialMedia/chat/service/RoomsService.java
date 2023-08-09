package project_pet_backEnd.socialMedia.chat.service;


import project_pet_backEnd.socialMedia.chat.dto.request.AddUserToRoomRequest;
import project_pet_backEnd.socialMedia.chat.dto.request.CreateRoomRequest;
import project_pet_backEnd.socialMedia.chat.dto.response.GetUserRoomIdsSetResponse;

import java.util.Set;

public interface RoomsService {

    //create room
    void createGroupRoom(int activityId,String activityName);

    //get specific user  all roomIds
    GetUserRoomIdsSetResponse getUserGroupRoomIds(int userId);

    //get online userId list
    Set<Integer> getOnlineUsers();

    // add user into group chat
    void addToGroupChatRoom(AddUserToRoomRequest addUserToRoomRequest);

    // check room is exists?
    boolean checkRoomExists(String roomId);

    // get chat room name
    String getRoomNameById(String roomId);
}

